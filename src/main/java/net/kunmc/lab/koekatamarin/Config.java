package net.kunmc.lab.koekatamarin;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public class Config {
    public static boolean enabled = false;
    public static double speedPerSecond = 5.2;
    public static float fontSize = 5.0F;
    public static boolean use4x4KanaFont = true;
    public static double degrees = 0.0;
    public static int maxLengthOfStr = 5;
    public static boolean limitCharTypes = true;
    public static BlockData block = Material.DIAMOND_BLOCK.createBlockData();
}
