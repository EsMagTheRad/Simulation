package simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Floor extends Collideable{

	public Floor(int x, int y, int id, int scale) {
		super(x, y, id, scale);
	}
	public void render(Graphics g){
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect((int)(x*13),(int)(y*13), 13, 13);
	}
	public Rectangle getBounds() {
		return new Rectangle(100, 100, 0, 0);
	}
}
