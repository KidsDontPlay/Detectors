package mrriegel.detectors.network;

import io.netty.buffer.ByteBuf;
import mrriegel.detectors.gui.AbstractGui;
import mrriegel.detectors.tile.TileBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
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
					TileEntity t = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.pos);
					if (t instanceof TileBase) {
						TileBase tile = (TileBase) t;
						switch (message.id) {
						case AbstractGui.PLUSRANGE:
							tile.setRange(tile.getRange() + (message.shift ? 10 : 1));
							break;
						case AbstractGui.MINUSRANGE:
							tile.setRange(tile.getRange() - (message.shift ? 10 : 1));
							if (tile.getRange() < 0)
								tile.setRange(0);
							break;
						case AbstractGui.ALL:
							tile.setAll(!tile.isAll());
							break;
						case AbstractGui.VISIBLE:
							tile.setVisible(!tile.isVisible());
							break;
						case AbstractGui.MOB:
							tile.setKind(tile.getKind().next());
							break;
						case AbstractGui.PLUSNUM:
							tile.setNumber(tile.getNumber() + (message.shift ? 10 : 1));
							break;
						case AbstractGui.MINUSNUM:
							tile.setNumber(tile.getNumber() - (message.shift ? 10 : 1));
							if (tile.getNumber() < 0)
								tile.setNumber(0);
							break;
						case AbstractGui.AGE:
							tile.setAge(tile.getAge().next());
							break;
						case AbstractGui.OP:
							tile.setOp(tile.getOp().next());
							break;
						default:
							break;
						}
						tile.syncWithClient();
					}
				}
			});
			return null;
		}
	}

}
