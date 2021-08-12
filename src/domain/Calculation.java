package domain;

import java.util.ArrayList;

public class Calculation {
	/*Calculates the result */
private int employee1;
private int employee2;
private int projectID;
private int days;
static ArrayList<EmployeeEntry> employees;

public Calculation(ArrayList<EmployeeEntry> employees) {
	Calculation.employees = employees;
	}

public Calculation() {
	super();
	}

/* Calculates time spent together (in days) for two entries
 * May contain more than 1 object with the same employee1, employee2 and project
  */
private static ArrayList<Calculation> getAllColaborations() {
	if(Calculation.employees==null) {
		return null;
	}
	if(Calculation.employees.size()<=1) {
		return null;
	}
	ArrayList<Calculation> allColabs = new ArrayList<Calculation>();
	for(int i=0;i<Calculation.employees.size();i++) {
		for(int j=i+1;j<Calculation.employees.size();j++) {
			if(employees.get(i).getProjectID()==employees.get(j).getProjectID()) {
			Calculation calc = new Calculation();
			calc.employee1 = employees.get(i).getEmpID();
			calc.employee2 = employees.get(j).getEmpID();
			calc.projectID = employees.get(i).getProjectID();
			calc.days = employees.get(i).getTimeTogetherWith(employees.get(j));
			//System.out.println(i+"-"+j+"-"+calc.days);
			if(calc.days>0) {
			allColabs.add(calc);
			}
			}
			}
	}
	return allColabs;
	
}

private static ArrayList<Calculation> joinColabsByProject() {
	/*the result does not contain more than 1 object with the same employee1, employee2 and project
	 * May contain more than 1 object with the same employee1, employee2 and different project 
	 */
	ArrayList<Calculation> colabs = getAllColaborations();
	if(colabs==null) {
		return null;
	}
	for(int i=0; i<colabs.size();i++) {
		for(int j=i+1; j<colabs.size();j++) {
			if(colabs.get(i).projectID==colabs.get(j).projectID) {
				if(((colabs.get(i).employee1==colabs.get(j).employee1)
							&&(colabs.get(i).employee2==colabs.get(j).employee2))
						||((colabs.get(i).employee1==colabs.get(j).employee2)
							&&(colabs.get(i).employee2==colabs.get(j).employee1)))
						{
					colabs.get(i).days += colabs.get(j).days;
					colabs.remove(j);
					j--;
				}
			}
		}
	}
	
	return colabs;
}

private static ArrayList<Calculation> joinColabs() {
	/*
	 * the result does not contain two objects with the same employee1 and employee2
	 */
	ArrayList<Calculation> colabs = joinColabsByProject();
	if(colabs==null) {
		return null;
	}
	for(int i=0; i<colabs.size();i++) {
		for(int j=i+1; j<colabs.size();j++) {
				if(((colabs.get(i).employee1==colabs.get(j).employee1)
							&&(colabs.get(i).employee2==colabs.get(j).employee2))
						||((colabs.get(i).employee1==colabs.get(j).employee2)
							&&(colabs.get(i).employee2==colabs.get(j).employee1)))
						{
					colabs.get(i).days += colabs.get(j).days;
					colabs.remove(j);
					j--;
				}
		}
	}
	
	return colabs;
}

private static Calculation getMaxByProject() {
	/*returns the two employees who have worked together longest
	 * in 1 project
	 */
	Calculation calc = null;
	if(joinColabsByProject()!=null) {
		calc = joinColabsByProject().get(0);
		for(int i=0; i<joinColabsByProject().size();i++) {
			if(calc.days<joinColabsByProject().get(i).days) {
				calc = joinColabsByProject().get(i);
			}
		}
	}
	return calc;
}

private static Calculation getMax() {
	/*returns the two employees who have worked longest
	 * across all projects
	 */
	Calculation calc = null;
	if(joinColabs()!=null) {
		calc = joinColabs().get(0);
		for(int i=0; i<joinColabs().size();i++) {
			if(calc.days<joinColabs().get(i).days) {
				calc = joinColabs().get(i);
			}
		}
	}
	return calc;
}

public static int[] getMaxDaysInSingleProject() {
	/*public method - returns only the values
	 * that need to be printed 
	 * (the attributes of the object representing the longest collaboration
	 * in 1 project
	 */
	int[] max = new int[4];
	if(getMaxByProject()==null) {return null;}
	max[0] = getMaxByProject().employee1;
	max[1] = getMaxByProject().employee2;
	max[2] = getMaxByProject().projectID;
	max[3] = getMaxByProject().days;
	return max;
}

public static int[] getMaxDaysAcrossAllProjects() {
	/*public method - returns only the values
	 * that need to be printed 
	 * (the attributes of the object representing the longest collaboration
	 * across all projects
	 */
	int[] max = new int[3];
	if(getMax()==null) {return null;}
	max[0] = getMax().employee1;
	max[1] = getMax().employee2;
	max[2] = getMax().days;
	return max;
}

public static ArrayList<Integer> getProjectsForBestDuo() {
	/*public method - returns the projects ID, in which
	 * the two employees with the longest collaboration across all projects
	 * have worked together
	 */
	ArrayList<Integer> projects = new ArrayList<Integer>();
	if(getMax()==null) {return null;}
	ArrayList<Calculation> colabs = joinColabsByProject();
	for(int i=0; i<colabs.size();i++) {
				if(((colabs.get(i).employee1==getMax().employee1)
							&&(colabs.get(i).employee2==getMax().employee2))
						||((colabs.get(i).employee1==getMax().employee2)
							&&(colabs.get(i).employee2==getMax().employee1)))
						{
					
					projects.add(colabs.get(i).projectID);
					
				}
			
		
	}
	return projects;
}

}


