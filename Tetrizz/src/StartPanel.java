import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class StartPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	static final int SCREEN_WIDTH = 700;
	static final int SCREEN_HEIGHT = 600;
	
	JButton startButton;
	JButton settingsButton;
	JButton multiplayerButton;
	
	GameFrame frame;
		
	public StartPanel(GameFrame frame) {		
		this.frame = frame;
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		startButton = new JButton("Start Game");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.changePanel(1);
			}
		});
		settingsButton = new JButton("Settings");
		settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.changePanel(2);				
			}
		});
		multiplayerButton = new JButton("Multiplayer");
		multiplayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.changePanel(4);				
			}
		});
		
		
		JLabel label = new JLabel("Tetriz");
		label.setFont(new Font("Serif", Font.PLAIN, 50));
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(Color.BLACK);
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.Y_AXIS));
		buttonPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPane.setBorder(BorderFactory.createEmptyBorder(20, 10, 80, 10));
		buttonPane.add(label);
		buttonPane.add(startButton);
		buttonPane.add(multiplayerButton);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 10)));
		buttonPane.add(settingsButton);

		this.add(buttonPane, BorderLayout.CENTER);
	}	
}
