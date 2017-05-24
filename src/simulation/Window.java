package simulation;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SpringLayout.Constraints;

public class Window {
	
	private JTextArea testArea = new JTextArea("Enter size");
	private Container controlcontainer = new Container();
	
	public Window(int w, int h, String title, Simulation sim){
		sim.setPreferredSize(new Dimension(w, h));
		sim.setMaximumSize(new Dimension(w, h));
		sim.setMinimumSize(new Dimension(w, h));
		
		JButton b1 = new JButton("one");
		JButton b2 = new JButton("two");
		
		controlcontainer.setLayout(new GridLayout(1, 3));
		controlcontainer.add(b1);
		controlcontainer.add(b2);
		controlcontainer.add(testArea);
		
		JFrame frame = new JFrame(title);
		frame.getContentPane().setLayout(new BorderLayout());
		//frame.add(testArea, BorderLayout.EAST);
		frame.add(sim, BorderLayout.WEST);
		frame.add(controlcontainer, BorderLayout.NORTH);
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		
		sim.start(5);
	}
	public static void main(String[] args){
		new Window(920, 620, "SIMULATION", new Simulation());
	}
}
