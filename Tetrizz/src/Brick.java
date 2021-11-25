import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Brick {
	static int UNIT_SIZE;
	static int[][] shape;
	int posX;
	int posY;
	Random rand = new Random();
	
	public Brick(int x, int y, int size) {
		posX = x;
		posY = y;
		UNIT_SIZE = size;
		shape = brickShape(rand.nextInt(7));
	}
	public void draw(Graphics g) {
		g.setColor(Color.green);
		for(int i = 0; i < shape.length; i++) {
			for(int k = 0; k < shape[i].length; k++) {
				if(shape[i][k] == 1) {
					g.fillRect(posX+(i*UNIT_SIZE), posY+(k*UNIT_SIZE), UNIT_SIZE,UNIT_SIZE);				
				}
			}
		}
	}
	public void setPosition(int x, int y) {
		posX = x;
		posY = y;
	}
	public int getY() {
		return posY;	
	}
	public int getX() {
		return posX;
	}
	
	public int[][] getShape() {
		return shape;
	}
	public int getHeigth() {
		return shape[0].length;
	}
	public int getLength() {
		return shape.length;
	}
	public void drop() {	
		posY = posY + UNIT_SIZE;			
	}
	public void move(int i) {
		posX = posX + (i * UNIT_SIZE);
	}
	public int[][] brickShape(int i) {
		switch(i) {
			//L
			case 0:
				return new int[][] {{0,0,1},{0,0,1},{0,1,1}};
			//Reverse-L
			case 1:
				return new int[][] {{0,1,0},{0,1,0},{0,1,1}};
			//Square
			case 2:
				return new int[][] {{1,1},{1,1}};
			//T
			case 3:
				return new int[][] {{0,1,0},{1,1,1},{0,0,0}};	
			//Z
			case 4:
				return new int[][] {{1,1,0},{0,1,1},{0,0,0}};
			//Reverse-Z
			case 5:
				return new int[][] {{0,1,1},{1,1,0},{0,0,0}};
			//Straight piece
			case 6: 
				return new int[][] {{0,0,0},{1,1,1},{0,0,0}};
			//4X1 straight
				//return new int[][] {{0,0,0,0},{1,1,1,1},{0,0,0,0},{0,0,0,0}};
			default:
				return new int[][] {{1,1},{1,1}};
		}
	}
	static void rotateMatrix(){
        for (int i = 0; i < shape.length; i++) {
           for (int j = i; j < shape[i].length; j++) {
              int temp= shape[i][j];
              shape[i][j]= shape[j][i];
              shape[j][i]= temp;
           }
        }
	  for(int i=0;i<shape.length;i++){
	     int top=0;
	     int bottom = shape.length-1;
	     while(top<bottom){
	        int temp = shape[top][i];
	        shape[top][i]=shape[bottom][i];
	        shape[bottom][i] = temp;
	        top++;
	        bottom--;
	     }
	  }
	}
}
