package mrriegel.detectors.gui;

import java.io.IOException;

import com.google.common.collect.Lists;

import mrriegel.detectors.Detectors;
import mrriegel.detectors.network.ButtonMessage;
import mrriegel.detectors.network.PacketHandler;
import mrriegel.detectors.tile.TileBase;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class AbstractGui extends GuiContainer {

	public static final int plus = 0, minus = 1, all = 2, visible = 3;

	private static final ResourceLocation GuiTextures = new ResourceLocation(Detectors.MODID + ":textures/gui/gui.png");
	private GuiButton plusButton, minusButton, allButton, visibleButton;
	private TileBase tile;
	BlockPos pos;

	public AbstractGui(Container inventorySlotsIn, BlockPos pos) {
		super(inventorySlotsIn);
		this.pos = pos;
	}

	@Override
	public void initGui() {
		super.initGui();
		allButton = new GuiButton(all, guiLeft + 27, guiTop + 3, 20, 20, "");
		buttonList.add(allButton);
		visibleButton = new GuiButton(visible, guiLeft + 3, guiTop + 27, 20, 20, "");
		buttonList.add(visibleButton);
		tile = (TileBase) mc.theWorld.getTileEntity(pos);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = tile == null ? "Hosenträger" : mc.theWorld.getBlockState(tile.getPos()).getBlock().getLocalizedName();
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		this.fontRendererObj.drawString(mc.thePlayer.inventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GuiTextures);
		if (allButton != null)
			this.drawTexturedModalRect(allButton.xPosition - guiLeft, allButton.yPosition - guiTop, 176 + (!tile.isAll() ? 0 : 20), 18, 20, 20);
		if (visibleButton != null)
			this.drawTexturedModalRect(visibleButton.xPosition - guiLeft, visibleButton.yPosition - guiTop, 176 + (!tile.isVisible() ? 0 : 20), 38, 20, 20);

		if (allButton != null && allButton.isMouseOver())
			drawHoveringText(Lists.newArrayList(tile.isAll() ? "all" : "one"), mouseX - guiLeft, mouseY - guiTop);
		if (visibleButton != null && visibleButton.isMouseOver())
			drawHoveringText(Lists.newArrayList(tile.isVisible() ? "visible" : "invisible"), mouseX - guiLeft, mouseY - guiTop);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GuiTextures);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
		case AbstractGui.plus:
			tile.setNumber(tile.getNumber() + (isShiftKeyDown() ? 10 : 1));
			break;
		case AbstractGui.minus:
			tile.setNumber(tile.getNumber() - (isShiftKeyDown() ? 10 : 1));
			if (tile.getNumber() < 0)
				tile.setNumber(0);
			break;
		case AbstractGui.all:
			tile.setAll(!tile.isAll());
			break;
		case AbstractGui.visible:
			tile.setVisible(!tile.isVisible());
			break;
		default:
			break;
		}
		PacketHandler.INSTANCE.sendToServer(new ButtonMessage(tile.getPos(), button.id, isShiftKeyDown()));
	}
}
