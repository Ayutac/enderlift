package com.kqp.enderlift.recipe;

import com.kqp.enderlift.Enderlift;
import com.kqp.enderlift.block.EnderliftBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.world.World;

public class EnderliftRecipe extends SpecialCraftingRecipe {
    private static final int EYE_SLOT = 4;
    private static final int[] OBSIDIAN_SLOTS = { 0, 2, 6, 8 };
    private static final int[] WOOL_SLOTS = { 1, 3, 5, 7 };

    public EnderliftRecipe() {
        super(Enderlift.ENDERLIFT_RECIPE_ID);
    }

    public boolean matches(CraftingInventory inv, World world) {
        if (inv.getWidth() == 3 && inv.getHeight() == 3) {
            if (inv.getInvStack(EYE_SLOT).getItem() != Items.ENDER_EYE) {
                return false;
            }

            for (int slot : OBSIDIAN_SLOTS) {
                if (inv.getInvStack(slot).getItem() != Items.OBSIDIAN) {
                    return false;
                }
            }

            MaterialColor color = null;

            for (int slot : WOOL_SLOTS) {
                ItemStack is = inv.getInvStack(slot);

                if (is.getItem() instanceof BlockItem) {
                    Block block = ((BlockItem) is.getItem()).getBlock();
                    Material mat = block.getMaterial(null);

                    if (mat == Material.WOOL && block.getName().asString().toLowerCase().contains("wool")) {
                        MaterialColor currColor = mat.getColor();

                        if (color == null) {
                            color = currColor;
                        } else {
                            if (color != currColor) {
                                return false;
                            }
                        }
                    }
                }
            }

            if (Enderlift.getMatchingBlock(color) == null) {
                return false;
            }

            return true;
        }

        return false;
    }

    public ItemStack craft(CraftingInventory inv) {
        if (inv.getWidth() == 3 && inv.getHeight() == 3) {
            if (inv.getInvStack(EYE_SLOT).getItem() != Items.ENDER_EYE) {
                return ItemStack.EMPTY;
            }

            for (int slot : OBSIDIAN_SLOTS) {
                if (inv.getInvStack(slot).getItem() != Items.OBSIDIAN) {
                    return ItemStack.EMPTY;
                }
            }

            MaterialColor color = null;

            for (int slot : WOOL_SLOTS) {
                ItemStack is = inv.getInvStack(slot);

                if (is.getItem() instanceof BlockItem) {
                    Block block = ((BlockItem) is.getItem()).getBlock();
                    Material mat = block.getMaterial(null);

                    if (mat == Material.WOOL && block.getName().asString().toLowerCase().contains("wool")) {
                        MaterialColor currColor = mat.getColor();

                        if (color == null) {
                            color = currColor;
                        } else {
                            if (color != currColor) {
                                return ItemStack.EMPTY;
                            }
                        }
                    }
                }
            }

            EnderliftBlock block = Enderlift.getMatchingBlock(color);

            if (Enderlift.getMatchingBlock(color) == null) {
                return ItemStack.EMPTY;
            } else {
                return new ItemStack(block);
            }
        }

        return ItemStack.EMPTY;
    }

    public boolean fits(int width, int height) {
        return width == 3 && height == 3;
    }

    public RecipeSerializer<?> getSerializer() {
        return null;
    }
}
