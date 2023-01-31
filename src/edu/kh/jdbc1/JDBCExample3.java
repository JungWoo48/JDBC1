package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Emp;

public class JDBCExample3 {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		// 부서명을 입력받아 같은 부서에 있는 사원의
		// 사원명 부서명 급여조회
		
		// 객체 참조변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try { 
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String type = "jdbc:oracle:thin:@";
			String ip = "localhost";
			String port = ":1521";
			String sid = ":XE";
			String user = "kh";
			String pw = "kh1234";
			
			conn = DriverManager.getConnection(type + ip + port+ sid, user, pw);
			
			System.out.println("부서명 입력 : ");
			String input = sc.nextLine();
			
			
			String sql = "SELECT EMP_NAME, NVL(DEPT_TITLE, '부서없음') AS DEPT_TITLE, SALARY"
							+ " FROM EMPLOYEE"
							+ " JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)"
							+ " WHERE NVL(DEPT_TITLE, '부서없음') = '" + input + "'"; // - input 양옆에 db상에서 쓰는 홑따옴표가 필요
																						// 리터럴 표기법
			//*****(중요)*****
			//java에서 작성되는 sql에
			//문자열(String) 변수를 추가(이어쓰기) 할경우
			//'' (db문자열 리터럴)이 누락되지 않게 쓴다
			
			// '' 미작성시 String값이 컬럼명으로 인식되어
			// 부적합한 식별자 오류가 발생
			
			
			stmt = conn.createStatement(); //statement 객체생성
			
			rs = stmt.executeQuery(sql); // statement 객체로 sql전달후 값 반환
			
			
			//조회 결과(rs)를 List에 옮겨담기
			List<Emp> list = new ArrayList<>();
			
			while(rs.next()) { //다음 행으로 이동해서 행에 데이터가 있으면 truer, 다음으로 이행
				
				// 현재 행에 존재하는 컬럼ㄱ밧 얻어오기
				String empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				int salary = rs.getInt("SALARY");
				
				//Emp 객체를 생성하여 컬럼값 담기
				Emp emp = new Emp(empName, deptTitle, salary);
			
				
				//생성된 Emp객체를 List추가
				list.add(emp);
				
				
			}
			// List에 추가된 emp 객체가 없다면 "결과없ㄱ음"
			//있다면 순차넉 출력
			
			if(list.isEmpty()) { //list가 비어있을 경우
				System.out.println("결과없음");
				
			} else {
				
				//향상된 for문으로 출력
				for(Emp emp : list) {
					System.out.println(emp);
				}
				
			}
			
	
			
		} catch(Exception e) {
			e.printStackTrace();
			
			
		} finally {
			try {
				if(rs !=null)rs.close();
				if(stmt !=null)stmt.close();
				if(conn !=null)conn.close();
				
			} catch(SQLException e) {
				
			}
			
			
			
		}
		
	}

}
