package mrriegel.detectors;

import mrriegel.detectors.item.ItemBase;
import mrriegel.detectors.item.ItemLinker;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
	public static final ItemBase edit = new ItemLinker();

	public static void init() {
		GameRegistry.register(edit);
	}

	public static void initModels() {
		edit.initModel();
	}
}
