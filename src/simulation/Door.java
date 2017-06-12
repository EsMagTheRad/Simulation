package simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Door extends SimulatedObject {
	public Door(int x, int y, int id, int scale) {
		super(x,y,id,scale);
	}
	public Rectangle getBounds() {
		return new Rectangle((int)(x*13),(int)(y*13), 13, 13);
		
	}
	public void render(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect((int)(x*13),(int)(y*13), 13, 13);
	}
}
