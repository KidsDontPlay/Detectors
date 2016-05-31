package mrriegel.detectors.gui.player;

import mrriegel.detectors.gui.AbstractContainer;
import mrriegel.detectors.gui.AbstractGui;

public class GuiPlayerDetector extends AbstractGui {

	public GuiPlayerDetector(AbstractContainer container) {
		super(container);
	}

	@Override
	public void initGui() {
		super.initGui();
		initButtons(VISIBLE, MINUSNUM, MINUSRANGE, PLUSRANGE, PLUSNUM, OP);
	}
}
