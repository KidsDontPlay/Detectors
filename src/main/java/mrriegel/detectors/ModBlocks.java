package mrriegel.detectors;

import mrriegel.detectors.block.BlockAnimalChecker;
import mrriegel.detectors.block.BlockBase;
import mrriegel.detectors.block.BlockCropDetector;
import mrriegel.detectors.tile.TileAnimalChecker;
import mrriegel.detectors.tile.TileCropDetector;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

public class ModBlocks {
	public static final BlockBase crop = new BlockCropDetector();
	public static final BlockBase animal = new BlockAnimalChecker();

	public static void init() {
		register(crop);
//		register(animal);

		GameRegistry.registerTileEntity(TileCropDetector.class, "tileCropDetector");
//		GameRegistry.registerTileEntity(TileAnimalChecker.class, "animal");
	}

	public static void initModels() {
		crop.initModel();
//		animal.initModel();
	}

	private static void register(BlockBase block) {
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}
}
