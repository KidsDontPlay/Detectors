package mrriegel.detectors.tile;

import mrriegel.detectors.block.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileRainDetector extends TileBase implements ITickable {

	@Override
	public void update() {
		if (worldObj.isRemote || worldObj.getTotalWorldTime() % 5 != 0)
			return;

		boolean x = worldObj.isRaining();
		if (worldObj.getBlockState(pos).getValue(BlockBase.STATE).booleanValue() != x) {
			((BlockBase) worldObj.getBlockState(pos).getBlock()).setState(worldObj, pos, worldObj.getBlockState(pos), x);
			syncWithClient();
		}
	}

	@Override
	public boolean useBlockPosSet() {
		return false;
	}

	@Override
	public boolean useUUIDSet() {
		return false;
	}

}
