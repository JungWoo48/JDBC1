package edu.kh.jdbc1.model.vo;

public class Employee {

	private String empName;
	private String jobName;
	private int salary;
	private int annualincome;
	
	public Employee() {}

	public Employee(String empName, String jobName, int salary, int annualincome) {
		super();
		this.empName = empName;
		this.jobName = jobName;
		this.salary = salary;
		this.annualincome = annualincome;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getAnnualincome() {
		return annualincome;
	}

	public void setAnnualincome(int annualincome) {
		this.annualincome = annualincome;
	}

	@Override
	public String toString() {
		return empName +  " / " + jobName + " / " + salary + " / " + annualincome;
	}
	
	
}
