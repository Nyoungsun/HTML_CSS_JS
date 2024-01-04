package board.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import board.bean.BoardDTO;
import member.dao.MemberDAO;
 
public class BoardDAO {
	private SqlSessionFactory sqlSessionFactory;
	
	public static BoardDAO boardDAO = new BoardDAO();
	
	public static BoardDAO getInstance() {
		return boardDAO;
	}
	
	public BoardDAO() {
		InputStream inputStream; 
		
		try {
			inputStream = Resources.getResourceAsStream("mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public int boardWrite(Map<String, Object> map) {
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		int count = sqlSession.insert("boardSQL.boardWrite", map); 
		sqlSession.commit();
		sqlSession.close();
		
		return count;
	}
	
	public List<BoardDTO> boardList(Map<String, Object> map) {
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		List<BoardDTO> list = sqlSession.selectList("boardSQL.boardList", map);
		sqlSession.close();
			
		return list;
	}
	
	public BoardDTO boardView(int seq) {
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		BoardDTO boardDTO = sqlSession.selectOne("boardSQL.boardView", seq);
		sqlSession.close();
		
		return boardDTO;
	}
	
	public int totalArticle() {
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		int totalArticle = sqlSession.selectOne("boardSQL.totalArticle");
		sqlSession.close();
		
		return totalArticle;
	}
	
	// 답글 데이터는 map에 존재
	public void boardReply(Map<String, Object> map) {
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		
		// 1. 원글 가져오기 → 원글 데이터는 boardDTO에 존재
		BoardDTO boardDTO = sqlSession.selectOne("boardSQL.boardView", map.get("pseq"));
		
		// 2. step 밀기: update board set step = step + 1 where ref = 원글 ref and step > 원글 step
		sqlSession.update("boardSQL.boardReply", boardDTO); 
		 
		// 3. map에 원글 데이터 삽입
		// 답글 ref = 원글 ref
		// 답글 lev = 원글 lev + 1
		// 답글 step = 원글 step + 1
		map.put("ref", boardDTO.getRef());
		map.put("lev", boardDTO.getLev() + 1);
		map.put("step", boardDTO.getStep() + 1);
		
		// 4. 답글 insert
		sqlSession.insert("boardSQL.boardReply2", map); 
		
		// 5. 원글 reply 증가: update board set reply = reply + 1 where seq = 원글 seq
		sqlSession.update("boardSQL.boardReply3", boardDTO.getSeq());
		
		sqlSession.commit();
		sqlSession.close();
	}
}
	/*
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private DataSource dataSource;
	
	// Context.xml에서 Connection Pool 설정했으므로 
    // 직접 JDBC 연동 X
	
	private static BoardDAO boardDAO = new BoardDAO();

	public static BoardDAO getInstance() {
		return boardDAO;
	}

	public BoardDAO() { 
		try {
			Context context = new InitialContext(); // 인터페이스이므로 직접 접근 불가
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/oracle"); //서버가 톰캣일 경우 'java:comp/env/' 필수
			// Object를 반환하므로 캐스팅 필요
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

	public int boardWrite(Map<String, String> map) {

		int count = 0;

		String sql = "insert into board(seq, id, name, email, subject, content, ref)"
				+ "values(seq_board.nextval, ?, ?, ?, ? ,? ,seq_board.currval)";
		try {
			connection = dataSource.getConnection();
			
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, map.get("name"));
			preparedStatement.setString(2, map.get("id"));
			preparedStatement.setString(3, map.get("email"));
			preparedStatement.setString(4, map.get("subject"));
			preparedStatement.setString(5, map.get("content"));

			count = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BoardDAO.close(connection, preparedStatement);
		}
		return count;
	}
	
	public ArrayList<BoardDTO> boardList(Map<String, Integer> map) {
		

		ArrayList<BoardDTO> list = new ArrayList<>();
		String sql = "select * from "
					+ "(select rownum rn, A.* "
					+ "from (select * from board order by ref desc, step asc) A) "
					+ "where rn between ? and ?";
		try {
			connection = dataSource.getConnection();
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, map.get("start"));
			preparedStatement.setInt(2, map.get("end"));
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				BoardDTO boardDTO = new BoardDTO();
				boardDTO.setSeq(resultSet.getInt("seq"));
				boardDTO.setId(resultSet.getString("id"));
				boardDTO.setName(resultSet.getString("name"));
				boardDTO.setEmail(resultSet.getString("email"));
				boardDTO.setSubject(resultSet.getString("subject"));
				boardDTO.setContent(resultSet.getString("content"));
				
				boardDTO.setRef(resultSet.getString("ref"));
				boardDTO.setLev(resultSet.getString("lev"));
				boardDTO.setStep(resultSet.getString("step"));
				boardDTO.setPseq(resultSet.getString("pseq"));
				boardDTO.setReply(resultSet.getString("reply"));
				
				boardDTO.setHit(resultSet.getString("hit"));
				boardDTO.setLogtime(resultSet.getDate("logtime"));
				list.add(boardDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			list = null;
		} finally {
			BoardDAO.close(connection, preparedStatement, resultSet);
		}
		return list;
	}
	
	public BoardDTO boardList(int seq) {
		
		BoardDTO dto = null;

		try {
			connection = dataSource.getConnection();
			
			String sql = "select * from board where seq = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, seq);
			
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				dto = new BoardDTO();
				dto.setSubject(resultSet.getString("subject"));
				dto.setContent(resultSet.getString("content"));
				dto.setId(resultSet.getString("id"));
				dto.setHit(resultSet.getString("hit"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BoardDAO.close(connection, preparedStatement, resultSet);
		}
		return dto;
	}
	
	public int totalArticle() {

		int totalArticle = 0;
		String sql = "select count(*)total from board";

		try {
			connection = dataSource.getConnection();
			
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
			resultSet.next();
			totalArticle = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BoardDAO.close(connection, preparedStatement, resultSet);
		}
		return totalArticle;
	}
	*/
