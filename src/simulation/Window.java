package simulation;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;

public class Window extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4091618198331241697L;
	private JSlider sizeadjust, speedadjust;
	JButton save, b1;
	private Container controlcontainer, checkboxContainer;
	private boolean started, windowtesting, surveillancetesting, doortesting, nextMonth;
	private Hashtable lableTable, lableTable2;
	private JCheckBox windowBox, surveillanceBox, doorBox;
	static int windowcount = 0, doorcount = 0, propertycount = 0;
	private  int month;
	Scanner scanner;
	
	//DELETE
	static int[] testArray  = new int[12];
	
	public Window(int w, int h, String title){
		
		
		loadActivations();
		loadMonth();
		
		propertycount = 0;
		windowcount = 0;
		doorcount = 0;
		
		started = false;
		windowtesting = false;
		doortesting = false;
		surveillancetesting = false;
		nextMonth = false;
		controlcontainer = new Container();
		b1 = new JButton("Move Automatic");
		save = new JButton("Save Data for: " + new MonthToString().getMonthFromInt(month));
		sizeadjust = new JSlider();
		speedadjust = new JSlider();
		lableTable = new Hashtable();
		lableTable2 = new Hashtable();
		
		
		
		//Init all contents of the head- Container
		b1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (b1.getText()=="Move Automatic") {
				started = true;
				b1.setText("WASD");
				} else {
					started = false;
					b1.setText("Move Automatic");
				}
			}
		});
		// TODO Override changed int
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				nextMonth = true;
				month = month+1;
				save.setText("Save Data for: " + new MonthToString().getMonthFromInt(month));
				if(month >12){	month = 1; }
	
				File file = new File("Month.txt");
				if (file.exists());{
					file.delete();
				}
				BufferedWriter writer = null;
				try {
					  writer = new BufferedWriter( new FileWriter( "Month.txt"));
					    writer.write(String.valueOf(month));

					}
					catch ( IOException e1)
					{
					}
					finally
					{
					    try
					    {
					        if ( writer != null)
					        writer.close( );
					    }
					    catch ( IOException e1)
					    {
					    }
				    }
				
				writeStringToFile();
				System.out.println("called:" + month);
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
	
	private void writeStringToFile(){
		
		int totalActivations = windowcount + doorcount + propertycount;
		testArray[month-1] = totalActivations;
		
		File file = new File("Activations.txt");
		if (file.exists());{
			file.delete();
		}
		try {
			 FileWriter f_writer = new FileWriter(file, true);
		    
			 	for (int i = 0; i < testArray.length; i++) {
		                  f_writer.write(testArray[i]+ " ");
		            }
		        f_writer.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		
	}
	public void setActivationList(int[] list){
		testArray = list;
	}
	
	public void setWindowcount(int count){
		windowcount = windowcount + count;
		
	}
	public void setPropertycount(int count){
		propertycount = propertycount + count;
		System.out.println("" + propertycount);
	}
	public void setDoorcount(int count){
		doorcount = doorcount + count;
	}
	
	public void loadActivations(){
		try {
			scanner = new Scanner(new File("Activations.txt"));
			testArray = new int [12];
			int i = 0;
			while(scanner.hasNextInt())
			{
			     testArray[i++] = scanner.nextInt();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadMonth(){
		try {
			scanner = new Scanner(new File("Month.txt"));
			month = scanner.nextInt();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean isNextMonth(){
		return nextMonth;
	}
	public void setNextMonth(boolean nextMonth){
		this.nextMonth = nextMonth;
	}

}