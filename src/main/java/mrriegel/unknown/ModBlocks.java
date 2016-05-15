package mrriegel.unknown;

import mrriegel.unknown.block.BlockAnimalChecker;
import mrriegel.unknown.block.BlockGrowthChecker;
import mrriegel.unknown.tile.TileAnimalChecker;
import mrriegel.unknown.tile.TileGrowthChecker;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static final Block growth = new BlockGrowthChecker();
	public static final Block animal = new BlockAnimalChecker();

	public static void init() {
		register(growth);
		register(animal);
		
		GameRegistry.registerTileEntity(TileGrowthChecker.class, "growtg");
		GameRegistry.registerTileEntity(TileAnimalChecker.class, "animal");
	}

	private static void register(Block block) {
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}
}
