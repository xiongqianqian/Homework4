package servlet;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servlet.Post;
/**
 * Servlet implementation class userData
 */
@WebServlet("/userData")
public class userData extends HttpServlet {
	private static final long serialVersionUID = 1L;
String uploadFilePath;
	
	
	String fileName = "history";
	String subDirName = ".";
	String filePath;
	File filePathDir;
	FileWriter fileWriter;
	FileReader fileReader;
	private final int fileLimitSize=50*1024*1024;
    /**
     * @see HttpServlet#HttpServlet()
     */
public void init() {
		
		// Here we get the absolute path of the destination directory
		// uploadFilePath = this.getServletContext().getRealPath(UPLOAD_DIR) +
		// File.separator;
		String separator = System.getProperty("file.separator");
		
		//create a file to keep entries
		filePath = this.getServletContext().getRealPath(separator) + subDirName
				+ separator;
		filePathDir = new File(filePath);
		if (!filePathDir.exists()) {
			filePathDir.mkdir();
		}

		filePath += fileName;

		uploadFilePath = this.getServletContext().getRealPath(
				getServletConfig().getInitParameter("upload_path"))
				+ File.separator;

		// Here we create the destination directory under the project main
		// directory if it does not exists
		//mkdirs & mkdir: 
		File fileSaveDir = new File(uploadFilePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdirs();
		}

	}
    public userData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><head>");
        out.println("<title></title>");
        out.println("</head><body>");
        out.println("<h2>Add User Information</h2>");
        
        
        out.println("<form method='GET' + action='index.html'>");
      
        out.println("<table border='0'>"); 
        
        out.println("<tr><th>User Name</th><td>"); 
        out.println("<input type='text' name='name' size='20' required></td><tr>");
        
        out.println("<tr><th>Gender</th><td>"); 
        out.println("<input type='text' name='gender' size='20' required></td><tr>");
        
        out.println("<tr><th>birthday</th><td>"); 
        out.println("<input type='text' name='birthday' size='20' required></td><tr>");
        
        out.println("<tr><th>Phone number</th><td>"); 
        out.println("<input type='text' name='number' size='20' required></td><tr>");
        
out.println(filePath);
        
        out.println("<tr><td></td><td><input type='submit' name='Next' value='Submit'></td></tr>");
        out.println("</table>");
        out.println("</form>");
	
	  String name = request.getParameter("name");
	  String gender = request.getParameter("gender");
	  String birthday = request.getParameter("birthday");
	  String number = request.getParameter("number");

	  
	  
	RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/trip.html");

	if(checkParameter(name) && checkParameter(gender)&& checkParameter(number)) {

		  String dbUserName = "e1500951";
			String dbPassword = "wnNAZD3th9UM";
			String dbName = "e1500951_2018_test";
			String dbTableName = "exam1";
		  
			String query = "INSERT INTO " + dbTableName + " VALUES ('" + name + "', '" + gender + "', '"
					+ birthday + "', '" + number + "'"+"test,"+"test,"+"test,"+"test"+")";
			Connection conn = null;
			Statement stmt = null;
			ResultSet resultSet = null;
			ResultSetMetaData resultSetMeatData = null;
			try {
				// Here we load the database driver for Oracle database
				// Class.forName("oracle.jdbc.driver.OracleDriver");
				// For mySQL database the above code would look like this:
				Class.forName("com.mysql.jdbc.Driver");
				// Here we set the JDBC URL for the Oracle database
				// String url = "jdbc:oracle:thin:@db.cc.puv.fi:1521:ora817";
				// For mySQL database the above code would look like
				// something this.
				// Notice that here we are accessing mg_db database
				String url = "jdbc:mysql://mysql.cc.puv.fi:3306/" + dbName;
				// Here we create a connection to the database
				// conn = DriverManager.getConnection(url, "scott", "tiger");
				// For mg_db mySQL database the above code would look like
				// the following:
				conn = DriverManager.getConnection(url, dbUserName, dbPassword);
				// Herey we create the statement object for executing SQL commands.
				stmt = conn.createStatement();
				// Here we executethe SQL query and save the results to a ResultSet
				// object
				stmt.executeUpdate(query);

				resultSetMeatData = resultSet.getMetaData();

				// Here we calculate the number of columns
				int columns = resultSetMeatData.getColumnCount();



			} catch (Exception e) {
				out.println("<p>" + e.getMessage());
			} finally {
				try {
					// Here we close all open streams
					if (stmt != null)
						stmt.close();
					if (conn != null)
						conn.close();
				} catch (SQLException sqlexc) {
					out.println("EXception occurred while closing streams!");
				}
			}
		  
    	   out.println("<hr>");
    	   StringBuilder feedback=new StringBuilder();
    	   name = request.getParameter("name");
 		  gender = request.getParameter("gender");
 		  birthday = request.getParameter("birthday");
 		   number = request.getParameter("number");
 		  feedback.append( "<tr><td>"+name+"</td><td>"+gender+"</td><td>"+birthday+"</td><td>"+number+"</td>");
 		 fileWriter = new FileWriter(filePath, true);
 		  fileWriter.write(feedback.toString());
			fileWriter.close();
    	   requestDispatcher.forward(request, response);
    	   return;
    	  }
	}
	
	private boolean checkParameter(String parameter) {
    	  if (parameter != null && !parameter.equals(""))
    		  return true;
    	  return false;
    	 }
	
}
