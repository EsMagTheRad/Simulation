package simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Wall {
	private int x, y;
	public Wall(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect((int)x, (int)y, 32, 32);
		g.setColor(Color.BLACK);
		g.drawRect((int)x, (int)y, 32, 32);
	}

	public Rectangle getBounds() {

		return new Rectangle((int)x, (int)y, 32, 32);
	}
	

}
