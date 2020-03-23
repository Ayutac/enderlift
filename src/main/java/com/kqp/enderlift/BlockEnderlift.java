package com.kqp.enderlift;

import java.util.Random;

import com.kqp.enderlift.event.playeraction.Action;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlockEnderlift extends Block {
    private static final Random RANDOM = new Random();

    public BlockEnderlift() {
        super(FabricBlockSettings.of(Material.STONE).strength(15.0F, 1200.0F).build());
    }

    public static void playNoise(World world, PlayerEntity player) {
        world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.BLOCKS, 0.5F, 0.4F / (RANDOM.nextFloat() * 0.4F + 0.8F));
    }

    public void playerAction(PlayerEntity player, World world, Action action) {
        BlockPos standingBlock = player.getBlockPos().down();

        if (world.getBlockState(standingBlock).getBlock() instanceof BlockEnderlift) {
            int direction = action == Action.JUMP ? 1 : -1;
            BlockPos other = findOthers(world, standingBlock, Enderlift.config.range * direction);

            if (other != null) {
                if (!world.isClient) {
                    boolean allow = true;

                    int redstoneType = Enderlift.config.redstoneType;
                    boolean on = world.isReceivingRedstonePower(standingBlock);
                    if (redstoneType == 1) {
                        allow = on;
                    } else if (redstoneType == 2) {
                        allow = !on;
                    }

                    int xpCost = Enderlift.config.xpCost;
                    if (allow && xpCost != 0) {
                        if (player.totalExperience >= xpCost) {
                            allow = true;
                            player.addExperience(-xpCost);
                        } else {
                            allow = false;
                        }
                    }

                    if (allow) {
                        int damage = Enderlift.config.damage;
                        if (damage != 0) {
                            player.damage(DamageSource.MAGIC, damage);
                        }

                        player.requestTeleport(player.getX(), other.getY() + 1, player.getZ());
                        BlockEnderlift.playNoise(world, player);
                        Vec3d vec3d = player.getVelocity();
                        player.setVelocity(vec3d.x, 0.0D, vec3d.z);
                    }
                }
            }
        }
    }

    private static BlockPos findOthers(World world, BlockPos pos, int range) {
        BlockPos foundPos = null;

        int start = pos.getY() + (range > 0 ? 1 : -1);
        int end = pos.getY() + range;

        boolean woolMatch = Enderlift.config.woolMatch;

        for (int i = start; i != end; i += Math.signum(range)) {
            BlockPos current = new BlockPos(pos.getX(), i, pos.getZ());
            BlockState bs = world.getBlockState(current);
            
            if (bs.getBlock() instanceof BlockEnderlift) {
                if (woolMatch) {
                    Block b1 = world.getBlockState(current.down()).getBlock();
                    Block b2 = world.getBlockState(pos.down()).getBlock();

                    if ((isWool(b1) && isWool(b2) && b1 == b2) || (!isWool(b1) || !isWool(b2))) {
                        foundPos = current;
                        break;
                    }
                } else {
                    foundPos = current;
                    break;
                }
            }
        }

        return foundPos;
    }

    private static boolean isWool(Block b) {
        return b.getName().asString().toLowerCase().contains("wool");
    }
}