package mrriegel.unknown.tile;

import java.util.Set;

import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import mrriegel.unknown.network.PacketHandler;
import mrriegel.unknown.network.TileMessage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class TileBase extends TileEntity {

	public Set<BlockPos> set = Sets.newHashSet();
	protected boolean state;
	protected boolean visible;

	@Override
	public Packet getDescriptionPacket() {
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
		this.set = new Gson().fromJson(compound.getString("set"), new TypeToken<Set<BlockPos>>() {
		}.getType());
		this.state = compound.getBoolean("state");
		this.visible = compound.getBoolean("visible");
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		writeSyncableData(compound);
	}

	public void writeSyncableData(NBTTagCompound compound) {
		compound.setString("set", new Gson().toJson(set));
		compound.setBoolean("state", state);
		compound.setBoolean("visible", visible);
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
			PacketHandler.INSTANCE.sendToDimension(new TileMessage(this), worldObj.provider.getDimension());
			// markDirty();
			// IBlockState state = getWorld().getBlockState(getPos());
			// getWorld().notifyBlockUpdate(getPos(), state, state, 3);
		}
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}

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
}
