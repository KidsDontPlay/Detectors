package mrriegel.detectors.tile;

import java.util.List;

import mrriegel.detectors.block.BlockBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TilePlayerDetector extends TileBase implements ITickable {

	@Override
	public void update() {
		if (worldObj.isRemote || worldObj.getTotalWorldTime() % 5 != 0)
			return;
		double w = .5;
		AxisAlignedBB aabb = new AxisAlignedBB(pos.getX() - range + w, pos.getY() - range + w, pos.getZ() - range + w, pos.getX() + range + w, pos.getY() + range + w, pos.getZ() + range + w);
		List<EntityPlayer> lis = worldObj.getEntitiesWithinAABB(EntityPlayer.class, aabb);
		if (visible)
			for (EntityPlayer p : lis)
				p.addPotionEffect(new PotionEffect(Potion.getPotionById(24), 8, 0));
		boolean on = op.match(lis.size(), number);
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
