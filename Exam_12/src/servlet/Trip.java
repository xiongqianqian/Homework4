package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class Trip
 */

@WebServlet(initParams = { @WebInitParam(name = "upload_path", value = "uplod/public/files/") })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 50, // 50 MB
		maxRequestSize = 1024 * 1024 * 100)
// 100 MB

public class Trip extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	String uploadFilePath;


	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	
	
	String fileName = "history";
	String subDirName = ".";
	String filePath;
	String testPath;
	File filePathDir;

	String testsubDirName = "test";
	File testfilePathDir;
	FileWriter fileWriter;
	FileReader fileReader;
	private final int fileLimitSize = 50 * 1024 * 1024;

	public void init() {

		// Here we get the absolute path of the destination directory
		// uploadFilePath = this.getServletContext().getRealPath(UPLOAD_DIR) +
		// File.separator;
		String separator = System.getProperty("file.separator");

		// create a file to keep entries
		filePath = this.getServletContext().getRealPath(separator) + subDirName + separator;

		testPath = this.getServletContext().getRealPath(separator) + testsubDirName + separator;

		testfilePathDir = new File(testPath);
		if (!testfilePathDir.exists()) {
			testfilePathDir.mkdir();
		}

		filePathDir = new File(filePath);
		if (!filePathDir.exists()) {
			filePathDir.mkdir();
		}

		filePath += fileName;

		uploadFilePath = this.getServletContext().getRealPath(getServletConfig().getInitParameter("upload_path"))
				+ File.separator;

		// Here we create the destination directory under the project main
		// directory if it does not exists
		// mkdirs & mkdir:
		File fileSaveDir = new File(uploadFilePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdirs();
		}

	}

	public Trip() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: trip.java
		// ").append(request.getContextPath());

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<html><head>");
		out.println("<title></title>");
		out.println("</head><body>");
		out.println("<h2>Add Trip Information</h2>");

		out.println("<form method='POST' action='trip.html' enctype='multipart/form-data'>");

		out.println("<table border='0'>");

		out.println("<tr><th>Date</th><td>");
		out.println("<input type='text' name='date' size='20' required></td><tr>");

		out.println("<tr><th>Destination</th><td>");
		out.println("<input type='text' name='destination' size='20' required></td><tr>");

		out.println("<tr><th>Destination Picture</th><td>");
		out.println("<input name='fileName' type='file' multiple></td><tr>");

		out.println("<tr><th>Price </th><td>");
		out.println("<input type='text' name='price' size='20' required></td><tr>");

		out.println("<tr><td></td><td><input type='submit' name='Next' value='Submit'></td></tr>");
		out.println("</table>");
		out.println("</form>");

		// String file = fileName;

	}

	private boolean checkParameter(String parameter) {
		if (parameter != null && !parameter.equals(""))
			return true;
		return false;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		String date = request.getParameter("date");
		String destination = request.getParameter("destination");

		File fileObj = null;

		String price = request.getParameter("price");

		StringBuilder feedback = new StringBuilder();

		String filename = null;
		
		String dbUserName= "admin";
		String dbPassword= "123456";
		String dbName = "test";
		String dbTableName="exam";
		PrintWriter out = response.getWriter();
		 try {
			   // Here we load the database driver
			   // Class.forName("oracle.jdbc.driver.OracleDriver");
			   Class.forName("com.mysql.jdbc.Driver");
			   // Here we set the JDBC URL for the Oracle database
			   // String url="jdbc:oracle:thin:@db.cc.puv.fi:1521:ora817";
			   String url = "jdbc:mysql://localhost:3306/" + dbName;
			   // Here we create a connection to the database
			   // conn=DriverManager.getConnection(url, "scott", "tiger");
			   conn = DriverManager.getConnection(url, dbUserName, dbPassword);
			   // Here we create the statement object for executing SQL commands
			   stmt = conn.createStatement();
			   String[] acceptedImages = new String[] { "jpg", "gif", "png" };
			   
			   //	Instead of get a list of files, we get the file name which has just been uploaded 
			   File[] imageFile = new File(testPath).listFiles(new ImageNameFilter(acceptedImages));
			   
			   File imgFile = new File(testPath + fileName);
			   // File image =null;
			   
			   FileInputStream fis = null;
			   // Here we initialize the preparedStatement object
			   
			   String updateString = "update " + dbTableName
					    + " set date='" + date + "' where destination like '" + destination + "'";	
			   
			   ps = conn.prepareStatement("insert into " + dbTableName
			     + "(	date, destination, filename, 	trip) " + "values(?,?,?,?) where date='null'");
			   
			   		// Here we set the name of the file as the value of the first
			    	// column.
			    	ps.setString(1,date);
			    	// Here we set the comment as the value of the 2nd
			    	// column.
			    	ps.setString(2,destination );
			    	
			    	
			    	
			    	fis = new FileInputStream(imgFile.getPath());
			    	// Here we set the input stream for the file as the value for
			    	// the
			    	// 3rd column.
			    	ps.setBinaryStream(3, (InputStream) fis, (int) (imgFile.length()));
			    	// Here we set the time of the post as the 4th
			    	// column.
			    	
			    	ps.setString(4, price);
			    	
			    	int counter = ps.executeUpdate();
			    	// Here we close the file input stream.
			    	fis.close();
			    	
			   
			   
			  
			  } catch (Exception e) {
			   out.println("position 0"+ e.getMessage());
			  } finally {
			   try {
			    if (stmt != null)
			     stmt.close();
			    if (conn != null)
			     conn.close();
			   } catch (SQLException e) {
			    out.print("position 1" + e.getMessage());
			   }
			  }
		
		
		

		fileWriter = new FileWriter(filePath, true);

		for (Part part : request.getParts()) {

			filename = getFileName(part);

			if (!filename.equals("")) {
				fileObj = new File(filename);
				filename = fileObj.getName();

				filename = date + "_" + filename;

				fileObj = new File(testPath + filename);

				part.write(fileObj.getAbsolutePath());

				feedback.append("<td>" + date + "</td><td>" + destination + "</td><td>");
				feedback.append("<img src='"
						+ testPath
						+ filename
						+ "' width='200' height='200'>");
				feedback.append("</td><td>" + price + "</td></tr>" + "                   ");
			}

		}

		fileWriter.write(feedback.toString());
		fileWriter.close();

		 out = response.getWriter();

		boolean checkUserDate = checkParameter(date);
		boolean checkUserDes = checkParameter(destination);
		out.println(testPath);
		out.println(checkUserDate && checkUserDes);
		//RequestDispatcher dispatcher = request.getRequestDispatcher("summary.html");

		if (checkUserDate && checkUserDes) {
			// Here we initialize the RequestDispatcher object.

			//response.sendRedirect("summary.html?info=
			response.sendRedirect("summary.html");

			//dispatcher.forward(request, response);
			//return;
		}

	}

	private String getFileName(Part part) {

		String contentDisp = part.getHeader("content-disposition");

		if (contentDisp != null) {

			/*
			 * In the following line we split a piece of text like the
			 * following: form-data; name="fileName"; filename=
			 * "C:\Users\Public\Pictures\Sample Pictures\img.jpg"
			 */
			String[] tokens = contentDisp.split(";");

			for (String token : tokens) {
				if (token.trim().startsWith("filename")) {
					return new File(token.split("=")[1].replace('\\', '/')).getName().replace("\"", "");
				}
			}
		}

		return "";
	}

}
