package mrriegel.detectors;

import mrriegel.detectors.block.BlockAnimalChecker;
import mrriegel.detectors.block.BlockBase;
import mrriegel.detectors.block.BlockBlockDetector;
import mrriegel.detectors.block.BlockCropDetector;
import mrriegel.detectors.block.BlockRainDetector;
import mrriegel.detectors.tile.TileBlockDetector;
import mrriegel.detectors.tile.TileCropDetector;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static final BlockBase crop = new BlockCropDetector();
	public static final BlockBase animal = new BlockAnimalChecker();
	public static final BlockBase block = new BlockBlockDetector();
	public static final BlockBase rain = new BlockRainDetector();

	public static void init() {
		register(crop);
		// register(animal);
		register(block);
		register(rain);
	}

	public static void initModels() {
		crop.initModel();
		// animal.initModel();
		block.initModel();
		rain.initModel();
	}

	private static void register(BlockBase block) {
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		GameRegistry.registerTileEntity(block.createNewTileEntity(null, 0).getClass(), "tile_" + block.getName());
	}
}
