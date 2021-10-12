package net.kunmc.lab.koekatamarin;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Shulker;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import static org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.CUSTOM;

public class MovingBlock {
    private final Location spawnedLocation;
    private final ArmorStand armorStand;
    private final FallingBlock fallingBlock;
    private final BukkitTask movingTask;
    private double speedPerSecond;

    public MovingBlock(Location location, double speedPerSecond, BlockData blockData) {
        this.spawnedLocation = location.clone();
        this.speedPerSecond = speedPerSecond;
        World world = location.getWorld();

        armorStand = world.spawn(spawnedLocation, ArmorStand.class, CUSTOM, x -> {
            x.setMarker(true);
            x.setVisible(false);
            x.setGravity(false);
        });

        world.spawn(spawnedLocation, Shulker.class, CUSTOM, x -> {
            x.setAI(false);
            x.setSilent(true);
            x.setInvulnerable(true);
            x.setInvisible(true);
            x.setLootTable(null);
            armorStand.addPassenger(x);
        });

        fallingBlock = world.spawnFallingBlock(spawnedLocation, blockData);
        fallingBlock.setInvulnerable(true);
        fallingBlock.setDropItem(false);
        fallingBlock.setGravity(false);
        armorStand.addPassenger(fallingBlock);

        movingTask = new MovingTask().runTaskTimerAsynchronously(Koekatamarin.instance, 0, 0);
    }

    public double speedPerSecond() {
        return speedPerSecond;
    }

    public void speedPerSecond(double speedPerSecond) {
        this.speedPerSecond = speedPerSecond;
    }

    public void remove() {
        movingTask.cancel();
        armorStand.getPassengers().forEach(Entity::remove);
        armorStand.remove();
    }

    private class MovingTask extends BukkitRunnable {
        @Override
        public void run() {
            if (armorStand.isDead()) {
                remove();
                cancel();
                return;
            }

            fallingBlock.setTicksLived(1);

            Vector direction = spawnedLocation.getDirection();
            Vector travelDistance = direction.multiply(speedPerSecond / 20);
            Location next = armorStand.getLocation().add(travelDistance);

            CraftArmorStand craftArmorStand = ((CraftArmorStand) armorStand);
            try {
                craftArmorStand.getHandle().teleportAndSync(next.getX(), next.getY(), next.getZ());
            } catch (Exception ignored) {
            }
        }
    }
}