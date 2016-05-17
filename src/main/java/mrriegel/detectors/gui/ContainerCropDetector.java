package mrriegel.detectors.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerCropDetector extends Container{

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
