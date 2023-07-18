package com.cbse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><body style='background-image: linear-gradient(140deg, #EADEDB 0%, #BC70A4 50%, #BFD641 75%);'>");
		out.println("<style>"
				+ "table, th, td {"
				+ "  border: 1px solid black;"
				+ "  border-collapse: collapse;"
				+ "}</style>");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","smsystem","smsystem");
			Statement s=con.createStatement();
			ResultSet rs=s.executeQuery("SELECT d.name, r.*, d.father_name, d.mother_name, d.dob FROM student_result r JOIN student_details d ON r.id = d.id");
			out.println("<div>");
			out.println("<table><tr>"
					+ "    <th>STUDENT_NAME</th>"
					+ "    <th>STUDENT_ID</th>"
					+ "    <th>COMPUTER</th>"
					+ "    <th>ENGLISH</th>"
					+ "    <th>PHYSICS</th>"
					+ "    <th>MATH</th>"
					+ "    <th>CHEMISTRY</th>"
					+ "    <th>TOTAL MARKS</th>"
					+ "    <th>PERCENTAGE(%)</th>"
					+ "    <th>FATHER_NAME</th>"
					+ "    <th>MOTHER_NAME</th>"
					+ "    <th>DOB</th>"
					+ "  </tr>");
			
			while(rs.next())
			{
			String name=rs.getString("name");
			Long id=rs.getLong("id");
			int comp=rs.getInt("computer");
			int eng=rs.getInt("english");
			int phy=rs.getInt("physics");
			int math=rs.getInt("math");
			int chem=rs.getInt("chemistry");
			int total=rs.getInt("obtained_marks");
			float perc=rs.getFloat("percentage");
			String fname=rs.getString("father_name");
			String mname=rs.getString("mother_name");
			Date date=rs.getDate("dob");
			
			out.println("<tr>"
					+ "    <td>"+name+"</td>"
					+ "    <td>"+id+"</td>"
					+ "    <td>"+comp+"</td>"
					+ "    <td>"+eng+"</td>"
					+ "    <td>"+phy+"</td>"
					+ "    <td>"+math+"</td>"
					+ "    <td>"+chem+"</td>"
					+ "    <td>"+total+"</td>"
					+ "    <td>"+perc+"</td>"
					+ "    <td>"+fname+"</td>"
					+ "    <td>"+mname+"</td>"
					+ "    <td>"+date+"</td>"
					+ "  </tr>");
			
			}
			out.println("</table>");
			out.println("<p>goto CBSE@Board Home Page "+"<a href='index.html'>click-here</a></p></div>");
			s.close();
			con.close();
		
		} catch (Exception e) {
			System.out.println(e);
			response.sendRedirect("error.html");
		}
		out.println("</body></html>");
		
	}

}
