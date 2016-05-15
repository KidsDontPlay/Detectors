package mrriegel.unknown.item;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import mrriegel.unknown.ExampleMod;
import mrriegel.unknown.tile.TileBase;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemEdit extends Item {
	public ItemEdit() {
		super();
		this.setCreativeTab(ExampleMod.tab1);
		this.setRegistryName("edit");
		this.setMaxStackSize(1);
		this.setUnlocalizedName(getRegistryName().toString());
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) {
			stack.setTagCompound(new NBTTagCompound());
		}
		return super.onItemRightClick(stack, world, player, hand);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
			return EnumActionResult.SUCCESS;
		if (stack.getTagCompound() == null)
			stack.setTagCompound(new NBTTagCompound());
		if (worldIn.getTileEntity(pos) instanceof TileBase && !stack.getTagCompound().getBoolean("active")) {
			stack.getTagCompound().setLong("tile", ((TileBase) worldIn.getTileEntity(pos)).getPos().toLong());
			stack.getTagCompound().setBoolean("active", true);
			stack.getTagCompound().setInteger("dim", worldIn.provider.getDimension());
			return EnumActionResult.SUCCESS;
		}
		BlockPos t = BlockPos.fromLong(stack.getTagCompound().getLong("tile"));
		int dim = stack.getTagCompound().getInteger("dim");
		if (worldIn.getTileEntity(t) instanceof TileBase && worldIn.provider.getDimension() == dim && !t.equals(pos) && t.getDistance(pos.getX(), pos.getY(), pos.getZ()) < 35) {
			TileBase tile = (TileBase) worldIn.getTileEntity(t);
			tile.set.add(pos);
			tile.syncWithClient();
		}
		return EnumActionResult.SUCCESS;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if (stack.getTagCompound() == null || stack.getTagCompound().getInteger("dim") != playerIn.worldObj.provider.getDimension())
			return;
		BlockPos t = BlockPos.fromLong(stack.getTagCompound().getLong("tile"));
		if (playerIn.worldObj.getTileEntity(t) != null)
			tooltip.add(playerIn.worldObj.getBlockState(t).getBlock().getLocalizedName() + " at x:" + t.getX()+" y:"+t.getY()+" z:"+t.getZ());
	}
}
