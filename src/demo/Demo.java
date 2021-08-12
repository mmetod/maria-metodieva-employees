package demo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import data.EmployeeDAO;
import domain.Calculation;

public class Demo {
	/* Run demo */

	/* Prints to console:
	 * 1.Any errors for invalid data in txt file (in red)
	 * 2.The result
	 * 	2.1.The two employees who have worked together longest in one project
	 * 	2.2.The two employees who have worked together longest across all projects 
	 * 		(if two employees have worked together 1 year in project 1 and 1 year in project 2
	 * 		that makes total of 2 years across all projects)  */
	public static void main(String[] args) throws ParseException {
	
		new Calculation(EmployeeDAO.read());
		
		int[] str = Calculation.getMaxDaysInSingleProject();
		if(str!=null) {
		System.out.println("Best collaboration is between Employee ID "
				+str[0]+" and Employee ID "+str[1]+" in project ID "
				+str[2]+".\nThe collaboration was "+str[3]+ " days long.");
	}
		else {
			System.out.println("No collaborations");
		}
		
		System.out.println();
		str = Calculation.getMaxDaysAcrossAllProjects();
		if(str!=null) {
		System.out.println("Best collaboration across all projects is between Employee ID "
				+str[0]+" and Employee ID "+str[1]+
				".\nThe collaboration was "+str[2]+ " days long.");
		System.out.println("The best duo worked together in the following projects: "
				+Calculation.getProjectsForBestDuo());
	}
		else {
			System.out.println("No collaborations");
		}
	}

}
