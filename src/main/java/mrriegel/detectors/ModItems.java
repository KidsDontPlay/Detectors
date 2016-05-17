package mrriegel.detectors;

import mrriegel.detectors.item.ItemBase;
import mrriegel.detectors.item.ItemEdit;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

public class ModItems {
	public static final ItemBase edit = new ItemEdit();

	public static void init() {
		GameRegistry.register(edit);
	}

	public static void initModels() {
		edit.initModel();
	}
}
