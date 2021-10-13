package net.kunmc.lab.koekatamarin;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.stream.Collectors;

public class MovingLetter {
    private final List<MovingBlock> blockList;
    private final BukkitTask detectCollisionTask;

    public MovingLetter(Letter letter, double speedPerSecond, Location bottomLeft, BlockData blockData) {
        List<Location> locationList = letter.toLocationList(bottomLeft);
        blockList = locationList.stream()
                .map(x -> new MovingBlock(x, speedPerSecond, blockData))
                .collect(Collectors.toUnmodifiableList());

        detectCollisionTask = new DetectCollisionTask().runTaskTimerAsynchronously(Koekatamarin.instance, 0, 0);
    }

    public void rotateAroundYAxis(Vector axis, double degrees) {
        for (MovingBlock movingBlock : blockList) {
            Location blockLoc = movingBlock.getLocation();
            Location loc = VectorUtil.rotateAroundYAxis(axis, blockLoc.toVector(), Math.toRadians(degrees)).toLocation(blockLoc.getWorld());
            movingBlock.teleport(loc);
        }
    }

    public Vector calcCenter() {
        return blockList.stream()
                .map(x -> x.getLocation().toVector())
                .reduce(Vector::add)
                .get().divide(new Vector(blockList.size(), blockList.size(), blockList.size()));
    }

    public void remove() {
        detectCollisionTask.cancel();
        blockList.forEach(MovingBlock::remove);
    }

    public boolean isCollideWithBlock() {
        return blockList.stream()
                .map(MovingBlock::getCenterLocation)
                .map(x -> x.add(x.getDirection().multiply(0.625)))
                .map(Location::getBlock)
                .anyMatch(x -> x.getType().isSolid());
    }

    public boolean isCollideWithOtherLetters() {
        throw new UnsupportedOperationException();
    }

    public boolean isRemoved() {
        return blockList.stream()
                .allMatch(MovingBlock::isRemoved);
    }

    private class DetectCollisionTask extends BukkitRunnable {
        @Override
        public void run() {
            if (isRemoved()) {
                cancel();
                return;
            }

            boolean moving = !isCollideWithBlock();
            blockList.forEach(x -> {
                x.setMoving(moving);
            });
        }

        private synchronized void threadWait() {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private synchronized void threadNotify() {
            notify();
        }
    }
}
