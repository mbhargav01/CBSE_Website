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

public class StudentInfo extends HttpServlet {
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
			ResultSet rs=s.executeQuery("select * from student_details");
			
			out.println("<div>");
			out.println("<table><tr>"
					+ "    <th>STUDENT_ID</th>"
					+ "    <th>STUDENT_NAME</th>"
					+ "    <th>CONTACT</th>"
					+ "    <th>EMAIL_ID</th>"
					+ "    <th>ADDRESS</th>"
					+ "    <th>DATE-OF-BIRTH</th>"
					+ "    <th>FATHER_NAME</th>"
					+ "    <th>MOTHER_NAME</th>"
					+ "  </tr>");
			
			while(rs.next())
			{
			Long id=rs.getLong("id");
			String name=rs.getString("name");
			Long contact=rs.getLong("contact");
			String mail=rs.getString("mail");
			String address=rs.getString("address");
			Date date=rs.getDate("dob");
			String fname=rs.getString("father_name");
			String mname=rs.getString("mother_name");
			
			out.println("<tr>"
					+ "    <td>"+id+"</td>"
					+ "    <td>"+name+"</td>"
					+ "    <td>"+contact+"</td>"
					+ "    <td>"+mail+"</td>"
					+ "    <td>"+address+"</td>"
					+ "    <td>"+date+"</td>"
					+ "    <td>"+fname+"</td>"
					+ "    <td>"+mname+"</td>"
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
