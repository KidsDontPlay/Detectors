package mrriegel.detectors;

import mrriegel.detectors.gui.AbstractGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	private static int tmp = 0;
	public static final int growth = tmp++;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == growth) {
			return new Container() {

				@Override
				public boolean canInteractWith(EntityPlayer playerIn) {
					return !false;
				}
			};
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == growth) {
			System.out.println("open2");
			return new AbstractGui(new Container() {

				@Override
				public boolean canInteractWith(EntityPlayer playerIn) {
					return !false;
				}
			},new BlockPos(x, y, z));
		}
		return null;
	}

}
