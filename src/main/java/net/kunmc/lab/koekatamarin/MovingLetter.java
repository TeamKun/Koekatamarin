package net.kunmc.lab.koekatamarin;

import org.bukkit.Location;
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
}
