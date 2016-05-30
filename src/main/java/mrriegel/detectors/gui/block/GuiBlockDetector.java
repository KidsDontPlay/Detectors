package mrriegel.detectors.gui.block;

import mrriegel.detectors.gui.AbstractContainer;
import mrriegel.detectors.gui.AbstractGui;

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
		initButtons(ALL, VISIBLE);
	}

}
