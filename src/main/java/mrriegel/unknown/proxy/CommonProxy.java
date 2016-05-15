package mrriegel.unknown.proxy;

import mrriegel.unknown.ModBlocks;
import mrriegel.unknown.ModItems;
import mrriegel.unknown.network.PacketHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ModBlocks.init();
		ModItems.init();
	}

	public void init(FMLInitializationEvent event) {
		PacketHandler.init();

	}

	public void postInit(FMLPostInitializationEvent event) {
		// TODO Auto-generated method stub

	}

}
