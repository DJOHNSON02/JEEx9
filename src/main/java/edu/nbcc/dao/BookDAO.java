/**
 * 
 */
package edu.nbcc.dao;

import java.util.List;

import edu.nbcc.model.Book;

/**
 * @author Dylan
 *
 */
public interface BookDAO {
	/**
	 * Delete
	 * @param d
	 * @return
	 */
	public int delete(int d);
	
	/**
	 * Insert a book
	 * @param book
	 * @return
	 */
	public int insert(Book book);
	
	/**
	 * Update
	 * @param book
	 * @return
	 */
	public int update(Book book);
	
	/**
	 * Find all Books
	 * @return
	 */
	public List<Book> findAll();
	
	/**
	 * Find book by name
	 * @param name
	 * @return
	 */
	public Book findByName(String name);
	
	/**
	 * Find Book by id
	 * @param id
	 * @return
	 */
	public Book findById(Int id);
}
