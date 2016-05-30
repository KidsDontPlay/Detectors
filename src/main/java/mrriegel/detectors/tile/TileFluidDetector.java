package mrriegel.detectors.tile;

import java.util.Set;

import mrriegel.detectors.block.BlockBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class TileFluidDetector extends TileBase implements ITickable {

	@Override
	public void update() {
		if (worldObj.isRemote || worldObj.getTotalWorldTime() % 5 != 0)
			return;
		int num = 0;
		for (BlockPos pos : blockPosSet)
			num += inInventory(stack, pos);
		boolean x = op.match(num, number * 1000);
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
		if (worldObj.getTileEntity(pos) == null || getFluid(stack) == null || !worldObj.getChunkFromBlockCoords(pos).isLoaded())
			return 0;
		if (worldObj.getTileEntity(pos) instanceof IFluidHandler) {
			int res = 0;
			IFluidHandler inv = (IFluidHandler) worldObj.getTileEntity(pos);
			Set<FluidTankInfo> set = Sets.newCopyOnWriteArraySet();
			for (EnumFacing f : EnumFacing.values())
				set.addAll(Lists.newArrayList(inv.getTankInfo(f)));
			for (FluidTankInfo i : set) {
				if (i.fluid != null && i.fluid.getFluid() == getFluid(stack).getFluid())
					res += i.fluid.amount;
				if (res > 0)
					break;
			}
			return res;
		}
		return 0;
	}

	public static FluidStack getFluid(ItemStack s) {
		if (s == null || s.getItem() == null)
			return null;
		FluidStack a = null;
		a = FluidContainerRegistry.getFluidForFilledItem(s);
		if (a != null)
			return a;
		if (s.getItem() instanceof IFluidContainerItem)
			a = ((IFluidContainerItem) s.getItem()).getFluid(s);
		return a;
	}
}
