package mrriegel.detectors.item;

import java.util.List;

import mrriegel.detectors.CreativeTab;
import mrriegel.detectors.tile.TileBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemLinker extends ItemBase {
	public ItemLinker() {
		super();
		this.setCreativeTab(CreativeTab.tab1);
		this.setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) {
			stack.setTagCompound(new NBTTagCompound());
			player.addChatMessage(new TextComponentString("Linker reset."));
		}
		return super.onItemRightClick(stack, world, player, hand);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
			return EnumActionResult.SUCCESS;
		if (stack.getTagCompound() == null)
			stack.setTagCompound(new NBTTagCompound());
		if (worldIn.getTileEntity(pos) instanceof TileBase && ((TileBase) worldIn.getTileEntity(pos)).useBlockPosSet() && !stack.getTagCompound().getBoolean("active")) {
			stack.getTagCompound().setLong("tile", ((TileBase) worldIn.getTileEntity(pos)).getPos().toLong());
			stack.getTagCompound().setBoolean("active", true);
			stack.getTagCompound().setInteger("dim", worldIn.provider.getDimension());
			playerIn.addChatMessage(new TextComponentString("Bound linker to " + worldIn.getBlockState(pos).getBlock().getLocalizedName()));
			return EnumActionResult.SUCCESS;
		}
		BlockPos t = BlockPos.fromLong(stack.getTagCompound().getLong("tile"));
		int dim = stack.getTagCompound().getInteger("dim");
		if (worldIn.getTileEntity(t) instanceof TileBase && worldIn.provider.getDimension() == dim && !t.equals(pos) && t.getDistance(pos.getX(), pos.getY(), pos.getZ()) < 35) {
			TileBase tile = (TileBase) worldIn.getTileEntity(t);
			if (tile.useBlockPosSet()) {
				if (!tile.getBlockPosSet().contains(pos)) {
					tile.getBlockPosSet().add(pos);
					playerIn.addChatMessage(new TextComponentString(worldIn.getBlockState(pos).getBlock().getLocalizedName() + " selected."));
				} else {
					tile.getBlockPosSet().remove(pos);
					playerIn.addChatMessage(new TextComponentString(worldIn.getBlockState(pos).getBlock().getLocalizedName() + " removed."));
				}

			}
			tile.syncWithClient();
		}
		return EnumActionResult.SUCCESS;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(I18n.format("tooltip." + getRegistryName().toString()));
		if (stack.getTagCompound() == null || stack.getTagCompound().getInteger("dim") != playerIn.worldObj.provider.getDimension())
			return;
		BlockPos t = BlockPos.fromLong(stack.getTagCompound().getLong("tile"));
		if (playerIn.worldObj.getTileEntity(t) != null)
			tooltip.add("Bound to " + playerIn.worldObj.getBlockState(t).getBlock().getLocalizedName() + " at x:" + t.getX() + " y:" + t.getY() + " z:" + t.getZ());
	}

	@Override
	public String getName() {
		return "linker";
	}

}
