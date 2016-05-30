package mrriegel.detectors;

import mrriegel.detectors.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Detectors.MODID, name = Detectors.MODNAME, version = Detectors.VERSION)
public class Detectors {
	public static final String MODID = "detectors";
	public static final String VERSION = "1.0.0";
	public static final String MODNAME = "Detectors";

	@Instance(Detectors.MODID)
	public static Detectors instance;

	@SidedProxy(clientSide = "mrriegel.detectors.proxy.ClientProxy", serverSide = "mrriegel.detectors.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		MinecraftForge.EVENT_BUS.register(this);

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	@SubscribeEvent
	public void task(PlayerInteractEvent e) {
	}

}