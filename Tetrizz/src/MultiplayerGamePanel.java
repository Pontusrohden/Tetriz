import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.JPanel;

public class MultiplayerGamePanel extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	int DELAY;
	int levelUp;
	boolean running = false;
	boolean gameOver = false;
	GameFrame frame;
	GameBoard gameBoard1;
	GameBoard gameBoard2;

	Brick brick1;
	Brick brick2;
	Timer timer = new Timer(DELAY, this);

	MultiplayerGamePanel(GameFrame frame) {
		this.frame = frame;
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
	}
	public void startGame() {
		DELAY = 600;
		gameBoard1 = new GameBoard(300,SCREEN_HEIGHT, UNIT_SIZE);
		brick1 = new Brick(SCREEN_WIDTH/2, 0, UNIT_SIZE);
		gameBoard2 = new GameBoard(300,SCREEN_HEIGHT, UNIT_SIZE);
		gameBoard2.setLocation(350,300);
		//brick2 = new Brick(SCREEN_WIDTH+350/2, 0, UNIT_SIZE);
		this.add(gameBoard1);
		this.add(gameBoard2);
		running = true;
		gameOver = false;
		levelUp = 100;
		timer.setDelay(DELAY);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		if(!gameOver) {
			brick1.draw(g);
			gameBoard1.draw(g);
			g.setColor(Color.white);
			g.setFont(new Font("Ink Free", Font.BOLD, 20));
			g.drawString("Score:" + gameBoard1.getScore(), (UNIT_SIZE), 20);
			
			g.setColor(Color.white);
			g.setFont(new Font("Ink Free", Font.BOLD, 20));
			g.drawString("Level: " + levelUp/100, (UNIT_SIZE), (40));
			
			brick2.draw(g);
			gameBoard2.draw(g);
			g.setColor(Color.white);
			g.setFont(new Font("Ink Free", Font.BOLD, 20));
			g.drawString("Score:" + gameBoard2.getScore(), (UNIT_SIZE), 20);
			
			g.setColor(Color.white);
			g.setFont(new Font("Ink Free", Font.BOLD, 20));
			g.drawString("Level: " + levelUp/100, (UNIT_SIZE), (40));
		}else {
			gameOver(g);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {			
			brick1.drop();
			if(gameBoard1.checkBottomCollision(brick1)) {
				if(!placementIsValid(brick1)) {
					timer.stop();
					gameOver = true;
					running = false;
				}else {					
					gameBoard1.placeBlock(brick1);
					gameBoard1.checkFullRow();
					brick1 = new Brick(SCREEN_WIDTH/2,  0, UNIT_SIZE);
				}
			}
			if(gameBoard1.getScore() >= levelUp) {
				DELAY -= 40;
				timer.setDelay(DELAY);
				levelUp += 100;
				
			}
			
			brick2.drop();
			if(gameBoard2.checkBottomCollision(brick2)) {
				if(!placementIsValid(brick2)) {
					timer.stop();
					gameOver = true;
					running = false;
				}else {					
					gameBoard2.placeBlock(brick2);
					gameBoard2.checkFullRow();
					brick2 = new Brick(SCREEN_WIDTH/2,  0, UNIT_SIZE);
				}
			}
			if(gameBoard2.getScore() >= levelUp) {
				DELAY -= 40;
				timer.setDelay(DELAY);
				levelUp += 100;
				
			}
			repaint();
		}
	}
	public class MyKeyAdapter extends KeyAdapter {
		private boolean pressed = false;
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			//Player 1
			case KeyEvent.VK_LEFT:
				if(!gameBoard1.checkLeftCollision(brick1)) {					
					brick1.move(-1);
					repaint();
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(!gameBoard1.checkRightCollision(brick1)) {		
					brick1.move(1);
					repaint();
				}
				break;
			case KeyEvent.VK_DOWN:
				if(!gameBoard1.checkBottomCollisionFast(brick1)) {		
					pressed = true;
					timer.stop();
					if(pressed) {	
						brick1.drop();
						repaint();
					}
				}
				else {
					pressed = false;
					timer.start();
				}
				break;
			case KeyEvent.VK_UP:
				Brick.rotateMatrix();
				if(gameBoard1.checkLeftCollision(brick1)) {
					brick1.move(1);
				}else if(gameBoard1.checkRightCollision(brick1)) {
					brick1.move(-1);
				}
				repaint();
				break;
				
			//Player 2
			case KeyEvent.VK_A:
				if(!gameBoard2.checkLeftCollision(brick2)) {					
					brick2.move(-1);
					repaint();
				}
				break;
			case KeyEvent.VK_D:
				if(!gameBoard2.checkRightCollision(brick2)) {		
					brick2.move(1);
					repaint();
				}
				break;
			case KeyEvent.VK_S:
				if(!gameBoard2.checkBottomCollisionFast(brick2)) {		
					pressed = true;
					timer.stop();
					if(pressed) {	
						brick2.drop();
						repaint();
					}
				}
				else {
					pressed = false;
					timer.start();
				}
				break;
			case KeyEvent.VK_W:
				Brick.rotateMatrix();
				if(gameBoard2.checkLeftCollision(brick2)) {
					brick2.move(1);
				}else if(gameBoard2.checkRightCollision(brick2)) {
					brick2.move(-1);
				}
				repaint();
				break;
			case KeyEvent.VK_SPACE:
				if(gameOver) {
					startGame();
					repaint();
				}
				break;
			case KeyEvent.VK_ESCAPE:
				if(running) {
					running = false;
					frame.pauseGame();
				}else {
					running = true;
					frame.unPauseGame();
				}
				break;
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				pressed = false;
				timer.start();
			}
		}
	}
	public boolean placementIsValid(Brick brick) {
		if(brick.getY() <= 3*UNIT_SIZE) {
			return false;
		}
		return true;
	}
	public void unPauseGamePanel() {
		running = true;
	}
	
	public void gameOver(Graphics g) {
		timer.stop();
		//Game over text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics1.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
		
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free", Font.BOLD, 20));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Score:" + gameBoard1.getScore(), (SCREEN_WIDTH - metrics2.stringWidth("Score: " + gameBoard1.getScore()))/2, (SCREEN_HEIGHT/2+40));
	
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free", Font.BOLD, 20));
		FontMetrics metrics3 = getFontMetrics(g.getFont());
		g.drawString("Press spacebar to play again", (SCREEN_WIDTH - metrics3.stringWidth("Press spacebar to play again"))/2, (SCREEN_HEIGHT/2+70));
		
		
	}
}
