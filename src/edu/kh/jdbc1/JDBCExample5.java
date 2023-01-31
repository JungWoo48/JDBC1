package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Employee;
import edu.kh.jdbc1.model.vo.Employee1;

public class JDBCExample5 {
	public static void main(String[] args) {
		
		
		// 입사일을 입력("2022_09_06) 받아
		// 입력 받은 값보다 먼저 입사한 사람의
		// 이름 입사일 성별(m,f) 조회
		
		Scanner sc = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			System.out.print("입사일 : ");
			String inputhireDate = sc.next();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kh";
			String pw = "kh1234";
			
			conn = DriverManager.getConnection(url, user, pw);
			
			String sql = "SELECT EMP_NAME, TO_CHAR(HIRE_DATE, 'YYYY\"년\"MM\"월\"DD\"일\"') AS 입사일, DECODE(SUBSTR(EMP_NO, 8, 1), '1', 'M', '2', 'F') AS GENDER"
						+" FROM EMPLOYEE"
						+" WHERE HIRE_DATE  < TO_DATE('" + inputhireDate + "')";
			
			//문자열 내부에 쌍따옴표 작성시 \로 작성해야 한다
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);		
			
			List<Employee1> list = new ArrayList();
			
			while(rs.next()) {
				String empName = rs.getString("EMP_NAME");
				String hireDate = rs.getString("입사일");
				String gender = rs.getString("GENDER");
				
				list.add(new Employee1(empName, hireDate, gender));
				
				
				//emp.setGender( rs.getStrinf("GENDER").charAt(0);
				// -> char 자료형 매개변수 필요
				// java의 char : 문자1개
				// db의 char : 고정길이 문자열 == String
				// Strinf.cahrAt(index) 이용
				
				
			}
			
			if(list.isEmpty()) {
				System.out.println("잘못된 입력");
			}else {
				for(Employee1 emp : list) {
					System.out.println(emp);
				}
			}
				
			
			
			} catch(Exception e) {
			e.printStackTrace();
			
			} finally {
			
				try {
					if(rs !=null) rs.close();
					if(stmt !=null) stmt.close();
					if(conn !=null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
			
		
		
	}
}
