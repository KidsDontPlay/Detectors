package mrriegel.detectors.network;

import mrriegel.detectors.Detectors;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(Detectors.MODID);

	public static void init() {
		int id = 0;
		INSTANCE.registerMessage(TileMessage.Handler.class, TileMessage.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(ButtonMessage.Handler.class, ButtonMessage.class, id++, Side.SERVER);
	}

}
