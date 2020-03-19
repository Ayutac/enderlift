package com.kqp.enderlift;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

public class Enderlift implements ModInitializer {
	public static final Block ENDERLIFT = new BlockEnderlift();

	@Override
	public void onInitialize() {
		System.out.println("Initializing Enderlift");

		Registry.register(Registry.BLOCK, new Identifier("enderlift", "enderlift"), ENDERLIFT);
		Registry.register(Registry.ITEM, new Identifier("enderlift", "enderlift"), new BlockItem(ENDERLIFT, new Item.Settings().group(ItemGroup.TRANSPORTATION)));

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			BlockPos other = BlockEnderlift.findOthers(world, hitResult.getBlockPos(), 256 * (player.isSneaking() ? -1 : 1));

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
		});
	}
}
