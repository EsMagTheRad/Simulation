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

import javax.imageio.ImageIO;
import javax.swing.JTextField;
public class Simulation extends Canvas implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7661068758602793816L;
	
	private JTextField size;
	private BufferedImage outline;
	private Handler handler;
	private boolean running = false;
	private Thread thread;
	private Graphics g;
	
	public Simulation(){
		size = new JTextField("Enter Size");
		try{
			outline = ImageIO.read(getClass().getResource("outline.png"));
		}catch (IOException e){
			e.printStackTrace();
		}
	this.addMouseListener(new MouseListener() {
			
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int xpos = e.getX();
				int ypos = e.getY();
				g.setColor(Color.RED);
				g.drawOval(xpos, ypos, 20, 20);
				System.out.println("drawn");
			}

			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) 	{}
			public void mouseReleased(MouseEvent e) {}
		});
		
	}
	public void start(int size){
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
			g.dispose();
		}
	}
	
	public void init(Graphics g){
		int w = outline.getWidth();
		int h = outline.getHeight();
		
		for(int i = 0; i< w; i++){
			for(int k = 0; k<h; k++){
				int pixel = outline.getRGB(i, k);
				int red = (pixel >> 16) & 0xff;;
					
					if(red == 255 ){
						g.setColor(Color.GRAY);
						g.fillRect(i*16, k*16, 16, 16);
					}; 
				}
			}
	}
}
