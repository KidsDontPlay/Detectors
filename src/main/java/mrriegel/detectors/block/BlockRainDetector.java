package mrriegel.detectors.block;

import mrriegel.detectors.tile.TileRainDetector;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRainDetector extends BlockBase {

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileRainDetector();
	}

	@Override
	public String getName() {
		return "rainDetector";
	}

}
