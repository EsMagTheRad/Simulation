package simulation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SpringLayout.Constraints;

public class Window extends JFrame{
	
	private JTextArea testArea = new JTextArea("Enter size");
	private JSlider sizeadjust, speedadjust;
	private Container controlcontainer = new Container();
	private boolean started;
	private Hashtable lableTable, lableTable2;
	
	public Window(int w, int h, String title){
		started = false;
		JButton b1 = new JButton("START");
		sizeadjust = new JSlider();
		speedadjust = new JSlider();
		lableTable = new Hashtable();
		lableTable2 = new Hashtable();
		
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
		
		sizeadjust.setMajorTickSpacing(10);
		sizeadjust.setMinorTickSpacing(1);
		sizeadjust.setMaximum(10);
		sizeadjust.setMinimum(1);
		sizeadjust.setPaintTicks(true);
		sizeadjust.setPaintLabels(true);
		sizeadjust.setValue(1);
		
		lableTable.put(new Integer(1), new JLabel("Small"));
		lableTable.put(new Integer(10), new JLabel("Big"));
		lableTable.put(new Integer(6), new JLabel("Objectsize"));
		sizeadjust.setLabelTable(lableTable);
		
		speedadjust.setMajorTickSpacing(10);
		speedadjust.setMinorTickSpacing(1);
		speedadjust.setMaximum(10);
		speedadjust.setMinimum(1);
		speedadjust.setPaintTicks(true);
		speedadjust.setPaintLabels(true);
		speedadjust.setValue(1);
		
		lableTable2.put(new Integer(1), new JLabel("Slow"));
		lableTable2.put(new Integer(10), new JLabel("Fast"));
		lableTable2.put(new Integer(6), new JLabel("ObjectSpeed"));
		speedadjust.setLabelTable(lableTable2);
		
		GridLayout settingsLayout = new GridLayout(1, 3);
		settingsLayout.setHgap(25);
		controlcontainer.setLayout(settingsLayout);
		controlcontainer.add(b1);
		controlcontainer.add(speedadjust);
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
		int size = sizeadjust.getValue();
		return size;
	}
	public int getSpeed(){
		int speed = speedadjust.getValue();
		return speed;
	}
	public boolean hasStarted(){
		return started;
	}
	public static void main(String[] args){
		new Window(920, 620, "SIMULATION");
	}
}
