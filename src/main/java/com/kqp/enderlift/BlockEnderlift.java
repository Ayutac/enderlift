package com.kqp.enderlift;

import java.util.Random;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class BlockEnderlift extends Block {
    private static final Random RANDOM = new Random();

    public BlockEnderlift() {
        super(FabricBlockSettings.of(Material.STONE).strength(15.0F, 1200.0F).build());
    }

    public static void playNoise(World world, PlayerEntity player) {
        world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.BLOCKS, 0.5F, 0.4F / (RANDOM.nextFloat() * 0.4F + 0.8F));
    }

    public ActionResult playerInteract(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        BlockPos other = findOthers(world, hitResult.getBlockPos(), Enderlift.config.range * (player.isSneaking() ? -1 : 1));

        if (hitResult.getSide() == Direction.UP && other != null) {
            if (player.getX() > other.getX() && player.getZ() > other.getZ() && player.getX() < other.getX() + 1 && player.getZ() < other.getZ() + 1) {
                BlockEnderlift.playNoise(world, player);

                if (!world.isClient) {
                    player.requestTeleport(player.getX(), other.getY() + 1, player.getZ());
                }

                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }

    private static BlockPos findOthers(World world, BlockPos pos, int range) {
        BlockPos foundPos = null;

        int start = pos.getY() + (range > 0 ? 1 : -1);
        int end = pos.getY() + range;

        for (int i = start; i != end; i += Math.signum(range)) {
            BlockPos current = new BlockPos(pos.getX(), i, pos.getZ());
            BlockState bs = world.getBlockState(current);
            
            if (bs.getBlock() instanceof BlockEnderlift) {
                foundPos = current;
                break;
            }
        }

        return foundPos;
    }
}