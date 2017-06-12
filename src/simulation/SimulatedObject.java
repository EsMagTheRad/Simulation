package simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class SimulatedObject extends Collideable{


	public SimulatedObject(float x, float y, float id, float scale){
		super(x, y, id, scale);
	}
	public Rectangle getBounds() {
		 return new Rectangle((int)x,(int) y,(int) (5* scale),(int) (5*scale));
	}
	public Rectangle getBoundsTop() {
		 return new Rectangle((int)(x+scale),(int) y,(int) (3* scale),(int) scale);
		
	}public Rectangle getBoundsBottom() {
		 return new Rectangle((int)(x+scale),(int) (y+4* scale),(int) (3* scale),(int) scale);
			
	}public Rectangle getBoundsLeft() {
		 return new Rectangle((int)x, (int)(y+scale),(int)scale,(int) (3*scale));
			
	}public Rectangle getBoundsRight() {
		 return new Rectangle((int)(x+4*scale),(int) (y+scale),(int) scale,(int) (3*scale));
			
	}

	public void render(Graphics g){
		g.setColor(Color.RED);
		g.fillOval((int)x, (int)y, (int)(5* scale), (int)(5*scale));
	}

}
