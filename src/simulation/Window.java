package simulation;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.plaf.FileChooserUI;

public class Window extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4091618198331241697L;
	private JSlider sizeadjust, speedadjust;
	private Container controlcontainer, checkboxContainer;
	private boolean started, windowtesting, surveillancetesting, doortesting;
	private Hashtable lableTable, lableTable2;
	private JCheckBox windowBox, surveillanceBox, doorBox;
	private int windowcount = 0, doorcount = 0, propertycount = 0;
	
	public Window(int w, int h, String title){
		started = false;
		windowtesting = false;
		doortesting = false;
		surveillancetesting = false;
		controlcontainer = new Container();
		JButton b1 = new JButton("AUTO");
		JButton save = new JButton("Save");
		sizeadjust = new JSlider();
		speedadjust = new JSlider();
		lableTable = new Hashtable();
		lableTable2 = new Hashtable();
		
		//Init all contents of the head- Container
		b1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (b1.getText()=="AUTO") {
				started = true;
				b1.setText("WASD");
				} else {
					started = false;
					b1.setText("AUTO");
				}
			}
		});
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				writeStringToFile("door: " + doorcount + " window: " + windowcount + " property: " + propertycount + " total: " + (windowcount+propertycount+doorcount));
				
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
		
		speedadjust.setMajorTickSpacing(6);
		speedadjust.setMinorTickSpacing(1);
		speedadjust.setMaximum(6);
		speedadjust.setMinimum(1);
		speedadjust.setPaintTicks(true);
		speedadjust.setPaintLabels(true);
		speedadjust.setValue(1);
		
		lableTable2.put(new Integer(1), new JLabel("Slow"));
		lableTable2.put(new Integer(6), new JLabel("Fast"));
		lableTable2.put(new Integer(3), new JLabel("ObjectSpeed"));
		speedadjust.setLabelTable(lableTable2);
		
		GridLayout settingsLayout = new GridLayout(1, 4);
		settingsLayout.setHgap(25);
		controlcontainer.setLayout(settingsLayout);
		controlcontainer.add(b1);
		controlcontainer.add(speedadjust);
		controlcontainer.add(sizeadjust);
		controlcontainer.add(save);
		
		//init all content of the east- container
		checkboxContainer = new Container();
		checkboxContainer.setLayout(new GridLayout(1, 3));
		windowBox = new JCheckBox("Alarm windows");
		windowBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (windowtesting == false){
					windowtesting = true;
				}else{
					windowtesting = false;
				}
			}
		});
		surveillanceBox = new JCheckBox("Motion detector");
		surveillanceBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (surveillancetesting == false){
					surveillancetesting = true;
				}else{
					surveillancetesting = false;
				}
			}
		});
		doorBox = new JCheckBox("Alarm door");
		doorBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (doortesting == false){
					doortesting = true;
				}else{
					doortesting = false;
				}
			}
		});
		checkboxContainer.add(windowBox);
		checkboxContainer.add(surveillanceBox);
		checkboxContainer.add(doorBox);
		
		
		
		Simulation sim = new Simulation(this);
		sim.setPreferredSize(new Dimension(w, h));
		sim.setMaximumSize(new Dimension(w, h));
		sim.setMinimumSize(new Dimension(w, h));
		
		
		this.getContentPane().setLayout(new BorderLayout());
		this.add(sim, BorderLayout.WEST);
		this.add(controlcontainer, BorderLayout.NORTH);
		this.add(checkboxContainer, BorderLayout.SOUTH);
		
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
	public float getSpeed(){
		float speed = speedadjust.getValue()*0.2f;
		return speed;
	}
	public boolean hasStarted(){
		return started;
	}
	public boolean isTestingWindow(){
		return windowtesting;
	}
	public boolean isSurveillance(){
		return surveillancetesting;
	}	
	public boolean isTestingDoor(){
		return doortesting;
	}
	public static void main(String[] args){
		new Window(1024, 752, "SIMULATION");
	}
	static void writeStringToFile(String data){
		BufferedWriter writer = null;
		try
		{
		    writer = new BufferedWriter( new FileWriter( "Chartdata.txt"));
		    writer.write( data);

		}catch ( IOException e){e.printStackTrace();}
		finally
		{
		    try
		    {
		        if ( writer != null)
		        writer.close( );
		    } catch ( IOException e){e.printStackTrace();}
		}

		
	}
	public void setWindowcount(int count){
		windowcount = windowcount + count;
	}
	public void setPropertycount(int count){
		propertycount = propertycount + count;
	}
	public void setDoorcount(int count){
		doorcount = doorcount + count;
	}
	
}
