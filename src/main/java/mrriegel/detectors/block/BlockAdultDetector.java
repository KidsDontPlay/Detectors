package mrriegel.detectors.block;

import mrriegel.detectors.Detectors;
import mrriegel.detectors.GuiHandler;
import mrriegel.detectors.tile.TileAdultDetector;
import mrriegel.detectors.tile.TileCropDetector;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAdultDetector extends BlockBase {

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileAdultDetector();
	}

	@Override
	public String getName() {
		return "adultDetector";
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;

		} else {
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileAdultDetector) {
				playerIn.openGui(Detectors.instance, GuiHandler.adult, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		}
	}

}
