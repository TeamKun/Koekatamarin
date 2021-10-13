package net.kunmc.lab.koekatamarin;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MovingString {
    private final List<MovingLetter> movingLetterList;

    public MovingString(String str, float fontSize, Font font, double speedPerSecond, Location centerBottom, BlockData blockData, double degrees) {
        this(Letter.toLetterList(str, fontSize, font),
                speedPerSecond,
                centerBottom,
                blockData,
                degrees);
    }

    public MovingString(List<Letter> letterList, double speedPerSecond, Location centerBottom, BlockData blockData, double degrees) {
        List<MovingLetter> tmpMovingLetterList = new ArrayList<>();

        double xAngle = Math.toRadians(centerBottom.getPitch() * (90 - degrees) / 90);
        double yAngle = Math.toRadians(centerBottom.getYaw() + degrees);

        List<Letter> rotatedLetterList = letterList.stream()
                .map(x -> x.rotate(xAngle, yAngle))
                .collect(Collectors.toUnmodifiableList());

        Location loc = centerBottom.clone();
        double strWidth = calcStringWidth(letterList);
        loc.add(strWidth / 2 * Math.cos(yAngle), 0, strWidth / 2 * Math.sin(yAngle));

        for (Letter letter : rotatedLetterList) {
            tmpMovingLetterList.add(new MovingLetter(letter, speedPerSecond, loc, blockData));
            double width = letter.width + 1;
            loc.add(-width * Math.cos(yAngle), 0, -width * Math.sin(yAngle));
        }

        movingLetterList = Collections.unmodifiableList(tmpMovingLetterList);
    }

    public void remove() {
        movingLetterList.forEach(MovingLetter::remove);
    }

    public static double calcStringWidth(List<Letter> letterList) {
        double width = letterList.stream()
                .mapToInt(x -> x.width)
                .sum();
        width += letterList.size() - 1;
        return width;
    }
}
