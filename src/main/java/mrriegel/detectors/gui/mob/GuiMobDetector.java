package mrriegel.detectors.gui.mob;

import net.minecraft.client.gui.GuiButton;
import mrriegel.detectors.gui.AbstractContainer;
import mrriegel.detectors.gui.AbstractGui;

public class GuiMobDetector extends AbstractGui {

	public GuiMobDetector(AbstractContainer container) {
		super(container);
	}

	@Override
	public void initGui() {
		super.initGui();
		initButtons(VISIBLE, MOB, MINUSNUM, MINUSRANGE, PLUSRANGE, PLUSNUM,AGE);
	}
}
