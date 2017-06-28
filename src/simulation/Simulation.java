package simulation;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
public class Simulation extends Canvas implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7661068758602793816L;
	private BufferedImage outline, exclamation;
	private boolean running = false, intersected = false, alarm = true;
	private boolean win= true, ent= true, prop = true; //Determines if Window, Entrance, or Property-alarmcount shall be ticket up or not
	private Thread thread;
	private Graphics g;
	private float xpos = -100, ypos = -100, scale = 0,  t2 = 0, xmov = 1, ymov = 1, rand;
	private int sensoractivationProperty, sensoractivationWindow, sensoractivationDoory;
	private Random random;
	private Window window;
	private LinkedList<Collideable> objectList;
	private String timeStamp = "", currentMonth = new MonthToString().getmonth();
	private int time = (int) System.currentTimeMillis(), delme = 0;
	private int [] activations;
	
	public Simulation(Window window){
		random = new Random();
		objectList = new LinkedList<Collideable>();
		this.window = window;
		sensoractivationProperty=0; 
		sensoractivationWindow=0;
		sensoractivationDoory=0;
		try{
			outline = ImageIO.read(getClass().getResource("outline.png"));
		}catch (IOException e){
			e.printStackTrace();
		}
		try{
			exclamation = ImageIO.read(getClass().getResource("exclam.png"));
		}catch (IOException e){
			e.printStackTrace();
		}
		this.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {	}
			public void keyReleased(KeyEvent e) {	}
			public void keyPressed(KeyEvent e) {
				
				float speed = window.getSpeed();
				int key = e.getKeyCode();
				if(key == KeyEvent.VK_D){
					xpos = xpos + speed*5;	
					}
				if(key == KeyEvent.VK_W){
					ypos = ypos - speed*5;
				}
				if(key == KeyEvent.VK_S){
					ypos = ypos + speed*5;
				}
				if(key == KeyEvent.VK_A){
					xpos = xpos - speed*5;	
				}
			}
		});
		this.addMouseListener(new MouseListener() {
			
			public void mouseClicked(MouseEvent e) {
					xpos = e.getX();
					ypos = e.getY();
			}
	
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				public void mousePressed(MouseEvent e) 	{}
				public void mouseReleased(MouseEvent e) {}
			});
		
	}


	public void start(){
		if(running){
			return;
			}
		
			else{
			running = true;
			thread = new Thread(this);
			thread.start();
		}		
	}
	public void run (){

		init();
		
		//this.requestFocus();
		long beforeTime = System.currentTimeMillis();
		double timeDiff = 0;
		
		while (running){
			

			timeDiff += (System.currentTimeMillis() - beforeTime) / ((double)(1000 / 60));
			beforeTime = System.currentTimeMillis();
			
			//thread sleeps
			while(timeDiff >= 1){
				render(); //Calls the tick() method of the object- list, witch refreshes the objects in the list
				timeDiff--;
			}
	
		}
	}
	public void init(){
		//int rand = random.nextInt(40);
		int w = outline.getWidth();
		int h = outline.getHeight();
		for(int i = 0; i< w; i++){
			for(int k = 0; k<h; k++){

				if(outline.getRGB(i, k) == -65536)		 {objectList.add(new Wall(i, k, 1, 1));}
				if(outline.getRGB(i, k) == -16776961) 	 {objectList.add(new Floor(i, k, 5, 1));}
				if(outline.getRGB(i, k) == -16711936)	 {objectList.add(new PropertyAlarm(i, k, 4, 1));}
				if(outline.getRGB(i, k) == -1)			 {objectList.add(new Alarmwindow(i, k, 2, 1));}
				if(outline.getRGB(i, k) == -10461088)    {objectList.add(new Door(i, k, 3, 1));}
			}
		}
	}
	private void render(){
		
		this.createBufferStrategy(3);
		boolean running = true;
		
		BufferStrategy strat;
		
		while(running){
			strat = this.getBufferStrategy();
			g = strat.getDrawGraphics();
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 1024, 800);
			g.setColor(Color.green);
			g.drawString("Fenster: Light_Blue", 800, 630);
			g.drawString("Grundstueck: Pink", 800, 660);
			g.drawString("Tuere: White", 800, 690);
			g.drawString("Monat: " + currentMonth, 70,660);
			g.drawString("Anzahl Aktivierungen Grundstueck: " + sensoractivationProperty + "  Fenster: " + sensoractivationWindow + "  Tuere: " + sensoractivationDoory, 70, 690);

			
			for(int i = 0; i < objectList.size(); i++){
				Collideable tempObject = objectList.get(i);
				
				if (tempObject.getId() == 1 ){tempObject.render(g);}
				if (tempObject.getId() == 5 ){tempObject.render(g);}
				if (tempObject.getId() == 2 && window.isTestingWindow()){tempObject.render(g);}
				if (tempObject.getId() == 4 && window.isSurveillance()){tempObject.render(g);}
				if (tempObject.getId() == 3 && window.isTestingDoor()){tempObject.render(g);}
			}
			if(window.isNextMonth() == true){
				
				
				window.setNextMonth(false);
				currentMonth = new MonthToString().getmonth();
				System.out.println(currentMonth);
				sensoractivationDoory = 0;
				sensoractivationProperty = 0;
				sensoractivationWindow = 0;
			}
			
			SimulatedObject simobject = new SimulatedObject(xpos, ypos, 0, 0);
			
			scale = window.getScale();
			simobject.setScale(scale);
			simobject.render(g);
			if (window.hasStarted()==true){
				
				simobject.render(g);
				
				if(t2 < rand){
					t2++;
				}else{
					xmov = random.nextInt(3) -1;
					ymov = random.nextInt(3) -1;
					t2 = 0;
					rand = random.nextInt((200 - 15)+1)+15;
					}
				float speed = window.getSpeed();
				xpos = xpos+speed*xmov;
				ypos = ypos+speed*ymov;
			}else if (window.hasStarted()==false){
				simobject.render(g);
			}
			
			
			//Display information
			for(int i = 0; i < objectList.size(); i++){
				
				g.setColor(Color.GREEN);
				Collideable tempobject = objectList.get(i);
				if(tempobject.getBounds().intersects(simobject.getBounds())&& tempobject.getId()==2 && window.isTestingWindow()==true) {
					setInfoText(g, tempobject);
					g.drawString("Alarm activated: Window", 400, 630);
					//sensoractivationWindow = sensoractivationWindow+1;
					}
					if(tempobject.getBounds().intersects(simobject.getBounds())&& tempobject.getId()==4 && window.isSurveillance()==true) {
					setInfoText(g, tempobject);
					g.drawString("Alarm activated: Property", 400, 660);
					//sensoractivationProperty = sensoractivationProperty+1;
					}
					if(tempobject.getBounds().intersects(simobject.getBounds())&& tempobject.getId()==3 && window.isTestingDoor()==true) {
					setInfoText(g, tempobject);
					g.drawString("Alarm activated: Door", 400, 660);
					//sensoractivationDoory = sensoractivationDoory+1;
					}
				if(tempobject.getBounds().intersects(simobject.getBoundsTop())&&tempobject.getId()==1) {
						ypos = (tempobject.getY()*13)+13;
						xmov = xmov*-1;
						ymov = ymov*-1;
					}
				if(tempobject.getBounds().intersects(simobject.getBoundsBottom())&&tempobject.getId()==1) {
						ypos = (tempobject.getY()*13)-scale*5;
						xmov = xmov*-1;
						ymov = ymov*-1;
				}
				if(tempobject.getBounds().intersects(simobject.getBoundsLeft())&&tempobject.getId()==1) {
						xpos = (tempobject.getX()*13+13);
						xmov = xmov*-1;
						ymov = ymov*-1;
				}
				if(tempobject.getBounds().intersects(simobject.getBoundsRight())&&tempobject.getId()==1 && intersected ==false) {
						xpos = (tempobject.getX()*13-(scale*5));
						xmov = xmov*-1;
						ymov = ymov*-1;
				}

			}
			strat.show();
			//g.dispose();
		}
	}
	public void setInfoText(Graphics g, Collideable tempobject){	
		g.drawImage(exclamation, (int)(tempobject.getX()*13), (int)(tempobject.getY()*13), null);
		timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
		
		if (time - (int) System.currentTimeMillis() <= -3000){
			alarm = true;
			time = (int) System.currentTimeMillis();
		}
		if (tempobject.getId() == 2 && alarm == true){
			sensoractivationWindow++;
			window.setWindowcount(1);
			alarm = false;
		}else if (tempobject.getId() == 3 && alarm == true) {
			sensoractivationDoory++;
			window.setDoorcount(1);
			alarm = false;
		}else if (tempobject.getId() == 4 && alarm == true) {
			sensoractivationProperty++;
			window.setPropertycount(1);
			alarm = false;
		}
	}
	
	
	
}