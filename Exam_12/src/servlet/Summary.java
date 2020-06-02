package servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Summary
 */

@WebServlet(initParams = { @WebInitParam(name = "upload_path", value = "uplod/public/files/") })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
maxFileSize = 1024 * 1024 * 50, // 50 MB
maxRequestSize = 1024 * 1024 * 100)
//100 MB
public class Summary extends HttpServlet {
	private static final long serialVersionUID = 1L;
String uploadFilePath;
	
	
	String fileName = "history";
	String subDirName = ".";
	String filePath;
	String testPath;
	File filePathDir;
	
	String testsubDirName = "test";
	File testfilePathDir;
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
		
		testPath=this.getServletContext().getRealPath(separator) + testsubDirName
				+ separator;
		
		testfilePathDir = new File(testPath);
		if (!testfilePathDir.exists()) {
			testfilePathDir.mkdir();
		}
		
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
    public Summary() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println("<html><head>");
        out.println("<title>Summary</title>");
        out.println("</head><body>");
		out.println("<table  border ='1'>");
		out.println("<tr><th>Name</th><th>Comment</th><th>Image</th><th>Name</th><th>Comment</th><th>Image</th><th>Comment</th><th>Image</th></tr>");
		
		fileReader = new FileReader(filePath);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line;
		while ((line = bufferedReader.readLine()) != null)
			out.println(line);
		out.println("</table>");
		
		out.println("<br>");
		out.println(filePath);
		out.println("<center>");
		out.println("<a href='LogoutServlet.html'>Logout</a>");
		out.println("</center></body>");

		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	}

}
