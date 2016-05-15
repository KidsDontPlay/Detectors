package mrriegel.unknown.tile;

import java.util.List;

import mrriegel.unknown.block.BlockAnimalChecker;
import mrriegel.unknown.block.BlockBase;
import mrriegel.unknown.block.BlockGrowthChecker;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class TileAnimalChecker extends TileBase implements ITickable {

	private int range = 10;
	private boolean all = true;

	@Override
	public void readSyncableData(NBTTagCompound compound) {
		super.readSyncableData(compound);
		this.range = compound.getInteger("range");
		this.all = compound.getBoolean("all");
	}

	@Override
	public void writeSyncableData(NBTTagCompound compound) {
		super.writeSyncableData(compound);
		compound.setInteger("range", range);
		compound.setBoolean("all", all);
	}

	@Override
	public void update() {
		if (worldObj.isRemote || worldObj.getTotalWorldTime() % 5 != 0)
			return;
		boolean on = true;
		List<EntityAgeable> lis = worldObj.getEntitiesWithinAABB(EntityAgeable.class, new AxisAlignedBB(pos.add(range, range, range), pos.add(-range, -range, -range)));
		if (lis.isEmpty())
			on = false;
		for (EntityAgeable p : lis)
			p.addPotionEffect(new PotionEffect(Potion.getPotionById(24), 8, 0));
		for (EntityAgeable p : lis) {
			if (all && p.isChild()) {
				on = false;
				break;
			} else if (!all && !p.isChild()) {
				break;
			}
		}
		if (worldObj.getBlockState(pos).getValue(BlockBase.STATE).booleanValue() != on) {
			((BlockBase) worldObj.getBlockState(pos).getBlock()).setState(worldObj, pos, worldObj.getBlockState(pos), on);
			syncWithClient();
		}
	}
}
