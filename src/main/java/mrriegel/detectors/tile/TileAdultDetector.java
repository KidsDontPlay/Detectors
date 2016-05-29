package mrriegel.detectors.tile;

import java.util.List;

import mrriegel.detectors.block.BlockBase;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileAdultDetector extends TileBase implements ITickable {

	@Override
	public void update() {
		if (worldObj.isRemote || worldObj.getTotalWorldTime() % 5 != 0)
			return;
		boolean on = true;
		List<EntityAgeable> lis = worldObj.getEntitiesWithinAABB(EntityAgeable.class, new AxisAlignedBB(pos.add(range+.5, range+.5, range+.5), pos.add(-range+.5, -range+.5, -range+.5)));
		if (lis.isEmpty())
			on = false;
		if (visible)
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

	@Override
	public boolean useBlockPosSet() {
		return false;
	}

	@Override
	public boolean useUUIDSet() {
		return true;
	}
}
