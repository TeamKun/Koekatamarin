package net.kunmc.lab.koekatamarin;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.stream.Collectors;

public class MovingLetter {
    private final List<MovingBlock> blockList;

    public MovingLetter(Letter letter, double speedPerSecond, Location bottomLeft, BlockData blockData) {
        List<Location> locationList = letter.toLocationList(bottomLeft);
        blockList = locationList.stream()
                .map(x -> new MovingBlock(x, speedPerSecond, blockData))
                .collect(Collectors.toUnmodifiableList());
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
        blockList.forEach(MovingBlock::remove);
    }

    public boolean isCollideWithBlock() {
        return blockList.stream()
                .map(MovingBlock::getCenterLocation)
                .map(x -> x.add(x.getDirection().multiply(0.625)))
                .map(Location::getBlock)
                .anyMatch(x -> x.getType().isSolid());
    }

    public boolean isCollideWithLiquid() {
        return blockList.stream()
                .map(MovingBlock::getCenterLocation)
                .map(x -> x.add(x.getDirection().multiply(0.625)))
                .map(Location::getBlock)
                .anyMatch(Block::isLiquid);
    }

    public boolean isCollideWithOtherLetters() {
        throw new UnsupportedOperationException();
    }

    public void setMoving(boolean b) {
        blockList.forEach(x -> x.setMoving(b));
    }

    public boolean isRemoved() {
        return blockList.stream()
                .allMatch(MovingBlock::isRemoved);
    }
}
