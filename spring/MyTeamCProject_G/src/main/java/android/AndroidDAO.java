package android;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import member.MemberVO;

public class AndroidDAO {
	//데이터베이스 연동
	DataSource db;
	
	public AndroidDAO() {
		try {
			Context context = new InitialContext();
			db = (DataSource) context.lookup("java:/comp/env/hanul");
		} catch (Exception e) {
			e.getMessage();
		}
		
	}
	
	//21.09.28 네이버 로그인 처리
			public MemberVO anNaverLogin(MemberVO vo) throws SQLException {
				Connection con = null;
				PreparedStatement ps = null;
				ResultSet rs = null;	
				
				try {
					con = db.getConnection();
					String sql = "SELECT * FROM MEMBER_C WHERE ID = ?";
					ps = con.prepareStatement(sql);
					ps.setString(1, vo.getId());
					rs = ps.executeQuery();
					
					System.out.println("vo.getId() : " + vo.getId());
					
					while(rs.next()) {
						vo.setEmail(rs.getString("email"));
						vo.setName(rs.getString("name"));
						vo.setPhone(rs.getString("phone"));
						vo.setNaver(rs.getString("naver"));
						
					}
				}catch (Exception e) {
					e.printStackTrace();
					e.getMessage();
				}finally {
					if(rs !=null) {
						rs.close();
					}else if(ps != null) {
						ps.close();
					}else if(con != null) {
						con.close();
					}
				}
				
				return vo;
			}
	
	// 네이버 아이디 DB 저장
		public String anNaverJoin(MemberVO vo) throws SQLException {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			int result = 0;
			String state = "";	
			
			try {
				con = db.getConnection();
				String sql = "INSERT INTO MEMBER_C (ID, EMAIL, NAME, PHONE, NAVER) VALUES (?, ?, ?, ?, ?)";
				
				ps = con.prepareStatement(sql);
				ps.setString(1, vo.getId());
				ps.setString(2, vo.getEmail());
				ps.setString(3, vo.getName());
				ps.setString(4, vo.getPhone());
				ps.setString(5, vo.getNaver());
				
				result = ps.executeUpdate();
				
				if(result > 0) {
					state = "true";	//삽입 성공함
				}else {
					state = "false";	//삽입 실패함
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				e.getMessage();
			}finally {
				if(rs !=null) {
					rs.close();
				}else if(ps != null) {
					ps.close();
				}else if(con != null) {
					con.close();
				}
			}

			return state;
		}
	
	//네이버 아이디 중복 검사
		public String anNaverIdChk(String naver_id) throws SQLException {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			String id = null;
			
			try {
				con = db.getConnection();
				String sql = "SELECT ID FROM MEMBER_C WHERE ID = ? AND NAVER = 'NAVER'";
				ps = con.prepareStatement(sql);
				ps.setString(1, naver_id);
				rs = ps.executeQuery();
				
				if(rs.next()) {	//결과 값이 있음
					id = rs.getString("id");
				}else {
					id = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				e.getMessage();
			}finally {
				if(rs !=null) {
					rs.close();
				}else if(ps != null) {
					ps.close();
				}else if(con != null) {
					con.close();
				}
			}
			
			System.out.println(id);
			return id;
		}
	
	// 카카오 아이디 중복 검사
	public String anKakaoIdChk(String kakao_id) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String id = null;
		
		try {
			con = db.getConnection();
			String sql = "SELECT ID FROM MEMBER_C WHERE ID = ? AND KAKAO = 'KAKAO'";
			ps = con.prepareStatement(sql);
			ps.setString(1, kakao_id);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				id = kakao_id;
			}else {
				id = "1";
			}
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}finally {
			if(rs !=null) {
				rs.close();
			}else if(ps != null) {
				ps.close();
			}else if(con != null) {
				con.close();
			}
		}
		
		
		return id;
	}


	//카카오 로그인
	public MemberVO anKakaoLogin(MemberVO vo) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		System.out.println("Kakao Login DAO :" + vo.getId());
		
		
		try {
			con = db.getConnection();
			String sql = "SELECT * FROM MEMBER_C WHERE ID = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getId());
			rs = ps.executeQuery();
			
			while(rs.next()) {
				vo.setName(rs.getString("name"));
				vo.setKakao(rs.getString("kakao"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			if(rs !=null) {
				rs.close();
			}else if(ps != null) {
				ps.close();
			}else if(con != null) {
				con.close();
			}
		}
	
		return vo;
	}
	
	//카카오 로그인 DB저장
	public String anKakaoJoin(MemberVO vo) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		
		System.out.println("kakao Join DAO : " + vo.getId());
		int result = 0;
		String state = "";
		
		try {
			con = db.getConnection();
			String sql = "INSERT INTO MEMBER_C (ID, NAME, KAKAO) VALUES (?, ?, ?)";
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1, vo.getId());
			ps.setString(2, vo.getName());
			ps.setString(3, vo.getKakao	());
			
			result = ps.executeUpdate();
			
			System.out.println("DAO result : " + result);
			
			if(result > 0) {
				state = "true";
				System.out.println("삽입 성공");
			}else {
				state = "false";
				System.out.println("실패");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.getMessage();
		}finally {
			if(rs !=null) {
				rs.close();
			}else if(ps != null) {
				ps.close();
			}else if(con != null) {
				con.close();
			}
		}
		
		return state;
		
	}

	//아이디 중복 검사
		public Integer anIdChk(String id) throws SQLException {
			//데이터베이스와 연동하여 원하는 결과를 얻는다
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;		
			
			System.out.println("anIdChk : " + id);
			
			int result = 0;
			
			try {
				con = db.getConnection();
				String sql = "SELECT * FROM MEMBER_C WHERE ID = '" + id + "'";
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				
				while(rs.next()) {
					result = 1;
				}
				
			} catch (Exception e) {
				e.getMessage();
				System.out.println(e.getMessage());
				System.out.println("anIdChk Exception");
			}finally {
				if(rs !=null) {
					rs.close();
				}else if(ps != null) {
					ps.close();
				}else if(con != null) {
					con.close();
				}
			}
			
			System.out.println(result);
			
			return result;
			
		}

}
