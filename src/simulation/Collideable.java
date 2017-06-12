package simulation;

import java.awt.Graphics;
import java.awt.Rectangle;
public abstract class Collideable {
	
	protected float x, y, id, scale;
	/**
	 * 	 Object id List:
	 * > Test- subject: 0
	 * > Wall: 1
	 * > Window: 2
	 * > Door: 3
	 * > Property: 4
	 * > Floor: 5
	 **/
	public Collideable(float x, float y, float id, float scale)
	{
		this.x = x;
		this.y = y;
		this.id = id;
		this.scale = scale;
	}

	public void render(Graphics g){
		
	}
	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setScale(float scale){
		this.scale = scale;
	}
	public abstract Rectangle getBounds();
}
