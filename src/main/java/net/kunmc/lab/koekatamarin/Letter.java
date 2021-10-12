package net.kunmc.lab.koekatamarin;

import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Letter {
    private final Font font;
    public final List<Vector> blockPosList;
    private final int black = -16777216;

    public Letter(char letter, float fontSize, @Nullable Font font) {
        if (font == null) {
            this.font = new Font("Dialog.plain", Font.PLAIN, ((int) fontSize));
        } else {
            this.font = font.deriveFont(fontSize);
        }

        BufferedImage img = createLetterImage(letter);
        List<Vector> positionList = new ArrayList<>();

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                if (img.getRGB(x, y) == black) {
                    positionList.add(new Vector(x, img.getHeight() - y, 0));
                }
            }
        }

        blockPosList = Collections.unmodifiableList(positionList);
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
