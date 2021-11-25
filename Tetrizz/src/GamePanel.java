import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 350;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	int DELAY;
	int levelUp;
	boolean running = false;
	boolean gameOver = false;
	GameFrame frame;
	GameBoard gameBoard;
	Brick brick;
	Timer timer = new Timer(DELAY, this);

	GamePanel(GameFrame frame) {
		this.frame = frame;
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
	}
	public void startGame() {
		DELAY = 350;
		gameBoard = new GameBoard(SCREEN_WIDTH,SCREEN_HEIGHT, UNIT_SIZE);
		brick = new Brick(SCREEN_WIDTH/2, 0, UNIT_SIZE);
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
			brick.draw(g);
			gameBoard.draw(g);
			g.setColor(Color.white);
			g.setFont(new Font("Ink Free", Font.BOLD, 20));
			g.drawString("Score:" + gameBoard.getScore(), (UNIT_SIZE), 20);
			
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
			brick.drop();
			if(gameBoard.checkBottomCollision(brick)) {
				if(!placementIsValid()) {
					timer.stop();
					gameOver = true;
					running = false;
				}else {					
					gameBoard.placeBlock(brick);
					gameBoard.checkFullRow();
					brick = new Brick(SCREEN_WIDTH/2,  0, UNIT_SIZE);
				}
			}
			if(gameBoard.getScore() >= levelUp) {
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
			case KeyEvent.VK_LEFT:
				if(!gameBoard.checkLeftCollision(brick)) {					
					brick.move(-1);
					repaint();
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(!gameBoard.checkRightCollision(brick)) {		
					brick.move(1);
					repaint();
				}
				break;
			case KeyEvent.VK_DOWN:
				if(!gameBoard.checkBottomCollisionFast(brick)) {		
					pressed = true;
					timer.stop();
					if(pressed) {	
						brick.drop();
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
				if(gameBoard.checkLeftCollision(brick)) {
					brick.move(1);
				}else if(gameBoard.checkRightCollision(brick)) {
					brick.move(-1);
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
	public boolean placementIsValid() {
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
		g.drawString("Score:" + gameBoard.getScore(), (SCREEN_WIDTH - metrics2.stringWidth("Score: " + gameBoard.getScore()))/2, (SCREEN_HEIGHT/2+40));
	
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free", Font.BOLD, 20));
		FontMetrics metrics3 = getFontMetrics(g.getFont());
		g.drawString("Press spacebar to play again", (SCREEN_WIDTH - metrics3.stringWidth("Press spacebar to play again"))/2, (SCREEN_HEIGHT/2+70));
		
		
	}
}
