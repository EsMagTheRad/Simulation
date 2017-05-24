package simulation;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SpringLayout.Constraints;

public class Window extends JFrame{
	
	private JTextArea testArea = new JTextArea("Enter size");
	private JSlider sizeadjust = new JSlider();
	private Container controlcontainer = new Container();
	private boolean started;
	
	public Window(int w, int h, String title){
		started = false;
		JButton b1 = new JButton("START");
		JButton b2 = new JButton("Geschwindigkeit");
		
		b1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (b1.getText()=="START") {
				started = true;
				b1.setText("PAUSE");
				} else {
					started = false;
					b1.setText("START");
				}
			}
		});
		
		sizeadjust.setMaximum(10);
		sizeadjust.setMinimum(1);
		sizeadjust.setValue(1);
		
		controlcontainer.setLayout(new GridLayout(1, 3));
		controlcontainer.add(b1);
		controlcontainer.add(b2);
		controlcontainer.add(sizeadjust);
		
		Simulation sim = new Simulation(this);
		sim.setPreferredSize(new Dimension(w, h));
		sim.setMaximumSize(new Dimension(w, h));
		sim.setMinimumSize(new Dimension(w, h));
		
		
		this.getContentPane().setLayout(new BorderLayout());
		//frame.add(testArea, BorderLayout.EAST);
		this.add(sim, BorderLayout.WEST);
		this.add(controlcontainer, BorderLayout.NORTH);
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		
		
		sim.start();
	}
	public int getScale(){
		int s = sizeadjust.getValue();
		return s;
	}
	public boolean hasStarted(){
		return started;
	}
	public static void main(String[] args){
		new Window(920, 620, "SIMULATION");
	}
}
