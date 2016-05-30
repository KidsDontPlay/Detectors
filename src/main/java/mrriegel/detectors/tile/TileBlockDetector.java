package mrriegel.detectors.tile;

import mrriegel.detectors.block.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileBlockDetector extends TileBase implements ITickable {

	@Override
	public void update() {
		if (worldObj.isRemote || worldObj.getTotalWorldTime() % 5 != 0)
			return;
		boolean validAll = true;
		boolean validOne = false;
		blockPosSet.remove(pos);
		boolean minimum = false;
		for (BlockPos p : blockPosSet) {
			minimum = true;
			if (all && !stateEqual(worldObj.getBlockState(p))) {
				validAll = false;
				break;
			} else if (!all && stateEqual(worldObj.getBlockState(p))) {
				validOne = true;
				break;
			}
		}
		validAll = validAll && minimum;
		validOne = validOne && minimum;
		boolean x = all ? validAll : validOne;
		if (worldObj.getBlockState(pos).getValue(BlockBase.STATE).booleanValue() != x) {
			((BlockBase) worldObj.getBlockState(pos).getBlock()).setState(worldObj, pos, worldObj.getBlockState(pos), x);
			syncWithClient();
		}
	}

	private boolean stateEqual(IBlockState state) {
		if (stack != null && stack.getItem() instanceof ItemBlock) {
			Block block = ((ItemBlock) stack.getItem()).block;
			boolean metaEqual = stack.getItemDamage() == state.getBlock().damageDropped(state) && block == state.getBlock();
			IBlockState x = block.onBlockPlaced(worldObj, BlockPos.ORIGIN, EnumFacing.NORTH, 0, 0, 0, stack.getItem().getMetadata(stack.getItemDamage()), new EntityCow(worldObj));
			return x.equals(state) || metaEqual;
		}
		return false;
	}

	@Override
	public boolean useBlockPosSet() {
		return true;
	}

	@Override
	public boolean useUUIDSet() {
		return false;
	}

}
