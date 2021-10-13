package net.kunmc.lab.koekatamarin;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MovingString {
    private final List<MovingLetter> movingLetterList;
    private final BukkitTask detectCollisionTask;

    public MovingString(String str, float fontSize, Font font, double speedPerSecond, Location centerBottom, BlockData blockData, double degrees) {
        this(Letter.toLetterList(str, fontSize, font),
                speedPerSecond,
                centerBottom,
                blockData,
                degrees);
    }

    public MovingString(List<Letter> letterList, double speedPerSecond, Location centerBottom, BlockData blockData, double degrees) {
        List<MovingLetter> tmpMovingLetterList = new ArrayList<>();

        double maxHeight = letterList.stream()
                .mapToInt(x -> x.height)
                .max().getAsInt();
        double xAngle = Math.toRadians(centerBottom.getPitch() * (90 - degrees) / 90);
        double yAngle = Math.toRadians(centerBottom.getYaw() + degrees);

        List<Letter> rotatedLetterList = letterList.stream()
                .map(x -> x.rotate(xAngle, yAngle))
                .collect(Collectors.toUnmodifiableList());

        Location loc = centerBottom.clone();
        double strWidth = calcStringWidth(letterList);
        loc.add(strWidth / 2 * Math.cos(yAngle), 0, strWidth / 2 * Math.sin(yAngle));

        for (Letter letter : rotatedLetterList) {
            Location letterLocation = loc.clone();
            if (letter.height == 1) {
                letterLocation.add(0.0, (maxHeight - 1) / 2, 0.0);
            }

            tmpMovingLetterList.add(new MovingLetter(letter, speedPerSecond, letterLocation, blockData));

            double width = letter.width + 1;
            loc.add(-width * Math.cos(yAngle), 0, -width * Math.sin(yAngle));
        }

        movingLetterList = Collections.unmodifiableList(tmpMovingLetterList);
        detectCollisionTask = new DetectCollisionTask().runTaskTimerAsynchronously(Koekatamarin.instance, 0, 0);
    }

    public void remove() {
        detectCollisionTask.cancel();
        movingLetterList.forEach(MovingLetter::remove);
    }

    public static double calcStringWidth(List<Letter> letterList) {
        double width = letterList.stream()
                .mapToInt(x -> x.width)
                .sum();
        width += letterList.size() - 1;
        return width;
    }

    public boolean isRemoved() {
        return movingLetterList.stream()
                .allMatch(MovingLetter::isRemoved);
    }

    public boolean isCollideWithBlock() {
        return movingLetterList.stream()
                .anyMatch(MovingLetter::isCollideWithBlock);
    }

    public boolean isCollideWithLiquid() {
        return movingLetterList.stream()
                .anyMatch(MovingLetter::isCollideWithLiquid);
    }

    public boolean isCollideWithOtherStrings() {
        return movingLetterList.stream()
                .anyMatch(MovingLetter::isCollideWithOtherLetters);
    }

    public void setMoving(boolean b) {
        movingLetterList.forEach(x -> x.setMoving(b));
    }

    private class DetectCollisionTask extends BukkitRunnable {
        @Override
        public void run() {
            if (isRemoved()) {
                cancel();
                return;
            }

            boolean moving = !(isCollideWithBlock() || isCollideWithLiquid());
            setMoving(moving);
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
