package me.zqr0.hugelanskoy.Lanskoy;

import me.zqr0.hugelanskoy.Plugin;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Lanskoy{

    private boolean isAggressive;
    private final Giant giantMobEntity;
    private Location playerLocation;
    private UUID uuid;
    private final String NAME = "Lanskoy";
    private int giantInOnePlaceTimes;

    public Lanskoy(@NotNull Location playerLocation, boolean isAggressive) {
        this.isAggressive = isAggressive;
        this.playerLocation = playerLocation;

        Vector playerDirection = playerLocation.getDirection();
        playerDirection.setY(0);
        playerDirection.normalize();

        this.giantMobEntity = (Giant) Objects.requireNonNull(playerLocation.getWorld()).spawnEntity(
                playerLocation.clone().add(
                        playerDirection.clone().multiply(5)
                ).setDirection(playerDirection.multiply(-1)), EntityType.GIANT);

        this.uuid = this.giantMobEntity.getUniqueId();

        this.giantMobEntity.setCustomName(this.NAME);
        this.giantMobEntity.setCustomNameVisible(true);
        Objects.requireNonNull(this.giantMobEntity.getEquipment()).setItemInMainHand(new ItemStack(Material.IRON_SWORD));

        this.startLanskoy();
    }

   public void startLanskoy() {
        this.startToMove();
        this.attackEntity();
   }


    public boolean isDead() {
        return this.giantMobEntity.isDead();
    }


    private Vector generateVector(@NotNull Location a, @NotNull Location b) {
        return locationToVector(b).clone().subtract(locationToVector(a)).normalize();
    }

    private Vector locationToVector(@NotNull Location location) {
        return new Vector(location.getX(), location.getY(), location.getZ());
    }

    private LivingEntity findTargetToAttack() {

        try {
            List<Entity> entityList = (List<Entity>) Objects.requireNonNull(this.giantMobEntity
                            .getLocation()
                            .getWorld())
                    .getNearbyEntities(this.giantMobEntity.getLocation(), 40, 40, 40);

            List<Player> playersList = new ArrayList<>();
            List<LivingEntity> otherEntitiesList = new ArrayList<>();

            for (Entity entity : entityList) {
                RayTraceResult rts1;
                RayTraceResult rts2;
                RayTraceResult rts3;
                RayTraceResult rts4;
                RayTraceResult rts5;
                RayTraceResult rts6;
                RayTraceResult rts7;
                RayTraceResult rts8;

                if (entity instanceof LivingEntity) {
                    Location lanskoyEyesPos1 = this.giantMobEntity.getLocation().clone().add(2, 11, 0);
                    Location lanskoyEyesPos2 = this.giantMobEntity.getLocation().clone().add(-2, 11, 0);
                    Location lanskoyEyesPos3 = this.giantMobEntity.getLocation().clone().add(0, 11, 2);
                    Location lanskoyEyesPos4 = this.giantMobEntity.getLocation().clone().add(0, 11, -2);

                    rts1 = giantMobEntity.getLocation().getWorld().rayTraceBlocks(lanskoyEyesPos1, generateVector(lanskoyEyesPos1, ((LivingEntity) entity).getEyeLocation()), lanskoyEyesPos1.distance(((LivingEntity) entity).getEyeLocation()), FluidCollisionMode.NEVER, true);
                    rts2 = giantMobEntity.getLocation().getWorld().rayTraceBlocks(lanskoyEyesPos2, generateVector(lanskoyEyesPos2, ((LivingEntity) entity).getEyeLocation()), lanskoyEyesPos2.distance(((LivingEntity) entity).getEyeLocation()), FluidCollisionMode.NEVER, true);
                    rts3 = giantMobEntity.getLocation().getWorld().rayTraceBlocks(lanskoyEyesPos3, generateVector(lanskoyEyesPos3, ((LivingEntity) entity).getEyeLocation()), lanskoyEyesPos3.distance(((LivingEntity) entity).getEyeLocation()), FluidCollisionMode.NEVER, true);
                    rts4 = giantMobEntity.getLocation().getWorld().rayTraceBlocks(lanskoyEyesPos4, generateVector(lanskoyEyesPos4, ((LivingEntity) entity).getEyeLocation()), lanskoyEyesPos4.distance(((LivingEntity) entity).getEyeLocation()), FluidCollisionMode.NEVER, true);

                    rts5 = giantMobEntity.getLocation().getWorld().rayTraceBlocks(lanskoyEyesPos1, generateVector(lanskoyEyesPos1, entity.getLocation()), lanskoyEyesPos1.distance(entity.getLocation()), FluidCollisionMode.NEVER, true);
                    rts6 = giantMobEntity.getLocation().getWorld().rayTraceBlocks(lanskoyEyesPos2, generateVector(lanskoyEyesPos2, entity.getLocation()), lanskoyEyesPos2.distance(entity.getLocation()), FluidCollisionMode.NEVER, true);
                    rts7 = giantMobEntity.getLocation().getWorld().rayTraceBlocks(lanskoyEyesPos3, generateVector(lanskoyEyesPos3, entity.getLocation()), lanskoyEyesPos3.distance(entity.getLocation()), FluidCollisionMode.NEVER, true);
                    rts8 = giantMobEntity.getLocation().getWorld().rayTraceBlocks(lanskoyEyesPos4, generateVector(lanskoyEyesPos4, entity.getLocation()), lanskoyEyesPos4.distance(entity.getLocation()), FluidCollisionMode.NEVER, true);


                    if ((rts1 == null) || (rts2 == null) || (rts3 == null) || (rts4 == null) || (rts5 == null) || (rts6 == null) || (rts7 == null) || (rts8 == null)) {
                        if ((entity instanceof Player) && (!(((Player) entity).getGameMode() == GameMode.SPECTATOR))) {
                            playersList.add((Player) entity);
                        } else if (!(entity instanceof Player)) {
                            otherEntitiesList.add((LivingEntity) entity);
                        }
                    }
                }
            }

            LivingEntity target = null;
            Double minDistance = null;

            if (!(playersList.isEmpty())) {

                for (Player player : playersList) {
                    if (target == null) {
                        target = player;
                        minDistance = this.giantMobEntity.getLocation().distance(player.getLocation());
                    } //else if (minDistance > this.giantMobEntity.getLocation().distance(player.getLocation())) {
//                        target = player;
//                        minDistance = this.giantMobEntity.getLocation().distance(player.getLocation());
//                    }
                }
            }

            if (!(otherEntitiesList.isEmpty())) {
                for (LivingEntity entity : otherEntitiesList) {
                    if (target == null) {
                        target = entity;
                        minDistance = this.giantMobEntity.getLocation().distance(entity.getLocation());
                    } //else if (minDistance > this.giantMobEntity.getLocation().distance(entity.getLocation())) {
//                        target = entity;
//                        minDistance = this.giantMobEntity.getLocation().distance(entity.getLocation());
//                    }
                }
            }
            return target;

        } catch (NullPointerException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    private void attackEntity() {
        final Lanskoy lanskoy = this;
        LivingEntity target = this.findTargetToAttack();

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    LivingEntity livingLanskoy = lanskoy.giantMobEntity;

                    if (livingLanskoy.isDead()) {
                        return;
                    }

                    if (target != null) {
                        Fireball fireball = (Fireball) livingLanskoy.getWorld().spawnEntity(livingLanskoy.getEyeLocation().clone()
                                        .add(generateVector(livingLanskoy.getEyeLocation().clone(), target.getLocation()).clone().multiply(3.5)).clone()
                                        .add(0, -2, 0),
                                EntityType.FIREBALL
                        );
                        fireball.setDirection(generateVector(fireball.getLocation(), target.getLocation()).clone().multiply(2));
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        }.runTaskTimer(Plugin.getPlugin(Plugin.class), 0, 45);
    }

    private void startToMove() {
        final Lanskoy lanskoy = this;
        LivingEntity target = this.findTargetToAttack();

        new BukkitRunnable() {
            @Override
            public void run() {
                LivingEntity livingLanskoy = lanskoy.giantMobEntity;

                if (lanskoy.isDead()) {
                    return;
                }

                if (target == null) {
                    if (!(livingLanskoy.getEyeLocation().clone().add(0, 2, 0).getBlock().getType() == Material.WATER)) {
                        livingLanskoy.setVelocity(livingLanskoy.getLocation().getDirection().clone().normalize().setY(-4.5));
                    } else {
                        livingLanskoy.setVelocity(livingLanskoy.getLocation().getDirection().clone().normalize().setY(3));
                    }
                    return;
                }


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

                if ((giantLocXZ.distance(targetLocXZ) > 1) || (((targetY - giantMobY) > 20) && (giantMobY < targetY))) {
                    double oldX = giantLoc.getX();
                    double oldZ = giantLoc.getZ();

                    if (livingLanskoy.getEyeLocation().clone().add(0, 1, 0).getBlock().getType() == Material.WATER) {
                        livingLanskoy.setVelocity((giantLoc.getDirection().clone().normalize().setY(1.4)));
                        return;
                    } else {
                        livingLanskoy.setRemainingAir(livingLanskoy.getMaximumAir());
                        livingLanskoy.setVelocity(giantLoc.getDirection().clone().normalize().setY(-4.5));
                    }

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (livingLanskoy.isDead()) {
                                this.cancel();
                            }

                            if ((Math.abs(livingLanskoy.getLocation().getX() - oldX) < 1) && (Math.abs(livingLanskoy.getLocation().getZ() - oldZ) < 1) && ((giantMobY < targetY) || (giantLocXZ.distance(targetLocXZ) > 2.5))) {

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
