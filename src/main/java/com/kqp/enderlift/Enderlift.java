package com.kqp.enderlift;

import com.kqp.enderlift.block.EnderliftBlock;
import com.kqp.enderlift.event.playeraction.PlayerActionEvent;

import com.kqp.enderlift.recipe.EnderliftRecipe;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.world.WorldTickCallback;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Enderlift implements ModInitializer {
	public static final EnderliftBlock[] ENDERLIFT_BLOCKS = new EnderliftBlock[EnderliftColor.values().length];
	public static final Identifier ENDERLIFT_RECIPE_ID = new Identifier("enderlift:crafting_enderlift");

	public static EnderliftConfig config;

	@Override
	public void onInitialize() {
		System.out.println("Initializing Enderlift");

		config = EnderliftConfig.load();

		for (int i = 0; i < EnderliftColor.values().length; i++) {
			EnderliftColor color = EnderliftColor.values()[i];
			ENDERLIFT_BLOCKS[i] = new EnderliftBlock(color.color);

			Registry.register(Registry.BLOCK, new Identifier("enderlift", color.name + "enderlift"), ENDERLIFT_BLOCKS[i]);
			Registry.register(Registry.ITEM, new Identifier("enderlift", color.name + "enderlift"), new BlockItem(ENDERLIFT_BLOCKS[i], new Item.Settings().group(ItemGroup.TRANSPORTATION)));
		}

		Registry.register(Registry.RECIPE_SERIALIZER, ENDERLIFT_RECIPE_ID, new EnderliftRecipe());

		WorldTickCallback.EVENT.register(PlayerActionEvent::onWorldTick);
		PlayerActionEvent.register(EnderliftBlock::playerAction);
	}

	public static EnderliftBlock getMatchingBlock(MaterialColor color) {
		for (EnderliftBlock block : ENDERLIFT_BLOCKS) {
			if (block.getMaterial(null).getColor() == color) {
				return block;
			}
		}

		return null;
	}
}
