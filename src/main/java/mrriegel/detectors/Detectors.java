package mrriegel.detectors;

import mrriegel.detectors.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Detectors.MODID, name = Detectors.MODNAME, version = Detectors.VERSION)
public class Detectors {
	public static final String MODID = "detectors";
	public static final String VERSION = "1.0.0";
	public static final String MODNAME = "Detectors";

	@Instance(Detectors.MODID)
	public static Detectors instance;

	@SidedProxy(clientSide = "mrriegel.detectors.proxy.ClientProxy", serverSide = "mrriegel.detectors.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs tab1 = new CreativeTabs(Detectors.MODID) {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(Blocks.pumpkin);
		}

		@Override
		public String getTranslatedTabLabel() {
			return Detectors.MODNAME;
		}
	};

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