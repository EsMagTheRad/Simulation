package simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class MonthToString {
	public MonthToString(){
		
	}
	public String getmonth(){
		String month = "month";
		try {
			File monthText = new File("Month.txt");
			Scanner scanner = new Scanner(monthText);
			int m = scanner.nextInt();

			month = getMonthFromInt(m);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return month;
	}
	public String getMonthFromInt(int m){
		String month = "";

		if(m == 1) month ="January";
		if(m == 2) month ="February";
		if(m == 3) month ="March";
		if(m == 4) month ="April";
		if(m == 5) month ="May";
		if(m == 6) month ="June";
		if(m == 7) month ="July";
		if(m == 8) month ="August";
		if(m == 9) month ="September";
		if(m == 10) month ="October";
		if(m == 11) month ="November";
		if(m == 12) month ="December";
		
		return month;
	}
}
