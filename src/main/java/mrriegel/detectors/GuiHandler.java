package mrriegel.detectors;

import mrriegel.detectors.gui.block.ContainerBlockDetector;
import mrriegel.detectors.gui.block.GuiBlockDetector;
import mrriegel.detectors.gui.crop.ContainerCropDetector;
import mrriegel.detectors.gui.crop.GuiCropDetector;
import mrriegel.detectors.tile.TileBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	private static int tmp = 0;
	public static final int crop = tmp++;
	public static final int block = tmp++;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == crop) {
			return new ContainerCropDetector((TileBase) world.getTileEntity(new BlockPos(x, y, z)), player.inventory);
		}
		if (ID == block) {
			return new ContainerBlockDetector((TileBase) world.getTileEntity(new BlockPos(x, y, z)), player.inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == crop) {
			return new GuiCropDetector(new ContainerCropDetector((TileBase) world.getTileEntity(new BlockPos(x, y, z)), player.inventory));
		}
		if (ID == block) {
			return new GuiBlockDetector(new ContainerBlockDetector((TileBase) world.getTileEntity(new BlockPos(x, y, z)), player.inventory));
		}
		return null;
	}

}
