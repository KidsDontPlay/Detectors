package mrriegel.detectors.proxy;

import mrriegel.detectors.CraftingRecipes;
import mrriegel.detectors.Detectors;
import mrriegel.detectors.GuiHandler;
import mrriegel.detectors.ModBlocks;
import mrriegel.detectors.ModItems;
import mrriegel.detectors.network.PacketHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ModBlocks.init();
		ModItems.init();
		CraftingRecipes.init();
	}

	public void init(FMLInitializationEvent event) {
		PacketHandler.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(Detectors.instance, new GuiHandler());

	}

	public void postInit(FMLPostInitializationEvent event) {

	}

}
