package net.kunmc.lab.koekatamarin;

import dev.kotx.flylib.FlyLib;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public final class Koekatamarin extends JavaPlugin {
    public static Koekatamarin instance;

    @Override
    public void onEnable() {
        instance = this;

        FlyLib.create(this, builder -> {
            builder.listen(PlayerInteractEvent.class, e -> {
                Vector direction = e.getPlayer().getLocation().getDirection();
                Location location = e.getPlayer().getEyeLocation().add(direction.multiply(1.0));
                new MovingBlock(location, 6.0, Material.DIAMOND_BLOCK.createBlockData());
            });
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
