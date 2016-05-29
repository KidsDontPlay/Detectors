package mrriegel.detectors.gui.adult;

import net.minecraft.client.gui.GuiButton;
import mrriegel.detectors.gui.AbstractContainer;
import mrriegel.detectors.gui.AbstractGui;

public class GuiAdultDetector extends AbstractGui {

	public GuiAdultDetector(AbstractContainer container) {
		super(container);
	}

	@Override
	public void initGui() {
		super.initGui();
		initButtons(ALL, VISIBLE, MINUSRANGE, PLUSRANGE);
	}

}
