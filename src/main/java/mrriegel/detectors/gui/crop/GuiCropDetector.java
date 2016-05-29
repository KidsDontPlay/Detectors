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
		initButtons(ALL,VISIBLE);
	}

}
