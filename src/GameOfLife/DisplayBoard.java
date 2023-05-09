package GameOfLife;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class DisplayBoard extends JPanel{
	Toolkit t=Toolkit.getDefaultToolkit();
	private Gui gui;
	private boolean[][] board,nextBoard;
	private Color[][] bColor;
	private int size,startX,startY,width,height,gap;
	private Color color,emptyColor;
	private boolean H=true;
	
	public DisplayBoard(Gui gui1,int width,int height){
		gui=gui1;
		this.width=width;
		this.height=height;
		gap=1;
		color=new Color(255,255,255);
		emptyColor=new Color(0,0,0);
		board=new boolean[width][height];
		nextBoard=new boolean[width][height];
	}
	
	public void paintComponent(Graphics g) {
		//window.getHeight(); window.getWidth();
		if(this.getWidth()/(width)<=this.getHeight()/(height)) {
			size=(this.getWidth())/(width);
		}else {
			size=(this.getHeight())/(height);
		}
		startX=(int)((gui.getWidth()/2.0)-((size*width)/2.0));
		startY=(int)((gui.getHeight()/2.0)-((size*height)/2.0));
		// Board(Black and white),Highlighted piece, movable spaces
		super.paintComponent(g);
		
		this.setBackground(new Color(40,40,40));
		g.setColor(emptyColor);
		if(this.getWidth()/(width)<=this.getHeight()/(height)) {
			g.fillRect(0, 0, this.getWidth(), startY-gap);
			g.fillRect(0, startY+gap+size*height, this.getWidth(), startY);
		}else {
			g.fillRect(0, 0, startX, this.getHeight());
			g.fillRect(startX+gap+size*width, 0, startX-gap, this.getHeight());
		}
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				if(board[x][y]==true) {
					//color=bColor[x][y];
					g.setColor(color);
				}else {
					g.setColor(emptyColor);
				}
				g.fillRect(startX+x*size+gap, startY+y*size+gap, size-(gap), size-(gap));
			}
		}
	}
	
	public void redraw() {
		this.repaint();
	}
	
	public void update() {
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				int i=0;
				if(x!=width-1&&board[x+1][y]) {
					i++;
				}
				if(y!=height-1&&x!=width-1&&board[x+1][y+1]) {
					i++;
				}
				if(y!=height-1&&board[x][y+1]) {
					i++;
				}
				if(x!=0&&y!=height-1&&board[x-1][y+1]) {
					i++;
				}
				if(x!=0&&board[x-1][y]) {
					i++;
				}
				if(y!=0&&x!=0&&board[x-1][y-1]) {
					i++;
				}
				if(y!=0&&board[x][y-1]) {
					i++;
				}
				if(y!=0&&x!=width-1&&board[x+1][y-1]) {
					i++;
				}
				if(i==3) {
					nextBoard[x][y]=true;
				}else if(i==2&&board[x][y]) {
					nextBoard[x][y]=true;
				}
			}
		}
		board=copy(nextBoard);
		nextBoard=new boolean[width][height];
	}
	
	private boolean[][] copy(boolean board[][]){
		boolean[][] out=new boolean[width][height];
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				out[x][y]=board[x][y];
			}
		}
		return out;
	}
	
	public void place(int location) {
		int y=location/width;
		int x=location%width;
		if(board[x][y]) {
			board[x][y]=false;
		}else {
			board[x][y]=true;
		}
	}
	
	public int getLocation(double x,double y) {
		if(x>startX&&x<startX+(width*size)+gap) {
			if(y>startY&&y<startY+(size*height)+gap) {
				return ((int)((x-startX)/(size)))+((int)((y-startY)/(size))*width);
			}
		}
		return-1;
		
	}
}
