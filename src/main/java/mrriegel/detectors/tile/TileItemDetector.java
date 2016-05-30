package mrriegel.detectors.tile;

import java.util.Set;

import mrriegel.detectors.block.BlockBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import com.google.common.collect.Sets;

public class TileItemDetector extends TileBase implements ITickable {

	@Override
	public void update() {
		if (worldObj.isRemote || worldObj.getTotalWorldTime() % 5 != 0)
			return;
		int num = 0;
		for (BlockPos pos : blockPosSet)
			num += inInventory(stack, pos);
		boolean x = op.match(num, number);
		if (worldObj.getBlockState(pos).getValue(BlockBase.STATE).booleanValue() != x) {
			((BlockBase) worldObj.getBlockState(pos).getBlock()).setState(worldObj, pos, worldObj.getBlockState(pos), x);
			syncWithClient();
		}

	}

	@Override
	public boolean useBlockPosSet() {
		return true;
	}

	@Override
	public boolean useUUIDSet() {
		return false;
	}

	private int inInventory(ItemStack stack, BlockPos pos) {
		if (worldObj.getTileEntity(pos) == null || stack == null || !worldObj.getChunkFromBlockCoords(pos).isLoaded())
			return 0;
		if (worldObj.getTileEntity(pos) instanceof IInventory) {
			int res = 0;
			IInventory inv = (IInventory) worldObj.getTileEntity(pos);
			for (int i = 0; i < inv.getSizeInventory(); i++)
				if (stack.isItemEqual(inv.getStackInSlot(i)))
					res += inv.getStackInSlot(i).stackSize;
			return res;
		}
		int res = 0;
		Set<IItemHandler> set = Sets.newCopyOnWriteArraySet();
		for (EnumFacing f : EnumFacing.values())
			if (worldObj.getTileEntity(pos).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f))
				set.add(worldObj.getTileEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f));
		for (IItemHandler inv : set)
			for (int i = 0; i < inv.getSlots(); i++)
				if (stack.isItemEqual(inv.getStackInSlot(i)))
					res += inv.getStackInSlot(i).stackSize;

		return res;
	}
}
