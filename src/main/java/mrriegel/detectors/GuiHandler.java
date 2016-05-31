package mrriegel.detectors;

import mrriegel.detectors.gui.adult.ContainerAdultDetector;
import mrriegel.detectors.gui.adult.GuiAdultDetector;
import mrriegel.detectors.gui.block.ContainerBlockDetector;
import mrriegel.detectors.gui.block.GuiBlockDetector;
import mrriegel.detectors.gui.crop.ContainerCropDetector;
import mrriegel.detectors.gui.crop.GuiCropDetector;
import mrriegel.detectors.gui.fluid.ContainerFluidDetector;
import mrriegel.detectors.gui.fluid.GuiFluidDetector;
import mrriegel.detectors.gui.item.ContainerItemDetector;
import mrriegel.detectors.gui.item.GuiItemDetector;
import mrriegel.detectors.gui.mob.ContainerMobDetector;
import mrriegel.detectors.gui.mob.GuiMobDetector;
import mrriegel.detectors.gui.player.ContainerPlayerDetector;
import mrriegel.detectors.gui.player.GuiPlayerDetector;
import mrriegel.detectors.tile.TileBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	private static int tmp = 0;
	public static final int crop = tmp++;
	public static final int block = tmp++;
	public static final int adult = tmp++;
	public static final int mob = tmp++;
	public static final int item = tmp++;
	public static final int fluid = tmp++;
	public static final int player = tmp++;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == crop) {
			return new ContainerCropDetector((TileBase) world.getTileEntity(new BlockPos(x, y, z)), player.inventory);
		}
		if (ID == block) {
			return new ContainerBlockDetector((TileBase) world.getTileEntity(new BlockPos(x, y, z)), player.inventory);
		}
		if (ID == adult) {
			return new ContainerAdultDetector((TileBase) world.getTileEntity(new BlockPos(x, y, z)), player.inventory);
		}
		if (ID == mob) {
			return new ContainerMobDetector((TileBase) world.getTileEntity(new BlockPos(x, y, z)), player.inventory);
		}
		if (ID == item) {
			return new ContainerItemDetector((TileBase) world.getTileEntity(new BlockPos(x, y, z)), player.inventory);
		}
		if (ID == fluid) {
			return new ContainerFluidDetector((TileBase) world.getTileEntity(new BlockPos(x, y, z)), player.inventory);
		}
		if (ID == GuiHandler.player) {
			return new ContainerPlayerDetector((TileBase) world.getTileEntity(new BlockPos(x, y, z)), player.inventory);
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
		if (ID == adult) {
			return new GuiAdultDetector(new ContainerAdultDetector((TileBase) world.getTileEntity(new BlockPos(x, y, z)), player.inventory));
		}
		if (ID == mob) {
			return new GuiMobDetector(new ContainerMobDetector((TileBase) world.getTileEntity(new BlockPos(x, y, z)), player.inventory));
		}
		if (ID == item) {
			return new GuiItemDetector(new ContainerItemDetector((TileBase) world.getTileEntity(new BlockPos(x, y, z)), player.inventory));
		}
		if (ID == fluid) {
			return new GuiFluidDetector(new ContainerFluidDetector((TileBase) world.getTileEntity(new BlockPos(x, y, z)), player.inventory));
		}
		if (ID == GuiHandler.player) {
			return new GuiPlayerDetector(new ContainerPlayerDetector((TileBase) world.getTileEntity(new BlockPos(x, y, z)), player.inventory));
		}
		return null;
	}

}
