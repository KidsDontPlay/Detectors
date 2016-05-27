package mrriegel.detectors.block;

import mrriegel.detectors.tile.TileAnimalChecker;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAnimalChecker extends BlockBase {

	public BlockAnimalChecker() {
		super(Material.IRON);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileAnimalChecker();
	}

	@Override
	public String getName() {
		return "animalchecker";
	}

}
