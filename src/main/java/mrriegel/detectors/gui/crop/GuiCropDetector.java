package mrriegel.detectors.gui.crop;

import mrriegel.detectors.gui.AbstractContainer;
import mrriegel.detectors.gui.AbstractGui;
import net.minecraft.client.gui.GuiButton;

public class GuiCropDetector extends AbstractGui {

	public GuiCropDetector(AbstractContainer container) {
		super(container);
	}

	@Override
	public void initGui() {
		super.initGui();
		allButton = new GuiButton(ALL, guiLeft + 7, guiTop + 16, 20, 20, "");
		buttonList.add(allButton);
		visibleButton = new GuiButton(VISIBLE, guiLeft + 32, guiTop + 16, 20, 20, "");
		buttonList.add(visibleButton);
		mobButton = new GuiButton(MOB, guiLeft + 7, guiTop + 46, 20, 20, "");
		buttonList.add(mobButton);
		minusRangeButton = new GuiButton(MINUSRANGE, guiLeft + 97, guiTop + 16, 20, 20, "-");
		buttonList.add(minusRangeButton);
		plusRangeButton = new GuiButton(PLUSRANGE, guiLeft + 147, guiTop + 16, 20, 20, "+");
		buttonList.add(plusRangeButton);
		minusNumButton = new GuiButton(MINUSNUM, guiLeft + 97, guiTop + 46, 20, 20, "-");
		buttonList.add(minusNumButton);
		plusNumButton = new GuiButton(PLUSNUM, guiLeft + 147, guiTop + 46, 20, 20, "+");
		buttonList.add(plusNumButton);
	}

}
