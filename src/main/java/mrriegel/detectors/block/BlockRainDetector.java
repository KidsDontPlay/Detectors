package mrriegel.detectors.block;

import mrriegel.detectors.tile.TileRainDetector;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRainDetector extends BlockBase{

	public BlockRainDetector() {
		super(Material.IRON);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileRainDetector();
	}

	@Override
	public String getName() {
		return "rainDetector";
	}

}
