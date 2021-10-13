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
    private final Shulker shulker;
    private final FallingBlock fallingBlock;
    private final BukkitTask movingTask;
    private double speedPerSecond;
    private boolean moving = true;
    private int ticksLived = 0;
    public static final String scoreboardTag = "movingBlockEntity";

    public MovingBlock(Location location, double speedPerSecond, BlockData blockData) {
        this.spawnedLocation = location.clone();
        this.speedPerSecond = speedPerSecond;
        World world = location.getWorld();

        armorStand = world.spawn(spawnedLocation, ArmorStand.class, CUSTOM, x -> {
            x.setMarker(true);
            x.setVisible(false);
            x.setGravity(false);
            x.addScoreboardTag(scoreboardTag);
        });

        shulker = world.spawn(spawnedLocation, Shulker.class, CUSTOM, x -> {
            x.setAI(false);
            x.setSilent(true);
            x.setInvulnerable(true);
            x.setInvisible(true);
            x.setLootTable(null);
            x.addScoreboardTag(scoreboardTag);
            armorStand.addPassenger(x);
        });

        fallingBlock = world.spawnFallingBlock(spawnedLocation, blockData);
        fallingBlock.setInvulnerable(true);
        fallingBlock.setDropItem(false);
        fallingBlock.setGravity(false);
        fallingBlock.addScoreboardTag(scoreboardTag);
        armorStand.addPassenger(fallingBlock);

        movingTask = new MovingTask().runTaskTimerAsynchronously(Koekatamarin.instance, 0, 0);
    }

    public void setMoving(boolean b) {
        moving = b;
    }

    public double speedPerSecond() {
        return speedPerSecond;
    }

    public void speedPerSecond(double speedPerSecond) {
        this.speedPerSecond = speedPerSecond;
    }

    public void teleport(Location to) {
        forceTeleportArmorStand(to);
    }

    public Location getLocation() {
        return armorStand.getLocation().clone();
    }

    public Location getCenterLocation() {
        Location loc = shulker.getBoundingBox().getCenter().toLocation(spawnedLocation.getWorld());
        loc.setPitch(spawnedLocation.getPitch());
        loc.setYaw(spawnedLocation.getYaw());
        return loc;
    }

    private void forceTeleportArmorStand(Location to) {
        CraftArmorStand craftArmorStand = ((CraftArmorStand) armorStand);
        try {
            craftArmorStand.getHandle().teleportAndSync(to.getX(), to.getY(), to.getZ());
        } catch (Exception ignored) {
        }
    }

    public void remove() {
        movingTask.cancel();
        armorStand.getPassengers().forEach(Entity::remove);
        armorStand.remove();
    }

    public boolean isRemoved() {
        return armorStand.isDead();
    }

    private class MovingTask extends BukkitRunnable {
        @Override
        public void run() {
            ticksLived++;
            if (ticksLived > 60 * 20) {
                remove();
            }

            if (armorStand.isDead()) {
                remove();
                cancel();
                return;
            }

            fallingBlock.setTicksLived(1);

            Vector direction = spawnedLocation.getDirection();
            Vector travelDistance = direction.multiply(speedPerSecond / 20);
            if (!moving) {
                travelDistance = direction.multiply(0.003);
            }

            Location next = armorStand.getLocation().add(travelDistance);

            forceTeleportArmorStand(next);
        }
    }
}