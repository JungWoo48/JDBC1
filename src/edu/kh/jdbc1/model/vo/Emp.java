package edu.kh.jdbc1.model.vo;

// VO(vlaue object) : 값 저장용 객체 ( 저장된 값을 읽는 용도로 사용
// -> 비슷한 단어로 DTO(data transfer object) : 데이터를 교환하는 용도의 객체(값 읽기, 쓰기)

public class Emp { // db에서 조회된 사원 한명(1행)의 정보를 저장하는 객체
	
	private String empName;
	private String deptTitle;
	private int salary;
	
	//기본 생성자
	public Emp() {}

	//매개변수 생성자
	public Emp(String empName, String deptTitle, int salary) {
		super();
		this.empName = empName;
		this.deptTitle = deptTitle;
		this.salary = salary;
	}



	// getter setter 
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDeptTitle() {
		return deptTitle;
	}

	public void setDeptTitle(String deptTitle) {
		this.deptTitle = deptTitle;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	@Override
	public String toString() {
		return "이름 : " + empName + "/ 부서명 : " + deptTitle + "/ 급여 : " + salary;
	}
	

}
