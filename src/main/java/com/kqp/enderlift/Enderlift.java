package com.kqp.enderlift;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Enderlift implements ModInitializer {
	public static final BlockEnderlift ENDERLIFT = new BlockEnderlift();

	public static EnderliftConfig config;

	@Override
	public void onInitialize() {
		System.out.println("Initializing Enderlift");

		config = EnderliftConfig.load();		

		Registry.register(Registry.BLOCK, new Identifier("enderlift", "enderlift"), ENDERLIFT);
		Registry.register(Registry.ITEM, new Identifier("enderlift", "enderlift"), new BlockItem(ENDERLIFT, new Item.Settings().group(ItemGroup.TRANSPORTATION)));

		UseBlockCallback.EVENT.register(ENDERLIFT::playerInteract);
	}
}
