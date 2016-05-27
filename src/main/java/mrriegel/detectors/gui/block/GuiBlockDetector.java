package mrriegel.detectors.gui.block;

import mrriegel.detectors.gui.AbstractContainer;
import mrriegel.detectors.gui.AbstractGui;
import net.minecraft.client.gui.GuiButton;

public class GuiBlockDetector extends AbstractGui {

	public GuiBlockDetector(AbstractContainer container) {
		super(container);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		this.drawTexturedModalRect(69 + guiLeft, 44 + guiTop, 176, 0, 18, 18);
	}

	@Override
	public void initGui() {
		super.initGui();
		allButton = new GuiButton(ALL, guiLeft + 7, guiTop + 16, 20, 20, "");
		buttonList.add(allButton);
		visibleButton = new GuiButton(VISIBLE, guiLeft + 32, guiTop + 16, 20, 20, "");
		buttonList.add(visibleButton);
	}

}
