package mrriegel.unknown.block;

import mrriegel.unknown.tile.TileGrowthChecker;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGrowthChecker extends BlockBase {

	public BlockGrowthChecker() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileGrowthChecker();
	}

	@Override
	public String getName() {
		return "growthchecker";
	}

}
