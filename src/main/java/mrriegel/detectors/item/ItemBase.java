package mrriegel.detectors.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public abstract class ItemBase extends Item {

	public ItemBase() {
		super();
		this.setRegistryName(getName());
		this.setUnlocalizedName(getRegistryName().toString());
	}

	public abstract String getName();

	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

}
