package domain;


import java.util.Date;

public class EmployeeEntry {
	/* represents one row (entry) from the txt file
	 */
	private int empID;
	private int projectID;
	private Date dateFrom;
	private Date dateTo;

	
	public EmployeeEntry(int empID, int projectID, Date dateFrom, Date dateTo) {
		this.empID = empID;
		this.projectID = projectID;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}


	public int getEmpID() {
		return empID;
	}


	public int getProjectID() {
		return projectID;
	}


	public Date getDateFrom() {
		return dateFrom;
	}


	public Date getDateTo() {
		return dateTo;
	}
	
	
	
	@Override
	public String toString() {
		return "Employee ID=" + empID + ", Project ID=" + projectID + "\n";
	}


	private boolean hasWorkedWith (EmployeeEntry otherEmployee) {
		/*
		 * returns if the two entries represent a collaboration between 2 employees
		 * or there is no collaboration
		 */
		return !((this.dateFrom.after(otherEmployee.dateTo))||
				(this.dateTo.before(otherEmployee.dateFrom))||
				(this.empID==otherEmployee.empID)||
				(this.projectID!=otherEmployee.projectID));
	}
	
	public int getTimeTogetherWith (EmployeeEntry otherEmployee) {
		/*if there is a collaboration, returns the days of the collaboration
		 * 
		 */
		if(!hasWorkedWith(otherEmployee)) {
			return 0;
		}
		Date dateFrom = (this.dateFrom.after(otherEmployee.dateFrom))?
				this.dateFrom:otherEmployee.dateFrom;
		Date dateTo = (this.dateTo.before(otherEmployee.dateTo))?
				this.dateTo:otherEmployee.dateTo;
		long time = dateTo.getTime()-dateFrom.getTime();
		int days = (int) (time/1000/60/60/24);
		return days;
	}
	
	

}
