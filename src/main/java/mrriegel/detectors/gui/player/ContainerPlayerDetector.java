package mrriegel.detectors.gui.player;

import mrriegel.detectors.gui.AbstractContainer;
import mrriegel.detectors.tile.TileBase;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerPlayerDetector extends AbstractContainer {

	public ContainerPlayerDetector(TileBase tile, InventoryPlayer playerInv) {
		super(tile, playerInv);
	}

}
