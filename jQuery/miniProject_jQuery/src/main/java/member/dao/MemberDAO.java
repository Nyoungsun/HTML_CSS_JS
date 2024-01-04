package member.dao;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.type.Alias;

import member.bean.MemberDTO;

public class MemberDAO {
	private SqlSessionFactory sqlSessionFactory;
	
	public static MemberDAO memberDAO = new MemberDAO();
	
	public static MemberDAO getInstance() {
		return memberDAO;
	}
	
	public MemberDAO() {
		try {
			InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	//회원가입
	public int memberWrite(MemberDTO memberDTO) {
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		int count = sqlSession.insert("memberSQL.memberWrite", memberDTO); 
		sqlSession.commit();
		sqlSession.close();
		
		return count;
	}
	
	//로그인, 중복확인
	public MemberDTO isExist(String id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		MemberDTO memberDTO = sqlSession.selectOne("memberSQL.isExist", id);
		sqlSession.close();
		
		return memberDTO;
	}
	
	//회원정보수정
	public int memberUpdate(MemberDTO memberDTO) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int count = sqlSession.update("memberSQL.memberUpdate", memberDTO);
		sqlSession.commit();
		sqlSession.close();
		
		return count;
	}

	//회원탈퇴
	public int memberDelete(String id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int count = sqlSession.delete("memberSQL.memberDelete", id);
		sqlSession.commit();
		sqlSession.close();
		
		return count;
	}
}
	/*
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private DataSource dataSource;

	private static MemberDAO memberDAO = new MemberDAO();

	public static MemberDAO getInstance() {
		return memberDAO;
	}

	public MemberDAO() { // driver
		try {
			Context context = new InitialContext(); // 인터페이스이므로 직접 접근 불가
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/oracle"); //서버가 톰캣일 경우 'java:comp/env/' 필수
		} catch (NamingException e) {
			e.printStackTrace();
		} 
	}

	private static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultset) {
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			} // !=null, 제대로 실행되었으면
			if (connection != null) {
				connection.close();
			}
			if (resultset != null) {
				resultset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void close(Connection connection, PreparedStatement preparedStatement) {
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			} // !=null, 제대로 실행되었으면
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public int memberWrite(MemberDTO memberDTO) {
		int count = 0;

		String sql = "insert into member values(?, ?, ?, ? ,? ,? ,? ,? ,? ,? ,? ,?, sysdate) ";
		try {
			connection = dataSource.getConnection();
			
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, memberDTO.getName());
			preparedStatement.setString(2, memberDTO.getId());
			preparedStatement.setString(3, memberDTO.getPwd());
			preparedStatement.setString(4, memberDTO.getGender());
			preparedStatement.setString(5, memberDTO.getEmail1());
			preparedStatement.setString(6, memberDTO.getEmail2());
			preparedStatement.setString(7, memberDTO.getTel1());
			preparedStatement.setString(8, memberDTO.getTel2());
			preparedStatement.setString(9, memberDTO.getTel3());
			preparedStatement.setString(10, memberDTO.getZipcode());
			preparedStatement.setString(11, memberDTO.getAddr1());
			preparedStatement.setString(12, memberDTO.getAddr2());

			count = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MemberDAO.close(connection, preparedStatement);
		}
		return count;
	}

	public MemberDTO memberRead(String id) {

		MemberDTO dto = null;

		try {
			connection = dataSource.getConnection();
			
			String sql = "select * from member where id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				dto = new MemberDTO();
				dto.setId(resultSet.getString("id"));
				dto.setPw(resultSet.getString("pwd"));
				dto.setName(resultSet.getString("name"));
				dto.setGender(resultSet.getString("gender"));
				dto.setEmail1(resultSet.getString("email1"));
				dto.setEmail2(resultSet.getString("email2"));
				dto.setPost(resultSet.getString("zipcode"));
				dto.setAddr1(resultSet.getString("addr1"));
				dto.setAddr2(resultSet.getString("addr2"));
				dto.setTel1(resultSet.getString("tel1"));
				dto.setTel2(resultSet.getString("tel2"));
				dto.setTel3(resultSet.getString("tel3"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MemberDAO.close(connection, preparedStatement, resultSet);
		}
		return dto;
	}

	public int memberUpdate(MemberDTO memberDTO) {

		int count = 0;

		String sql = "update member set "
				+ "name=?, pwd=?, gender=?, email1=?, email2=?, tel1=?, tel2=?, tel3=?, zipcode=?, addr1=?, addr2=?, logtime=sysdate "
				+ "where id = ?";
		try {
			connection = dataSource.getConnection();
			
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, memberDTO.getName());
			preparedStatement.setString(2, memberDTO.getPw());
			preparedStatement.setString(3, memberDTO.getGender());
			preparedStatement.setString(4, memberDTO.getEmail1());
			preparedStatement.setString(5, memberDTO.getEmail2());
			preparedStatement.setString(6, memberDTO.getTel1());
			preparedStatement.setString(7, memberDTO.getTel2());
			preparedStatement.setString(8, memberDTO.getTel3());
			preparedStatement.setString(9, memberDTO.getPost());
			preparedStatement.setString(10, memberDTO.getAddr1());
			preparedStatement.setString(11, memberDTO.getAddr2());

			preparedStatement.setString(12, memberDTO.getId());

			count = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MemberDAO.close(connection, preparedStatement);
		}
		return count;
	}

	public int memberDelete(String id, String pw) {

		int count = 0;

		String sql = "delete from member where id=? pw=?";
		try {
			connection = dataSource.getConnection();
			
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, id);
			preparedStatement.setString(2, pw);

			count = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MemberDAO.close(connection, preparedStatement);
		}
		return count;
	}

	public boolean isexist(String id) {

		boolean isexist = false;

		String sql = "select * from member where id = ?";
		try {
			connection = dataSource.getConnection();
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				isexist = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MemberDAO.close(connection, preparedStatement, resultSet);
		}
		return isexist;
	}
	*/

