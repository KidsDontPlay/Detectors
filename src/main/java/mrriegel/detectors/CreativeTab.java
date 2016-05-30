package mrriegel.detectors;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTab {
	public static CreativeTabs tab1 = new CreativeTabs(Detectors.MODID) {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(ModBlocks.crop);
		}

		@Override
		public String getTranslatedTabLabel() {
			return Detectors.MODNAME;
		}
	};
}
