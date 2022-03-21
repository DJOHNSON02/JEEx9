/**
 * 
 */
package edu.nbcc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.nbcc.model.Book;

/**
 * @author Dylan
 *
 */
public class BookDAOlmpl implements BookDAO {
	
	private static final DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static final DB_URL = "jdbc:mysql://sql5.freemysqlhosting.net:3306/javaeeex9";
	private static final USER_ID = "root";
	private static final PASSWORD = "Kingjohny0127";
	
	private static final String DELETE = "Delete from javaeeex9.book where id =? ";
	private static final String INSERT = "INSERT INTO javaeeex9.book(name, term, price) values (?, ?, ?)";
	private static final String UPDATE = "UPDATE javaeeex9.book set name = ?, price = ?, term = ? where id = ?";
	private static final String FIND_ALL = "SELECT * FROM javaeeex9.book order by id";
	private static final String FIND_BY_ID = "SELECT * FROM javaeeex9.book where id = ?";
	private static final String FIND_BY_NAME = "SELECT * FROM javaeeex9.book where name = ?";

	@Override
	public int delete(int d) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Book book) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Book book) {
		
	}

	@Override
	public List<Book> findAll() {
		Connection conn = null;
		PreparedStatement stmt = null;
		List<Book> list = new ArrayList<Book>();
		
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(FIND_ALL);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Book book  = new Book();
				
				book.setId(rs.getInt("id"));
	            book.setName(rs.getString("name"));
	            book.setPrice(rs.getDouble("price"));
	            book.setTerm(rs.getInt("term"));
	            
	            list.add(book);
	            return list;
			}
			
		} catch(SQLException ex) {
			System.out.println(ex.getMessage());
			return null;
		} finally {
			close(stmt);
			close(conn);
		}
	}

	@Override
	public Book findByName(String name) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(FIND_BY_NAME);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			Book book  = new Book();
			
			if (rs.next()) {				
				book.setId(rs.getInt("id"));
	            book.setName(rs.getString("name"));
	            book.setPrice(rs.getDouble("price"));
	            book.setTerm(rs.getInt("term"));	            
			}
			return book;
		} catch(SQLException ex) {
			System.out.println(ex.getMessage());
			return null;
		} finally {
			close(stmt);
			close(conn);
		}
	}
	
	@Override
	public Book findById(int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(FIND_BY_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			Book book  = new Book();
			
			if (rs.next()) {								
				book.setId(rs.getInt("id"));
	            book.setName(rs.getString("name"));
	            book.setPrice(rs.getDouble("price"));
	            book.setTerm(rs.getInt("term"));	            
			}
			return book;
		} catch(SQLException ex) {
			System.out.println(ex.getMessage());
			return null;
		} finally {
			close(stmt);
			close(conn);
		}
	}

}
