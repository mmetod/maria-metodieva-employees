package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import domain.EmployeeEntry;

public class EmployeeDAO {
/* Data Access Object */
	
	/* reads txt file 
	 * with fields empID (employee ID), projectID, dateFrom, dateTo*/
	public static ArrayList<EmployeeEntry> read() {
		ArrayList<EmployeeEntry> employees = new ArrayList<EmployeeEntry>();
		try {
			Scanner in = new Scanner(new File("./files/employees.txt"));
			while(in.hasNextLine()) {
				String [] line = in.nextLine().split(", ");
				
				int empID = -1;
				try {
				empID = Integer.parseInt(line[0]);
				} catch(NullPointerException | NumberFormatException e) {
					System.err.println("Invalid data will be excluded from calculation. Check your file for mistakes.");
					continue;
				}
				if(empID<0) {
					System.err.println("Every row should have Employee ID. The ID should be whole positive number.");
					continue;
				}
				
				int projectID = -1;
				try {
					projectID = Integer.parseInt(line[1]);
					} catch(NullPointerException | NumberFormatException e) {
						System.err.println("Invalid data will be excluded from calculation. Check your file for mistakes.");
						continue;
					}
					if(projectID<0) {
						System.err.println("Every row should have Project ID. The ID should be whole positive number.");
						continue;
					}
				
				String date = null;
				try {
					date = line[2];
					} catch(NullPointerException e) {
						System.err.println("Invalid data will be excluded from calculation. Check your file for mistakes.");
						continue;
					}
				
				Date dateFrom = formatRandomDateFormat(date);
				if(dateFrom==null) {
					System.err.println("Wrong format Date from for "
							+ "employee ID "+empID+" and project ID "
							+projectID+". The entry is invalid and will be"
									+ " excluded from calculation");
					continue;
				}
				
				date = null;
				try {
					date = line[3];
					} catch(NullPointerException e) {
						System.err.println("Invalid data will be excluded from calculation. Check your file for mistakes.");
						continue;
					}
				Date dateTo;
				if(date.toLowerCase().equals("null")) {
					dateTo = new Date();
				}
				else {
					dateTo = formatRandomDateFormat(date);
					if(dateTo==null) {
						System.err.println("Wrong format Date To for "
								+ "employee ID "+empID+" and project ID "
								+projectID+". The entry is invalid and will be"
										+ " excluded from calculation\n");
						continue;
					}
				}
				
				
				if(dateTo.before(dateFrom)) {
					System.err.println("Wrong period for "
							+ "employee ID "+empID+" and project ID "
							+projectID+". Date From cannot be after Date To. "
							+ "The entry is invalid and will be"
							+ " excluded from calculation\n");
					
					continue;
				}
			
				employees.add(new EmployeeEntry(empID,projectID,dateFrom,dateTo));		
				
				
			}
			
			in.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found. Table count = 0");
		}
		
		return employees;
	}
	
	
	/* Formats dates in txt file
	 * Works for 5 formats: yyyy-MM-dd, yyyy.MM.dd, yyyy/MM/dd
	 * dd.MM.yyyy, dd/MM/yyyy
	 * A date is eligible if after 01.01.1900
	 */
	private static Date formatRandomDateFormat(String str) {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateInPast = new Date();
		try {
			dateInPast = dateFormat.parse("1900-01-01");
		} catch (ParseException e6) {
			
		}
		try {
			date = dateFormat.parse(str);
			if(date.before(dateInPast)) {
				new ParseException("date",0);
			}
			} catch (ParseException e) {
				dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				try {
					date = dateFormat.parse(str);
					if(date.before(dateInPast)) {
						throw new ParseException("date",0);
					}
				} catch (ParseException e1) {
					dateFormat = new SimpleDateFormat("yyyy.MM.dd");
					try {
						date = dateFormat.parse(str);
						if(date.before(dateInPast)) {
							throw new ParseException("date",0);
						}
					} catch (ParseException e2) {
						
							dateFormat = new SimpleDateFormat("dd.MM.yyyy");
							try {
								date = dateFormat.parse(str);
								if(date.before(dateInPast)) {
									throw new ParseException("date",0);
								}
							} catch (ParseException e4) {
								dateFormat = new SimpleDateFormat("dd/MM/yyyy");
								try {
									date = dateFormat.parse(str);
									if(date.before(dateInPast)) {
										throw new ParseException("date",0);
									}
								} catch (ParseException e5) {
									date=null;
								}
							}
						}
					}
				}
			
		return date;
	}
}
