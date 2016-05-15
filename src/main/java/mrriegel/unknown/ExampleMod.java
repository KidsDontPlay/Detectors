package mrriegel.unknown;

import mrriegel.unknown.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
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

@Mod(modid = ExampleMod.MODID, name = ExampleMod.MODNAME, version = ExampleMod.VERSION)
public class ExampleMod {
	public static final String MODID = "unknown";
	public static final String VERSION = "1.0.0";
	public static final String MODNAME = "Check";

	@Instance(ExampleMod.MODID)
	public static ExampleMod instance;

	@SidedProxy(clientSide = "mrriegel.unknown.proxy.ClientProxy", serverSide = "mrriegel.unknown.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs tab1 = new CreativeTabs(ExampleMod.MODID) {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(Blocks.pumpkin);
		}

		@Override
		public String getTranslatedTabLabel() {
			return ExampleMod.MODNAME;
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