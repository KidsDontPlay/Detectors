package mrriegel.detectors.tile;

import java.util.Set;
import java.util.UUID;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public abstract class TileBase extends TileEntity {

	protected Set<BlockPos> blockPosSet = Sets.newHashSet();
	protected Set<UUID> UUIDSet = Sets.newHashSet();
	protected boolean state;
	protected boolean visible = true;
	protected boolean all = true;
	protected int number;
	protected int range = 6;
	protected MobKind kind = MobKind.GOOD;
	protected ItemStack stack;
	protected Age age = Age.ADULT;

	public enum MobKind {
		GOOD, EVIL, BOTH;
		private static MobKind[] vals = values();

		public MobKind next() {
			return vals[(this.ordinal() + 1) % vals.length];
		}

		public Class getClazz() {
			switch (this) {
			case GOOD:
				return EntityAnimal.class;
			case EVIL:
				return EntityMob.class;
			case BOTH:
				return EntityCreature.class;
			default:
				return EntityLiving.class;
			}
		}
	}

	public enum Age {
		ADULT, CHILD, BOTH;
		private static Age[] vals = values();

		public Age next() {
			return vals[(this.ordinal() + 1) % vals.length];
		}

		public boolean match(EntityCreature e) {
			switch (this) {
			case ADULT:
				return !e.isChild();
			case CHILD:
				return e.isChild();
			case BOTH:
				return true;
			default:
				return false;
			}
		}

	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound syncData = new NBTTagCompound();
		this.writeSyncableData(syncData);
		return new SPacketUpdateTileEntity(this.pos, 1, syncData);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readSyncableData(pkt.getNbtCompound());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		readSyncableData(compound);
	}

	public void readSyncableData(NBTTagCompound compound) {
		this.blockPosSet = new Gson().fromJson(compound.getString("set"), new TypeToken<Set<BlockPos>>() {
		}.getType());
		this.UUIDSet = new Gson().fromJson(compound.getString("uuid"), new TypeToken<Set<UUID>>() {
		}.getType());
		this.state = compound.getBoolean("state");
		this.visible = compound.getBoolean("visible");
		this.all = compound.getBoolean("all");
		this.number = compound.getInteger("number");
		this.range = compound.getInteger("range");
		this.kind = MobKind.valueOf(compound.getString("kind"));
		if (compound.hasKey("stack"))
			this.stack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("stack"));
		else
			this.stack = null;
		this.age = Age.valueOf(compound.getString("age"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		writeSyncableData(compound);
		return compound;
	}

	public void writeSyncableData(NBTTagCompound compound) {
		compound.setString("set", new Gson().toJson(blockPosSet));
		compound.setString("uuid", new Gson().toJson(UUIDSet));
		compound.setBoolean("state", state);
		compound.setBoolean("visible", visible);
		compound.setBoolean("all", all);
		compound.setInteger("number", number);
		compound.setInteger("range", range);
		compound.setString("kind", kind.toString());
		if (stack != null) {
			NBTTagCompound tag = new NBTTagCompound();
			stack.writeToNBT(tag);
			compound.setTag("stack", tag);
		}
		compound.setString("age", age.toString());
	}

	public void deserializeSyncNBT(NBTTagCompound nbt) {
		this.readSyncableData(nbt);
	}

	public NBTTagCompound serializeSyncNBT() {
		NBTTagCompound ret = new NBTTagCompound();
		this.writeSyncableData(ret);
		return ret;
	}

	public void syncWithClient() {
		if (!worldObj.isRemote) {
			WorldServer w = (WorldServer) worldObj;
			for (EntityPlayer p : w.playerEntities) {
				if (p.getPosition().getDistance(pos.getX(), pos.getY(), pos.getZ()) < 32) {
					((EntityPlayerMP) p).connection.sendPacket(getUpdatePacket());
				}
			}
			// PacketHandler.INSTANCE.sendToDimension(new TileMessage(this),
			// worldObj.provider.getDimension());
		}
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}

	public abstract boolean useBlockPosSet();

	public abstract boolean useUUIDSet();

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isAll() {
		return all;
	}

	public void setAll(boolean all) {
		this.all = all;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Set<BlockPos> getBlockPosSet() {
		return blockPosSet;
	}

	public void setBlockPosSet(Set<BlockPos> blockPosSet) {
		this.blockPosSet = blockPosSet;
	}

	public Set<UUID> getUUIDSet() {
		return UUIDSet;
	}

	public void setUUIDSet(Set<UUID> uUIDSet) {
		this.UUIDSet = uUIDSet;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public MobKind getKind() {
		return kind;
	}

	public void setKind(MobKind kind) {
		this.kind = kind;
	}

	public ItemStack getStack() {
		return stack;
	}

	public void setStack(ItemStack stack) {
		this.stack = stack;
	}

	public Age getAge() {
		return age;
	}

	public void setAge(Age age) {
		this.age = age;
	}
}
