package mrriegel.unknown.block;

import mrriegel.unknown.tile.TileAnimalChecker;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAnimalChecker extends BlockBase {

	public BlockAnimalChecker() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return (TileEntity) new TileAnimalChecker();
	}

	@Override
	public String getName() {
		return "animalchecker";
	}

}
