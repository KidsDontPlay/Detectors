package mrriegel.detectors.block;

import mrriegel.detectors.tile.TileAnimalChecker;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

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
