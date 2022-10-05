package me.zqr0.hugelanskoy.Lanskoy;

import me.zqr0.hugelanskoy.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Lanskoy {

    private boolean isAggressive;
    private final Giant giantMobEntity;
    private Location playerLocation;
    private UUID uuid;
    private final String NAME = "Lanskoy";
    private int giantInOnePlaceTimes;

    Lanskoy(@NotNull Location playerLocation, boolean isAggressive) {
        this.isAggressive = isAggressive;
        this.playerLocation = playerLocation;

        Vector playerDirection = playerLocation.getDirection();

        this.giantMobEntity = (Giant) Objects.requireNonNull(playerLocation.getWorld()).spawnEntity(
                playerLocation.clone().add(
                        playerDirection.clone().multiply(5)
                ).setDirection(playerDirection.multiply(-1)), EntityType.GIANT);

        this.uuid = this.giantMobEntity.getUniqueId();

        this.giantMobEntity.setCustomName(this.NAME);
        this.giantMobEntity.setCustomNameVisible(true);
        Objects.requireNonNull(this.giantMobEntity.getEquipment()).setItemInMainHand(new ItemStack(Material.IRON_AXE));

        playerDirection.setY(0);
        playerDirection.normalize();
    }

    public void startLanskoy() {
        this.findEntitiesNearby();
        this.startToMove();
        this.attackEntity();
    }

    public boolean isAggressive() {
        return this.isAggressive;
    }

    public void setAggression(@NotNull boolean status) {
        this.isAggressive = status;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public boolean isDead() {
        return this.giantMobEntity.isDead();
    }

    public boolean isInWater() {
        return this.giantMobEntity.isInWater();
    }

    private LivingEntity findEntitiesNearby() {
        try {

            List<Entity> entityList = (List<Entity>) Objects.requireNonNull(this.giantMobEntity
                            .getLocation()
                            .getWorld())
                    .getNearbyEntities(this.giantMobEntity.getLocation(), 40, 40, 40);

            for (Entity entity : entityList) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    if (livingEntity instanceof Player) {
                        return (Player) livingEntity;
                    }

                    return livingEntity;
                }

                return null;
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private void attackEntity() {
        final Lanskoy lanskoy = this;

        try {
            LivingEntity target = this.findEntitiesNearby();
            LivingEntity livingLanskoy = (LivingEntity) lanskoy;

            assert target != null;
            if (!(lanskoy.isDead())) {
                livingLanskoy.getLocation().setDirection(target.getLocation().toVector());
                livingLanskoy.attack(target);
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private void startToMove() {
        final Lanskoy lanskoy = this;

        new BukkitRunnable() {
            @Override
            public void run() {
                LivingEntity livingLanskoy = lanskoy.giantMobEntity;

                if (lanskoy.isDead()) {
                    return;
                }

                Entity target = lanskoy.findEntitiesNearby();

                assert target != null;
                double xDiff = target.getLocation().getX() - livingLanskoy.getEyeLocation().getX();
                double yDiff = target.getLocation().getY() - livingLanskoy.getEyeLocation().getY();
                double zDiff = target.getLocation().getZ() - livingLanskoy.getEyeLocation().getZ();
                double distanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
                double distanceY = Math.sqrt(distanceXZ * distanceXZ + yDiff * yDiff);

                float newYaw = (float) Math.toDegrees(Math.atan2(zDiff, xDiff)) - 90;
                float newPitch = (float) Math.toDegrees(Math.acos(yDiff / distanceY)) - 90;

                livingLanskoy.setRotation(newYaw, newPitch);

                Location giantLoc = livingLanskoy.getLocation().clone();
                Location giantLocXZ = livingLanskoy.getLocation().clone();
                giantLocXZ.setY(0);
                double giantMobY = giantLoc.clone().getY();

                Location targetLoc = target.getLocation().clone();
                Location targetLocXZ = target.getLocation().clone();
                targetLocXZ.setY(0);
                double targetY = targetLoc.clone().getY();

                if ((giantLocXZ.distance(targetLocXZ) > 1) || ( ((targetY - giantMobY) > 20) && (giantMobY < targetY)) ) {
                    double oldX = giantLoc.getX();
                    double oldZ = giantLoc.getZ();


                    if (livingLanskoy.getEyeLocation().clone().add(0, 1, 0).getBlock().getType() == Material.WATER) {
                        livingLanskoy.setVelocity((giantLoc.getDirection().clone().normalize().setY(1.4)));
                        return;
                    }

                    livingLanskoy.setRemainingAir(livingLanskoy.getMaximumAir());
                    livingLanskoy.setVelocity(giantLoc.getDirection().clone().normalize().setY(-4.5));

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (livingLanskoy.isDead()) {
                                this.cancel();
                            }

                            if ((Math.abs(livingLanskoy.getLocation().getX() - oldX) < 1) && (Math.abs(livingLanskoy.getLocation().getZ() - oldZ) < 1)&& ((giantMobY < targetY) || (giantLocXZ.distance(targetLocXZ) > 2.5))) {

                                lanskoy.giantInOnePlaceTimes++;

                                if (lanskoy.giantInOnePlaceTimes <= 2) {
                                    livingLanskoy.setVelocity(
                                            giantLoc.getDirection().clone().normalize().multiply(1).setY(1)
                                    );
                                } else if (lanskoy.giantInOnePlaceTimes == 4) {
                                    livingLanskoy.setVelocity(
                                            giantLoc.getDirection().clone().normalize().multiply(1).setY(4)
                                    );
                                } else if (lanskoy.giantInOnePlaceTimes == 8) {
                                    livingLanskoy.setVelocity(
                                            giantLoc.getDirection().clone().normalize().rotateAroundY(0.9).multiply(2).setY(5)
                                    );
                                } else if (lanskoy.giantInOnePlaceTimes == 10) {
                                    livingLanskoy.setVelocity(
                                            giantLoc.getDirection().clone().normalize().rotateAroundY(-0.9).multiply(2).setY(5)
                                    );
                                } else if (lanskoy.giantInOnePlaceTimes == 12) {
                                    livingLanskoy.setVelocity(
                                            giantLoc.getDirection().clone().normalize().rotateAroundY(-0.9).multiply(2).setY(5)
                                    );
                                } else if (lanskoy.giantInOnePlaceTimes == 14) {
                                    livingLanskoy.setVelocity(
                                            giantLoc.getDirection().clone().normalize().rotateAroundY(0.9).multiply(2).setY(5)
                                    );
                                } else if (lanskoy.giantInOnePlaceTimes == 16) {
                                    livingLanskoy.setVelocity(
                                            giantLoc.getDirection().clone().normalize().rotateAroundY(-0.9).multiply(2).setY(5)
                                    );

                                    lanskoy.giantInOnePlaceTimes = 0;
                                }
                            } else {
                                lanskoy.giantInOnePlaceTimes = 0;
                            }
                        }
                    }.runTaskLater(Plugin.getPlugin(Plugin.class), 7);
                }

            }

        }.runTaskTimer(Plugin.getPlugin(Plugin.class), 0, 10);
    }
}
