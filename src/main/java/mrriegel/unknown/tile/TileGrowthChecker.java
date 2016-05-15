package mrriegel.unknown.tile;

import mrriegel.unknown.block.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileGrowthChecker extends TileBase implements ITickable {

	private boolean all = true;

	@Override
	public void readSyncableData(NBTTagCompound compound) {
		super.readSyncableData(compound);
		this.all = compound.getBoolean("all");
	}

	@Override
	public void writeSyncableData(NBTTagCompound compound) {
		super.writeSyncableData(compound);
		compound.setBoolean("all", all);
	}

	@Override
	public void update() {
		if (worldObj.isRemote || worldObj.getTotalWorldTime() % 5 != 0)
			return;
		boolean on = true;
		set.remove(pos);
		boolean minimum = false;
		for (BlockPos p : set) {
			Block b = worldObj.getBlockState(p).getBlock();
			if (b instanceof IGrowable) {
				minimum = true;
				if (all && ((IGrowable) b).canGrow(worldObj, p, worldObj.getBlockState(p), false)) {
					on = false;
					break;
				} else if (!all && !((IGrowable) b).canGrow(worldObj, p, worldObj.getBlockState(p), false)) {
					break;
				}
			}
		}
		on = on && minimum;
		if (worldObj.getBlockState(pos).getValue(BlockBase.STATE).booleanValue() != on) {
			((BlockBase) worldObj.getBlockState(pos).getBlock()).setState(worldObj, pos, worldObj.getBlockState(pos), on);
			syncWithClient();
		}
	}
}
