package mrriegel.detectors.network;

import io.netty.buffer.ByteBuf;
import mrriegel.detectors.gui.AbstractGui;
import mrriegel.detectors.tile.TileBase;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ButtonMessage implements IMessage {

	int id;
	BlockPos pos;
	boolean shift;

	public ButtonMessage() {
	}

	public ButtonMessage(BlockPos pos, int id, boolean shift) {
		this.id = id;
		this.pos = pos;
		this.shift = shift;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.id = buf.readInt();
		this.pos = BlockPos.fromLong(buf.readLong());
		this.shift = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.id);
		buf.writeLong(this.pos.toLong());
		buf.writeBoolean(this.shift);
	}

	public static class Handler implements IMessageHandler<ButtonMessage, IMessage> {

		@Override
		public IMessage onMessage(final ButtonMessage message, final MessageContext ctx) {

			((WorldServer) ctx.getServerHandler().playerEntity.worldObj).addScheduledTask(new Runnable() {
				@Override
				public void run() {
					TileEntity tile = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.pos);
					if (tile instanceof TileBase) {
						TileBase t = (TileBase) tile;
						switch (message.id) {
						case AbstractGui.plus:
							t.setNumber(t.getNumber() + (message.shift ? 10 : 1));
							break;
						case AbstractGui.minus:
							t.setNumber(t.getNumber() - (message.shift ? 10 : 1));
							if (t.getNumber() < 0)
								t.setNumber(0);
							break;
						case AbstractGui.all:
							t.setAll(!t.isAll());
							break;
						case AbstractGui.visible:
							t.setVisible(!t.isVisible());
							break;
						default:
							break;
						}
//						t.syncWithClient();
					}
				}
			});
			return null;
		}
	}

}
