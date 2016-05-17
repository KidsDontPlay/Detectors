package mrriegel.detectors.network;

import io.netty.buffer.ByteBuf;
import mrriegel.detectors.tile.TileBase;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TileMessage implements IMessage {

	NBTTagCompound nbt;
	BlockPos pos;

	public TileMessage() {
	}

	public TileMessage(TileEntity tile) {
		this.pos = tile.getPos();
		if (tile instanceof TileBase)
			this.nbt = ((TileBase) tile).serializeSyncNBT();
		else
			this.nbt = tile.serializeNBT();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		nbt = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		ByteBufUtils.writeTag(buf, nbt);
	}

	public static class Handler implements IMessageHandler<TileMessage, IMessage> {

		@Override
		public IMessage onMessage(final TileMessage message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				@Override
				public void run() {
					TileEntity tile = Minecraft.getMinecraft().theWorld.getTileEntity(message.pos);
					if (tile instanceof TileBase)
						((TileBase) tile).deserializeSyncNBT(message.nbt);
					else
						tile.deserializeNBT(message.nbt);
				}
			});
			return null;
		}

	}

}
