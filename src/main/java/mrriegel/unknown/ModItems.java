package mrriegel.unknown;

import mrriegel.unknown.item.ItemEdit;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
public static final Item edit=new ItemEdit();
	
	public static void init(){
		GameRegistry.register(edit);
	}
}
