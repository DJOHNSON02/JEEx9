/**
 * 
 */
package edu.nbcc.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nbcc.dao.BookDAO;
import edu.nbcc.dao.BookDAOlmpl;
import edu.nbcc.model.Book;
import edu.nbcc.model.BookModel;
import edu.nbcc.model.ErrorModel;
import edu.nbcc.util.ValidationUtil;
import jakarta.servlet.RequestDispatcher;

/**
 * @author Dylan
 *
 */
public class BookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String BOOK_VIEW = "/books.jsp";
	private static final String CREATE_VIEW = "/book.jsp";
	private static final String BOOK_SUMMARY = "/bookSummary.jsp";

	private RequestDispatcher view;

	public RequestDispatcher getView() {
		return view;
	}

	public void setView(HttpServletRequest request, String viewPath) {
		view = request.getRequestDispatcher(viewPath);
	}

	public BookController() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Book book = new Book();
		String path = request.getPathInfo();
		BookDAO dao = new BookDAOlmpl();
		if (path == null) {
			List<Book> list = dao.findAll();
			request.setAttribute("vm", list);
			setView(request, BOOK_VIEW);
		} else {
			String[] paths = path.split("/");
			if (paths[1].equalsIgnoreCase("create") || ValidationUtil.isNumeric(paths[1])) {
				setView(request, CREATE_VIEW);
				int id = ValidationUtil.getInteger(paths[1]);
				if (id != 0) {
					Book bk = dao.findById(id);
					if (bk != null) {
						BookModel vm = new BookModel();
						vm.setBook(bk);
						request.setAttribute("vm", vm);
					} else {
						request.setAttribute("error", new ErrorModel("Book not found"));
					}
				} else {
					request.setAttribute("vm", new BookModel());
				}

			}
		}
		getView().forward(request, response);

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		BookDAO dao = new BookDAOlmpl();
		List<String> errors = new ArrayList<String>();
		Book book = new Book();
		
		int id = 0;
		double price = 0;
		int term = 0;
		
		setView(request, BOOK_SUMMARY);

		try {
			if (request.getParameter("bookPrice") == null) {
				errors.add("Book price is null");
			}

			if (request.getParameter("bookName") == null) {
				errors.add("Book name is null");
			}

			if (request.getParameter("bookTerm") == null) {
				errors.add("Book term is null");
			}

			if (ValidationUtil.isNumeric(request.getParameter("hdnBookId"))) {
				id = ValidationUtil.getInteger(request.getParameter("hdnBookId"));
			} else {
				errors.add("Invalid Id");
			}

			String name = request.getParameter("bookName");
			if (name == null || name.equals("")) {
				errors.add("Name field cannot be null or blank");
			}

			if (ValidationUtil.isNumeric(request.getParameter("bookPrice"))) {
				price = ValidationUtil.getDouble(request.getParameter("bookPrice"));

				if (price == 0.0) {
					errors.add("Price cannot be 0");
				}
			} else {
				errors.add("Invalid Price");
			}

			if (request.getParameter("bookTerm") != null) {
				if (ValidationUtil.isNumeric(request.getParameter("bookTerm"))) {
					term = ValidationUtil.getInteger(request.getParameter("bookTerm"));
				} else {
					errors.add("Invalid term");
				}
			} else {
				errors.add("Please select a term");
			}

			if (errors.size() == 0) {
				book.setId(id);
				book.setName(name);
				book.setPrice(price);
				book.setTerm(term);

				String action = request.getParameter("action");

				switch (action) {
				case "Create":
					book = dao.insert(book);
					request.setAttribute("createdBook", book);
					break;
				case "Save":
					int saveId = dao.update(book);
					if (saveId > 0) {
						request.setAttribute("savedBook", book);
					} else {
						BookModel vm = new BookModel();
						vm.setBook(book);
						request.setAttribute("vm", vm);
						request.setAttribute("error", new ErrorModel("Book does not exist"));
						setView(request, CREATE_VIEW);
					}

					break;
				case "Delete":
					int rowsAffected = dao.delete(book.getId());
					if (rowsAffected > 0) {
						request.setAttribute("deletedBook", book);
					} else {
						
					}
				}
			} else {
				BookModel vm = new BookModel();
				vm.setBook(book);
				setView(request, CREATE_VIEW);
				ErrorModel errorModel = new ErrorModel();
				errorModel.setErrors(errors);
				request.setAttribute("error", errorModel);
				request.setAttribute("vm", vm);
			}

		} catch (Exception e) {
			request.setAttribute("error", new ErrorModel("An error has occured"));
			setView(request, CREATE_VIEW);
		}
		getView().forward(request, response);
	}
}
