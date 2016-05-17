package mrriegel.detectors.tile;

import mrriegel.detectors.block.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileCropDetector extends TileBase implements ITickable {

	@Override
	public void update() {
		if (worldObj.isRemote || worldObj.getTotalWorldTime() % 5 != 0)
			return;
		boolean validAll = true;
		boolean validOne = false;
		set.remove(pos);
		boolean minimum = false;
		for (BlockPos p : set) {
			Block b = worldObj.getBlockState(p).getBlock();
			if (b instanceof IGrowable) {
				minimum = true;
				if (all && ((IGrowable) b).canGrow(worldObj, p, worldObj.getBlockState(p), false)) {
					validAll = false;
					break;
				} else if (!all && !((IGrowable) b).canGrow(worldObj, p, worldObj.getBlockState(p), false)) {
					validOne = true;
					break;
				}
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

	@Override
	public boolean useBlockPosSet() {
		return true;
	}
}
