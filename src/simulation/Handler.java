package simulation;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;


public class Handler {
	public LinkedList<Wall> objectList = new LinkedList<Wall>();
	
	private Wall tempObject;
	Random rand = new Random();
	

	public void tick()
	{
		for (int i = 0; i < objectList.size(); i++){
			tempObject = objectList.get(i);

		}
	}
	public void render(Graphics g){
		for(int i = 0; i < objectList.size(); i++){
			tempObject = objectList.get(i);
			tempObject.render(g);
		}
	}
	public void addObject(Wall object){
		objectList.add(object);
	}
	public void removeObject(Wall object){
		objectList.remove(object);
	}

}
