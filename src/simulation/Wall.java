package simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Wall extends Collideable {

	public Wall(float x, float y, float id, float scale){
		super(x, y, id, scale);
	}

	public Rectangle getBounds() {
		return new Rectangle( (int)(x*13),(int) (y*13),(int) 13,(int) 13);

	}
	public void render(Graphics g){
		g.setColor(Color.GRAY);
		g.fillRect((int) (x*13),(int) (y*13),(int) 13,(int) 13);
	}
	

}
