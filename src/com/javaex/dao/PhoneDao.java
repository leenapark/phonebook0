package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.PersonVo;

public class PhoneDao {

	// 필드
	// 0. import java.sql.*;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int count = 0;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "phonedb";
	private String pw = "phonedb";

	// 생성자
	// 메소드 g/s
	// 메소드 일반

	// DB 정리
	public void getconnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 자원 정리
	public void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// ********리스트*********
	public List<PersonVo> getList() {
		List<PersonVo> allList = new ArrayList<PersonVo>();

		getconnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			/*
			 * select phone_id, user_name, user_ph, user_company from phonedb;
			 */
			String query = "";
			query += "select phone_id, \n";
			query += "	name, \n";
			query += "	hp, \n";
			query += "	company \n";
			query += " from person";

			// System.out.println(query);

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int phoneId = rs.getInt("phone_id");
				String userName = rs.getString("name");
				String userHp = rs.getString("hp");
				String userCompany = rs.getString("company");

				PersonVo vo = new PersonVo(phoneId, userName, userHp, userCompany);
				allList.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return allList;
	}

	// ********등록**********
	public int phoneInsert(PersonVo personVo) {

		getconnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			/*
			 * insert into person values (seq_phone_id.nextval, '박리나', '010-1234-5678',
			 * '02-1234-5678');
			 */

			String query = "";
			query += "insert into person \n";
			query += " values (seq_phone_id.nextval, ?, ?, ?)";

			// 쿼리문 만들기
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());

			System.out.println(query);

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("[dao]" + count + "건 저장");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;

	}

	// **********수정**********
	public int getUpdate(PersonVo personVo) {

		getconnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			/*
			 * update person set hp = ?, company = ? where phone_id = ?;
			 */
			String query = "";
			query += "update person ";
			query += "set 	 name = ?, ";
			query += "		 hp = ?, ";
			query += "		 company = ? ";
			query += "		 where phone_id = ?";

			// 쿼리문 테스트
			// System.out.println(query);

			// 쿼리문 만들기
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());
			pstmt.setInt(4, personVo.getPersonId());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 수정되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;
	}

	// *********삭제**************
	public int getDelete(int num) {

		getconnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " delete person ";
			query += " where phone_id = ? ";

			// System.out.println(query);

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, num);

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 삭제 되었습니다.");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return count;
	}

	// **********검색************
	public List<PersonVo> getSearch(String search) {
		List<PersonVo> serch = new ArrayList<PersonVo>();

		getconnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			/*
			 * select name, hp, company from person where name like '%유%' or hp like '%3%'
			 * or company like '%123%';
			 */
			String query = "";
			query += "select phone_id, ";
			query += "	     name, ";
			query += "	 	 hp, ";
			query += "		 company ";
			query += " from person ";
			query += " where name like ? ";
			query += " and(or hp like ? ";
			query += " or company like ?)";

			// 쿼리문 확인
			// System.out.println(query);

			// 쿼리문 만들기
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + search + "%");
			pstmt.setString(2, "%" + search + "%");
			pstmt.setString(3, "%" + search + "%");

			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int bookId = rs.getInt("phone_id");
				String userName = rs.getString("name");
				String userHp = rs.getString("hp");
				String userCompany = rs.getString("company");

				PersonVo phoneVo = new PersonVo(bookId, userName, userHp, userCompany);
				serch.add(phoneVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return serch;
	}

	
	//************해당하는 정보 가져오기***********
	public PersonVo getPerson(int personId) {
		PersonVo personVo = null;

		getconnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select phone_id, ";
			query += "	      name, ";
			query += "	      hp, ";
			query += "	      company ";
			query += " from person ";
			query += " where phone_id = ? ";

			// System.out.println(query);

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, personId);
			
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int personID = rs.getInt("phone_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				personVo = new PersonVo(personID, name, hp, company);
				
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return personVo;
	}
}
