package javaweb.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javaweb.utils.Hash;

/**
 GET /hash/compare 得到 hash_compare.jsp
 +----------+---------+
 | username |         |  <- input
 +----------+---------+
 | password |         |  <- input
 +----------+---------+
 |  Reset   |  Submit |  <- button 
 +----------+---------+
 
 按下 Submit button
 POST /hash/compare 
 進行資料比對
  
 * */
@WebServlet("/hash/compare")
public class HashCompareServlet extends HttpServlet {
	private static Map<String, String> users=new ConcurrentHashMap<>();
	static {
		users.put("john","A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ=");
		users.put("mary","+GOLl5svT3k92229GX4O4lp6bqMrCuIvXjxdEZ2DnnU=");	
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/view/hash_compare.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		
		//1.確認使用者
		String hashPasword=users.get(username);
		if(hashPasword==null) {
			resp.getWriter().println("user not found");
			return;
		}
		if(Hash.getHash(password).equals(hashPasword)) {
			resp.getWriter().println("Login OK");
			return;
		}
		resp.getWriter().println("Invalid pasword");
	}
	
	
	
}