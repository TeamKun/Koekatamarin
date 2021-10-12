package net.kunmc.lab.koekatamarin;

import org.bukkit.util.Vector;

public class VectorUtil {
    public static Vector rotateAroundYAxis(Vector center, Vector vector, double radian) {
        double rad = Math.atan2(vector.getZ() - center.getZ(), vector.getX() - center.getX()) + radian;
        double distanceFromCenter = center.clone().setY(0).subtract(vector.clone().setY(0)).length();
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        return new Vector(center.getX() + distanceFromCenter * cos, vector.getY(), center.getZ() + distanceFromCenter * sin);
    }
}
