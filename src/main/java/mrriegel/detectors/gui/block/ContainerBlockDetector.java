package mrriegel.detectors.gui.block;

import mrriegel.detectors.gui.AbstractContainer;
import mrriegel.detectors.gui.XSlot;
import mrriegel.detectors.tile.TileBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;

public class ContainerBlockDetector extends AbstractContainer {
	IInventory inv;

	public ContainerBlockDetector(final TileBase tile, InventoryPlayer playerInv) {
		super(tile, playerInv);
		inv = new InventoryBasic(null, false, 1);
		inv.setInventorySlotContents(0, tile.getStack());
		Slot x = new XSlot(inv, 0, 70, 45) {

			@Override
			public void onSlotChanged() {
				super.onSlotChanged();
				tile.setStack(getStack());
				tile.syncWithClient();
			}
		};
		this.addSlotToContainer(x);
	}

}
