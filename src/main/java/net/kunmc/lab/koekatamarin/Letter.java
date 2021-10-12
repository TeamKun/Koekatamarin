package net.kunmc.lab.koekatamarin;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Letter {
    private final Font font;
    private final List<Vector> relativePosList;
    public final int width;
    public final int height;
    private double xAngle = 0.0;
    private double yAngle = 0.0;
    private final int black = -16777216;

    public Letter(char letter, float fontSize, @Nullable Font font) {
        if (font == null) {
            this.font = new Font("Dialog.plain", Font.PLAIN, ((int) fontSize));
        } else {
            this.font = font.deriveFont(fontSize);
        }

        BufferedImage img = createLetterImage(letter);
        width = img.getWidth();
        height = img.getHeight();

        List<Vector> positionList = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (img.getRGB(x, y) == black) {
                    positionList.add(new Vector(-x, height - y, 0));
                }
            }
        }

        relativePosList = Collections.unmodifiableList(positionList);
    }

    private Letter(Font font, List<Vector> posList, int width, int height, double xAngle, double yAngle) {
        this.font = font;
        this.relativePosList = posList;
        this.width = width;
        this.height = height;
        this.xAngle = xAngle;
        this.yAngle = yAngle;
    }

    public Letter rotate(double xAngle, double yAngle) {
        return new Letter(
                font,
                relativePosList.stream()
                        .map(Vector::clone)
                        .map(v -> v.rotateAroundX(xAngle))
                        .map(v -> v.rotateAroundY(-yAngle))
                        .collect(Collectors.toUnmodifiableList()),
                width,
                height,
                xAngle,
                yAngle);
    }

    public double xAngle() {
        return xAngle;
    }

    public double yAngle() {
        return yAngle;
    }

    public List<Location> toLocationList(Location bottomLeft) {
        return relativePosList.stream()
                .map(v -> bottomLeft.clone().add(v))
                .collect(Collectors.toUnmodifiableList());
    }

    private BufferedImage createLetterImage(char letter) {
        int imgSize = 20 * 10 / font.getSize();
        BufferedImage img = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_3BYTE_BGR);
        Graphics graphics = img.createGraphics();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, imgSize, imgSize);

        graphics.setFont(font);
        graphics.setColor(Color.BLACK);
        graphics.drawString(Character.toString(letter), 1, 10);
        graphics.dispose();

        return clipMargin(img);
    }

    private BufferedImage clipMargin(BufferedImage img) {
        int minY = 99999;
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                if (img.getRGB(x, y) == black) {
                    minY = y;
                }
            }

            if (minY != 99999) {
                break;
            }
        }

        int minX = 99999;
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                if (img.getRGB(x, y) == black) {
                    minX = x;
                }
            }

            if (minX != 99999) {
                break;
            }
        }

        int maxY = -1;
        for (int y = img.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < img.getWidth(); x++) {
                if (img.getRGB(x, y) == black) {
                    maxY = y;
                }
            }

            if (maxY != -1) {
                break;
            }
        }

        int maxX = -1;
        for (int x = img.getWidth() - 1; x >= 0; x--) {
            for (int y = 0; y < img.getHeight(); y++) {
                if (img.getRGB(x, y) == black) {
                    maxX = x;
                }
            }

            if (maxX != -1) {
                break;
            }
        }

        return img.getSubimage(minX, minY, maxX - minX + 1, maxY - minY + 1);
    }
}
