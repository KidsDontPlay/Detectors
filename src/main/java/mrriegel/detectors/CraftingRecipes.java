package mrriegel.detectors;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CraftingRecipes {

	public static void init() {
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.edit), " r ", "pip", " r ", 'r', Items.REDSTONE, 'p', Items.PAPER, 'i', Items.IRON_INGOT);
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.crop), "izi", "zrz", "izi", 'i', Items.IRON_INGOT, 'r', Blocks.REDSTONE_BLOCK, 'z', Items.WHEAT);
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.rain), "izi", "zrz", "izi", 'i', Items.IRON_INGOT, 'r', Blocks.REDSTONE_BLOCK, 'z', new ItemStack(Items.POTIONITEM));
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.mob), "izi", "zrz", "izi", 'i', Items.IRON_INGOT, 'r', Blocks.REDSTONE_BLOCK, 'z', Items.SUGAR);
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.item), "izi", "zrz", "izi", 'i', Items.IRON_INGOT, 'r', Blocks.REDSTONE_BLOCK, 'z', Blocks.PLANKS);
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.fluid), "izi", "zrz", "izi", 'i', Items.IRON_INGOT, 'r', Blocks.REDSTONE_BLOCK, 'z', Blocks.GLASS);
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.block), "izi", "zrz", "izi", 'i', Items.IRON_INGOT, 'r', Blocks.REDSTONE_BLOCK, 'z', Blocks.STONEBRICK);
	}

}
