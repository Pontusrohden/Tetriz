import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PausePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	static final int SCREEN_WIDTH = 200;
	static final int SCREEN_HEIGHT = 400;
	
	JButton menuButton;
	JButton settingsButton;
	
	GameFrame frame;
	
	int testing;
	
	public PausePanel(GameFrame frame) {		
		this.frame = frame;
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		menuButton = new JButton("Main menu");
		menuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				System.out.println("clicked");
				frame.changePanel(0);
			}
		});
		settingsButton = new JButton("Return");
		settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.unPauseGame();				
			}
		});
		
		JLabel label = new JLabel("Pause menu");
		label.setFont(new Font("Serif", Font.PLAIN, 50));
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(Color.BLACK);
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.Y_AXIS));
		buttonPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPane.setBorder(BorderFactory.createEmptyBorder(20, 10, 80, 10));
		buttonPane.add(label);
		buttonPane.add(menuButton);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 10)));
		buttonPane.add(settingsButton);

		this.add(buttonPane, BorderLayout.CENTER);
	}
}
