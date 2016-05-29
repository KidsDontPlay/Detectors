package mrriegel.detectors;

import mrriegel.detectors.block.BlockAdultDetector;
import mrriegel.detectors.block.BlockBase;
import mrriegel.detectors.block.BlockBlockDetector;
import mrriegel.detectors.block.BlockCropDetector;
import mrriegel.detectors.block.BlockItemDetector;
import mrriegel.detectors.block.BlockMobDetector;
import mrriegel.detectors.block.BlockRainDetector;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static final BlockBase crop = new BlockCropDetector();
	public static final BlockBase adult = new BlockAdultDetector();
	public static final BlockBase block = new BlockBlockDetector();
	public static final BlockBase rain = new BlockRainDetector();
	public static final BlockBase mob = new BlockMobDetector();
	public static final BlockBase item = new BlockItemDetector();

	public static void init() {
		register(crop);
		register(adult);
		register(block);
		register(rain);
		register(mob);
		register(item);
	}

	public static void initModels() {
		crop.initModel();
		adult.initModel();
		block.initModel();
		rain.initModel();
		mob.initModel();
		item.initModel();
	}

	private static void register(BlockBase block) {
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		GameRegistry.registerTileEntity(block.createNewTileEntity(null, 0).getClass(), "tile_" + block.getName());
	}
}
