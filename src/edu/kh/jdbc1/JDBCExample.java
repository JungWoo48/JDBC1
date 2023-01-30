package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample {
	public static void main(String[] args) {
		
		// JDBC : java에서 db에 연결(접근) 할 수 있게 해주는 java programmin api
		//														(java에 기본 내장된 인터페이스)
		// java.sql 패키지에서 제공함
		
		// * JDBC를 이용한 애플리켕션을 만들때 필요한것
		// 1. Java의 JDBC관련 인터페이스- 이미 자바에서 준비됨
		// 2. DBMS(oracle)
		// 3. Oracle에서 Java 애플리케션과 연결할떄 사용할 
		//		JDBD를 상속받아 구현한 클래스 모음(ojdbc11.jar 라이브러라)
		//		-> OracleDriver.class (JDBC 드라이버) 이용
		
		// 사용법
		// 1단계 : JDBC 객체 참조 변수 선언 (java.sql 패키지의 인터페이스)
		
		Connection conn = null;
		// DB연결 정보를 담은 객체
		// -> DBMS 타입, 이름, IP, Port, 계정명, 비밀번호 저장- 연결정보
		// -> DBeaver계정 접속방법과 유사함
		// * DB와 Java사이를 연결해주는 통로(Stream과 유사함)
		
		
		Statement stmt = null;
		// Connection을 통해
		// SQL 문을 DB에 전달하여 실행하고
		// 생성된 결과(ResultSet, 성공한 행의 개수)를 Java한테 반환 하는데 사용되는 객체
		
		
		ResultSet rs = null;
		// Select 질의 성공시 반환되는데
		// 조회 결과 집합을 나타내는 객체
		
		
		try {
			// 2단계 : 참조 변수에 알맞은 객체 대입
			
			// 1. DB 연결에 필요한 Oracle JDBC Driver 메모리에 로드하기
			// -> 객체로 만들어 두기
			
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 예외 발생 가능
			//				("패키지명 + 클래스명")
			// -> () 안에 작성된 클래스의 객체를 반환
			// -> 메모리에 객체가 생성되고, JDBC 필요시 알아서 참조해서 사용
			// --> 생략해도 자동으로 메모리 로드가 진행됨( 명식적으로 작성을 권장)
			
			// 2. 연결 정보를 담은 Connection을 생성
			// -> DriverManager 객체를 이용해서 Connection 객체를 만들어 얻어옴
			String type = "jdbc:oracle:thin:@"; // JDBC 드라이버 종류
			
			String ip = "localhost";// DB 서버 컴퓨터 IP localhost == 127.0.0.1(loop back ip)
							// localhost가 안될경우 ipconfig를 쳐서 IPv4주소 입력
			
			String port = ":1521"; // 포트번호 기본값 1521
			
			String sid = ":XE"; // DB이름
			
			String user = "kh"; // user이름
			
			String pw = "kh1234"; // user 계정 비밀번호
			
			// DriverManager : 메모리에 로드된 JDBC 드라이버를 이용해서
			// 					Connection 객체를 만드는 역할
			
			conn = DriverManager.getConnection(type + ip + port + sid, user, pw);
			// type + ip + port + sid : url
			
			//중간 확인 
			System.out.println(conn);
			
			
			// 3. SQL 작성
			// * Java에서 작성되는 SQL은 마지막에 ;찍지 않기
			String sql = "SELECT EMP_ID, EMP_NAME, SALARY, HIRE_DATE FROM EMPLOYEE";
			
			
			// 4. Statement 객체 생성 - Statemanet는 java와 db를 오고가는 셔틀버스 sql을 태워서 
			//							갖고가서 resultset을 받아온다 
			// 							connection이라는 길을 통해서 오고간다
			// -> Connecdtion 객체를 통헤서 생성
			stmt = conn.createStatement();
			
			
			// 5. 생성된 Statement 객체에 sql을 적재하여 실행한 후 
			// 결과를 반환 받아와서 rs변수에 저장
			rs = stmt.executeQuery(sql); // -.executeQuery(sql); sql을 가져가서 반환값을 받기위해 질의중인 구문
			//select문 수행 메서드, resultset반환 
			
			
			
			
			// 3단계 : SQL을 수핼해서 반환 받은 결과(ResultSet)를 한행씩 접근해서 컬럼값 얻어오기
			while(rs.next()) {
				//rs.next() : rs가 참조하는 resultset 객체의 첫번쨰 컬럼부터 순서대로 한행씩 이동하며
				//				다음행이 있을경우 true/ 없으면 false 반환
				
				
				//rs.get자료형("컬럼명")
				String empId = rs.getString("EMP_ID");
				// 현재 행의 "EMP_ID" 문자열 컬럼의 값을 얻어옴
				
				String empName = rs.getString("EMP_NAME");
				// 현재 행의 "EMP_NAME" 문자열의 컬럼값을 얻어옴
				
				int salary = rs.getInt("salary");
				// 현재 행의 "SALARY" 숫자 컬럼값을 얻어옴
				
				Date hireDate = rs.getDate("HIRE_DATE");
				// java.sql.Date , java.util.Date 둘다 가능
				
				
				//조회 결과 출력
				System.out.printf("사번 : %s / 이름 : %s / 급여 : %d / 입사일 : %s\n",
									empId, empName, salary, hireDate.toString() );
				// java.util.Date의 toSting은 yyyy-mm-dd형식으로 오버라이딩 되어있다
			}
			
			
			
		} catch(ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 경로가 잘못작성됨");
			
		} catch(SQLException e)	{
			// SQLException : DB관련 최상위 예외
			e.printStackTrace();
			
		} finally {
			// 4단계 : 사용한 JDBC 객체 자원 반환( close() ) 
			// ResultSet, Statement, Connection 닫기 (생성 역순으로 닫는것을 권장)
			try {
				if( rs != null) rs.close(); //if( rs != null) : null이 아닐경우에만 수행한다
				if( stmt != null) stmt.close();
				if( conn != null) conn.close();
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
		
			
			
			
		}

		
		
	}
}
