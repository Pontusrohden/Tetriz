import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private GamePanel gamePanel;
	private StartPanel startPanel;
	private SettingsPanel settingsPanel;
	private PausePanel pausePanel;
	private MultiplayerGamePanel multiplayerGamePanel;

	private JPanel[] panelContainer;
	private int screens = 5;
	
	GameFrame(){
		startPanel = new StartPanel(this);
		gamePanel = new GamePanel(this);
		settingsPanel = new SettingsPanel(this);
		pausePanel = new PausePanel(this);
		multiplayerGamePanel = new MultiplayerGamePanel(this);
		
		panelContainer = new JPanel[screens];
		panelContainer[0] = startPanel;
		panelContainer[1] = gamePanel;
		panelContainer[2] = settingsPanel;
		panelContainer[3] = pausePanel;
		panelContainer[4] = multiplayerGamePanel;

		this.add(panelContainer[0]);
		this.setTitle("Tretriz");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);		
	}
	public void changePanel(int panelIndex) {
		if(panelIndex == 1) {
			gamePanel.startGame();
		}
		if(panelIndex == 4) {
			multiplayerGamePanel.startGame();
		}
		this.getContentPane().removeAll();
		this.add(panelContainer[panelIndex]);
		gamePanel.requestFocus();
		this.revalidate();
		this.repaint();
	}
	public void pauseGame() {	
		this.add(pausePanel);
		this.remove(gamePanel);
		this.revalidate();
		this.repaint();
	}
	public void unPauseGame() {
		this.remove(pausePanel);
		this.add(gamePanel);
		gamePanel.unPauseGamePanel();
		gamePanel.requestFocus();
		this.revalidate();
		this.repaint();
		this.revalidate();
		this.repaint();
	}
}
