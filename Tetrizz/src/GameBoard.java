import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;

public class GameBoard extends JPanel{

	static int SCREEN_HEIGHT;
	static int SCREEN_WIDTH;
	static int UNIT_SIZE;
	
	int board [][];
	
	int score = 0;
	int level = 1;
	public GameBoard(int w, int h, int u) {
		SCREEN_WIDTH = w;
		SCREEN_HEIGHT = h;
		UNIT_SIZE = u;
		board = new int[w/u][h/u];
		
		for(int c = 0; c < h/u; c++) {
			for(int r = 0; r < w/u; r++ ) {
				if(c == h/u-1 || r == 0 || r == w/u-1) {
					board[r][c] = 2;
				}
			}
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void placeBlock(Brick b) {
		int temp [][] = b.getShape(); 
		for(int i = 0; i < temp.length; i++) {
			for(int k = 0; k < temp[i].length; k++) {
				if(temp[i][k] == 1) {
					board[(b.getX()+(i*UNIT_SIZE))/UNIT_SIZE][(b.getY()+(k*UNIT_SIZE))/UNIT_SIZE-1] = 1;	
				}
			}
		}
	}
	
	public void draw(Graphics g) {
		for(int i = 0; i < board.length; i++) {
			for(int k = 0; k < board[i].length; k++) {
				if(board[i][k] == 1) {
					g.setColor(Color.blue);
					g.fillRect(i*UNIT_SIZE,k*UNIT_SIZE,UNIT_SIZE, UNIT_SIZE);
				}else if(board[i][k] == 2) {
					g.setColor(Color.red);
					g.fillRect(i*UNIT_SIZE,k*UNIT_SIZE,UNIT_SIZE, UNIT_SIZE);
				}
			}
		}
		g.setColor(Color.gray);
		g.drawLine(0, 3*UNIT_SIZE, SCREEN_WIDTH, 3*UNIT_SIZE);
	}
	
	public boolean checkBottomCollision(Brick b) {
		int[][] shape = b.getShape();
		for(int i = 0; i < shape.length; i++) {
			for(int k = 0; k < shape[i].length; k++) {
				if(shape[i][k] == 1 && board[b.getX()/UNIT_SIZE+i][b.getY()/UNIT_SIZE+k] == 1 ||
						shape[i][k] == 1 && board[b.getX()/UNIT_SIZE+i][b.getY()/UNIT_SIZE+k] == 2) {
					return true;
				}
			}	
		}
		return false;
	}
	
	public boolean checkBottomCollisionFast(Brick b) {
		int[][] shape = b.getShape();
		for(int i = 0; i < shape.length; i++) {
			for(int k = 0; k < shape[i].length; k++) {
				if(shape[i][k] == 1 && board[b.getX()/UNIT_SIZE+i][b.getY()/UNIT_SIZE+k+1] == 1 ||
						shape[i][k] == 1 && board[b.getX()/UNIT_SIZE+i][b.getY()/UNIT_SIZE+k+1] == 2) {
					return true;
				}
			}	
		}
		return false;
	}
	public boolean checkLeftCollision(Brick b) {
		if(b.getX()/UNIT_SIZE == 0) {				
			return true;
		}
		int[][] shape = b.getShape();
		for(int i = 0; i < shape.length; i++) {
			for(int k = 0; k < shape[i].length; k++) {
				if(shape[i][k] == 1 && (board[b.getX()/UNIT_SIZE+i-1][b.getY()/UNIT_SIZE+k] == 1 || board[b.getX()/UNIT_SIZE+i-1][b.getY()/UNIT_SIZE+k] == 2)) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean checkRightCollision(Brick b) {
		if(b.getX()/UNIT_SIZE+b.getLength() == SCREEN_WIDTH/UNIT_SIZE) {	
			return true;
		}
		int[][] shape = b.getShape();
		for(int i = 0; i < shape.length; i++) {
			for(int k = 0; k < shape[i].length; k++) {
				if(shape[i][k] == 1 && (board[b.getX()/UNIT_SIZE+i+1][b.getY()/UNIT_SIZE+k] == 1 || board[b.getX()/UNIT_SIZE+i+1][b.getY()/UNIT_SIZE+k] == 2)) {
					return true;
				}
			}
		}
		return false;
	}

	public void checkFullRow() {
		int[] rowIndex = new int[0];
		for(int r = 0; r < board[0].length-1; r++) {
			boolean found = false;
			for(int c = 0; c < board.length; c++) {
				if(board[c][r] == 0) {
					found = true;
					break;
				}
			}
			if(!found) {
				int[] temp = Arrays.copyOf(rowIndex, rowIndex.length+1);
				temp[rowIndex.length] = r;
				rowIndex = temp;
				for(int i = 1; i < board.length-1; i++) {
					board[i][r] = 0;
				}
			}
		}
		switch(rowIndex.length) {
		case 1: 
			score += rowIndex.length*10;
			break;
		case 2:
			score += rowIndex.length*15;
			break;
		case 3:
			score += rowIndex.length*20;
			break;
		}
		moveRows(rowIndex);
	}
	public void moveRows(int[] rowIndex) {
		if(rowIndex.length > 0) {
			for(int index: rowIndex) {
				for(int k = index; k > 0; k--) {
					for(int c = 0; c < 14; c++) {						
						board[c][k] = board[c][k-1];
					}
				}	
			}
		}
	}
	public int getScore() {
		return score;
	}
}

