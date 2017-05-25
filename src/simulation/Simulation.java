package simulation;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
public class Simulation extends Canvas implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7661068758602793816L;
	private BufferedImage outline;
	private boolean running = false;
	private Thread thread;
	private Graphics g;
	private int xpos = -100, ypos = -100, scale = 0, t = 0;
	private Random random = new Random();
	private Window window;
	
	public Simulation(Window window){
		this.window = window;
		try{
			outline = ImageIO.read(getClass().getResource("outline.png"));
		}catch (IOException e){
			e.printStackTrace();
		}
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

		this.requestFocus();
		long lastTime = System.nanoTime();
		double ticks = 60.0;
		double ns = 1000000000 / ticks; //one sec div by desired FPS
		double delta = 0;
		
		while (running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1){
				//tick();
				delta--;
			}
			render();
		}
	}
	
	private void render(){
		this.createBufferStrategy(2);
		boolean running = true;
		
		BufferStrategy strat;
		
		while(running){
			strat = this.getBufferStrategy();
			g = strat.getDrawGraphics();
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 920, 620);
			init(g);
			
			strat.show();
			//g.dispose();
		}
	}
	
	public void init(Graphics g){
		int w = outline.getWidth();
		int h = outline.getHeight();
		scale = window.getScale();
	
		for(int i = 0; i< w; i++){
			for(int k = 0; k<h; k++){
				int pixel = outline.getRGB(i, k);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
					
					if(red == 255 && green == 0 && blue == 0 ) {
						g.setColor(Color.GRAY);
						g.fillRect(i*13, k*13, 13, 13);
					}
					else if(red == 0 && green == 0 && blue == 255) {
						g.setColor(Color.lightGray);
						g.fillRect(i*13, k*13, 13, 13);
					}
					else if(red == 0 && green == 255 && blue == 0 && window.isSurveillance()==true) {
						g.setColor(Color.pink);
						g.fillRect(i*13, k*13, 13, 13);
					}
					else if(red == 255 && green == 255 && blue == 255 && window.isTestingWindow() == true) {
						g.setColor(Color.cyan);
						g.fillRect(i*13, k*13, 13, 13);
					}
					else if(red == 96 && green == 96 && blue == 96 && window.isTestingDoor() == true) {
						g.setColor(Color.BLUE);
						g.fillRect(i*13, k*13, 13, 13);
					}
				}
			g.setColor(Color.RED);//Draws Red Simulation Area
			g.fillOval(xpos, ypos, 6* scale, 6* scale);

			if(t < 10000){
				t++;
			}else if (window.hasStarted()==true){

				g.fillOval(xpos, ypos, 6* scale, 6* scale);

				xpos = xpos-window.getSpeed();
				ypos = ypos-window.getSpeed();
				t = 0;
			}else if (window.hasStarted()==false){
				g.fillOval(xpos, ypos, 6* scale, 6* scale);
				t=0;
			}
			}
	}
}
