package com.cbse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowDownloadDoc extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		out.println("<html><body>");
		out.println("<style>table{ background-image: linear-gradient(140deg, #EADEDB 0%, #BC70A4 50%, #BFD641 75%);"
				+ "	border: 1px solid;"
				+ "	width:60%;"
				+ "	text-align:left;}"
				+ "	th, td {"
				+ "	border: 1px solid #000;"
				+ "	padding: 0.5em 1em;}"
				+ "}</style>");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","smsystem","smsystem");
			Statement s=con.createStatement();
			ResultSet rs=s.executeQuery("select * from library");
			
			out.println("<div style='margin-left:25%'><form action='docs'>");
			out.println("<table><tr>"
					+ "    <th>Remarks</th>"
					+ "    <th>Files</th>"
					+ "    <th>Download</th>"
					+ "  </tr>");
			
			while(rs.next())
			{
			String remark1=rs.getString("REMARKS");
			Blob file1=rs.getBlob("FILES");
			
			out.println("<tr>"
					+ "    <td>"+remark1+"</td>"
					+ "    <td>"+file1+"</td>"
					+ "    <td><a href='docs?remark="+remark1+"'>Click Here</a></td>"
					+ "  </tr>");
			}
			out.println("</table>");
			out.println("<p>goto CBSE@Board Home Page "+"<a href='index.html'>click-here</a></p></div></form>");
			s.close();
			con.close();
		
		} catch (Exception e) {
			System.out.println(e);
			response.sendRedirect("error.html");
		}
		out.println("</body></html>");
		
		
	}

}
