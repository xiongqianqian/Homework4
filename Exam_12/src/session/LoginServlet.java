package session;

import java.io.IOException;  
import java.io.PrintWriter;  
import java.util.List;  
  
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;

import bean.Db;
import bean.User;  

@WebServlet("/Login")
public class LoginServlet extends HttpServlet  {
	 public void doGet(HttpServletRequest request, HttpServletResponse response)  
	            throws ServletException, IOException {  
	
	        response.setCharacterEncoding("utf-8");  
	        response.setContentType("text/html;charset=utf-8");  
	        PrintWriter out = response.getWriter();  
	          
	        String username = request.getParameter("username");  
	        String password = request.getParameter("password");  
	          
	        List<User> list = Db.getAllUser();  
	        for(User user : list){  
	            if(user.getUsername().equals(username)&&user.getPassword().equals(password)){  
	                //��½�ɹ�����user����session�������  
	                request.getSession().setAttribute("user", user);  
	                //�ض���index.jsp  
	                response.sendRedirect("index.html");  
	                return;  
	            }  
	        }  
	        //��½ʧ��  
	        out.print("��¼ʧ�ܣ��û������������");  
	          
	    }  
	  
	    public void doPost(HttpServletRequest request, HttpServletResponse response)  
	            throws ServletException, IOException {  
	        doGet(request, response);  
	    }  
}
