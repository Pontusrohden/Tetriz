import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.JPanel;

public class SidePanel extends JPanel {

	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_WIDTH)/UNIT_SIZE;

	SidePanel() {
		
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.white);
		this.setFocusable(true);
	}
}