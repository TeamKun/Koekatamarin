package net.kunmc.lab.koekatamarin;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

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

    public void remove() {
        blockList.forEach(MovingBlock::remove);
    }
}
