package session;

import java.io.IOException;  

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {  
	  
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        //false代表：不创建session对象，只是从request中获取。  
        HttpSession session = request.getSession(false);  
        if(session==null){  
            return;  
        }  
        session.removeAttribute("user");  
        //从定向到index.jsp  
        response.sendRedirect("login.html");  
    }  
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        doGet(request, response);  
    }  
}  