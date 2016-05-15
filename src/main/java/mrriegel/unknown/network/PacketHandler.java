package mrriegel.unknown.network;

import mrriegel.unknown.ExampleMod;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(ExampleMod.MODID);

	public static void init() {
		int id = 0;
		INSTANCE.registerMessage(TileMessage.Handler.class, TileMessage.class, id++, Side.CLIENT);
	}

}
