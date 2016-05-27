package mrriegel.detectors.gui;

import java.io.IOException;

import mrriegel.detectors.Detectors;
import mrriegel.detectors.network.ButtonMessage;
import mrriegel.detectors.network.PacketHandler;
import mrriegel.detectors.tile.TileBase;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Lists;

public class AbstractGui extends GuiContainer {

	public static final int PLUSRANGE = 0, MINUSRANGE = 1, ALL = 2, VISIBLE = 3, MOB = 4, PLUSNUM = 5, MINUSNUM = 6;

	protected static final ResourceLocation GuiTextures = new ResourceLocation(Detectors.MODID + ":textures/gui/gui.png");
	protected GuiButton plusRangeButton, minusRangeButton, allButton, visibleButton, mobButton, plusNumButton, minusNumButton;
	protected TileBase tile;

	public AbstractGui(AbstractContainer container) {
		super(container);
		tile = container.tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = tile == null ? "Birdplane" : mc.theWorld.getBlockState(tile.getPos()).getBlock().getLocalizedName();
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		this.fontRendererObj.drawString(mc.thePlayer.inventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GuiTextures);
		if (allButton != null)
			this.drawTexturedModalRect(allButton.xPosition - guiLeft, allButton.yPosition - guiTop, 176 + (!tile.isAll() ? 0 : 20), 18, 20, 20);
		if (visibleButton != null)
			this.drawTexturedModalRect(visibleButton.xPosition - guiLeft, visibleButton.yPosition - guiTop, 176 + (!tile.isVisible() ? 0 : 20), 38, 20, 20);
		if (mobButton != null)
			this.drawTexturedModalRect(mobButton.xPosition - guiLeft, mobButton.yPosition - guiTop, 176 + (tile.getKind().ordinal() * 20), 58, 20, 20);

		if (plusRangeButton != null && plusRangeButton.isMouseOver())
			drawHoveringText(Lists.newArrayList(I18n.format("tooltip.detectors.range+")), mouseX - guiLeft, mouseY - guiTop);
		if (minusRangeButton != null && minusRangeButton.isMouseOver())
			drawHoveringText(Lists.newArrayList(I18n.format("tooltip.detectors.range-")), mouseX - guiLeft, mouseY - guiTop);
		if (allButton != null && allButton.isMouseOver())
			drawHoveringText(Lists.newArrayList(I18n.format("tooltip.detectors.all_" + tile.isAll())), mouseX - guiLeft, mouseY - guiTop);
		if (visibleButton != null && visibleButton.isMouseOver())
			drawHoveringText(Lists.newArrayList(I18n.format("tooltip.detectors.visible_" + tile.isVisible())), mouseX - guiLeft, mouseY - guiTop);
		if (mobButton != null && mobButton.isMouseOver())
			drawHoveringText(Lists.newArrayList(I18n.format("tooltip.detectors.kind_" + tile.getKind().ordinal())), mouseX - guiLeft, mouseY - guiTop);
		if (plusNumButton != null && plusNumButton.isMouseOver())
			drawHoveringText(Lists.newArrayList(I18n.format(plusNum())), mouseX - guiLeft, mouseY - guiTop);
		if (minusNumButton != null && minusNumButton.isMouseOver())
			drawHoveringText(Lists.newArrayList(I18n.format(minusNum())), mouseX - guiLeft, mouseY - guiTop);
	}

	protected String plusNum() {
		return "plus";
	}

	protected String minusNum() {
		return "minus";
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
		case AbstractGui.PLUSRANGE:
			tile.setRange(tile.getRange() + (isShiftKeyDown() ? 10 : 1));
			break;
		case AbstractGui.MINUSRANGE:
			tile.setRange(tile.getRange() - (isShiftKeyDown() ? 10 : 1));
			if (tile.getRange() < 0)
				tile.setRange(0);
			break;
		case AbstractGui.ALL:
			tile.setAll(!tile.isAll());
			break;
		case AbstractGui.VISIBLE:
			tile.setVisible(!tile.isVisible());
			break;
		case AbstractGui.MOB:
			tile.setKind(tile.getKind().next());
			break;
		case AbstractGui.PLUSNUM:
			tile.setNumber(tile.getNumber() + (isShiftKeyDown() ? 10 : 1));
			break;
		case AbstractGui.MINUSNUM:
			tile.setNumber(tile.getNumber() - (isShiftKeyDown() ? 10 : 1));
			if (tile.getNumber() < 0)
				tile.setNumber(0);
			break;
		default:
			break;
		}
		PacketHandler.INSTANCE.sendToServer(new ButtonMessage(tile.getPos(), button.id, isShiftKeyDown()));
	}

}
