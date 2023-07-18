package com.cbse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReportCard extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		out.println("<html><head><link rel='stylesheet' type='text/css' href='report.css'></head><body>"
				+ "<div style='height:140px;border: 2px solid skyblue;'>"
				+ "<img src='img/logo.png' alt='logo...'>"
				+ "<h1 style='margin: -90px 0px 50px 120px;'>Central Board of Secondary Education</h1>"
				+ "<img src='img/sidelide.JPG' alt='side-png..' style='margin: -110px 0px 20px 62%;'>"
				+ "</div>");
		out.println("<h2>REPORT CARD</h2>");
		
		long id=Long.parseLong( request.getParameter("rollno"));
		java.sql.Date date=java.sql.Date.valueOf( request.getParameter("dob"));
		String dob=new SimpleDateFormat("dd-MM-yyyy").format(date);
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","smsystem","smsystem");
			Statement s=con.createStatement();
			ResultSet rs=s.executeQuery("SELECT d.name, r.*, d.father_name, d.mother_name, d.dob FROM student_result r JOIN student_details d ON r.id = d.id where r.id="+id+" and d.dob='"+dob+"'");
			
			while(rs.next())
			{
			Long roll=rs.getLong("id");
			String name=rs.getString("name");
			String fname=rs.getString("father_name");
			String mname=rs.getString("mother_name");
			Date date1=rs.getDate("dob");
			int comp=rs.getInt("computer");
			int eng=rs.getInt("english");
			int phy=rs.getInt("physics");
			int math=rs.getInt("math");
			int chem=rs.getInt("chemistry");
			int total=rs.getInt("obtained_marks");
			float perc=rs.getFloat("percentage");
			String result;
			if(total>180) {
				result="QUALIFIED";
			}else
				result="FAIL";
			
			out.println("<table>"
					+ "    <tr><th>Roll NO.</th>"
					+ "    <td>"+roll+"</td></tr>"
					+ "    <tr><th>Name</th>"
					+ "    <td>"+name+"</td></tr>"
					+ "    <tr><th>Father's Name</th>"
					+ "    <td>"+fname+"</td></tr>"
					+ "    <tr><th>Mother's Name</th>"
					+ "    <td>"+mname+"</td></tr>"
					+ "    <tr><th>Date Of Birth</th>"
					+ "    <td>"+date1+"</td></tr>"
					+ "  </table>");
			
			out.println("<h2>Detail of Marks</h2>");
			out.println("<table><tr>"
					+ "    <th>Subject</th>"
					+ "    <th>Obtain Marks</th>"
					+ "    <th>Total Marks</th>"
					+ "    <th>Percentage(%)</th></tr>"
					
					+ "    <tr><td>COMPUTER</td>"
					+ "    <td>"+comp+"</td>"
					+ "    <td>100</td>"
					+ "    <td rowspan='5'>"+perc+"</td></tr>"
					
					+ "    <tr><td>ENGLISH</td>"
					+ "    <td>"+eng+"</td>"
					+ "    <td>100</td></tr>"
					
					+ "    <tr><td>PHYSICS</td>"
					+ "    <td>"+phy+"</td>"
					+ "    <td>100</td></tr>"
					
					+ "    <tr><td>MATH</td>"
					+ "    <td>"+math+"</td>"
					+ "    <td>100</td></tr>"
					
					+ "    <tr><td>CHEMISTRY</td>"
					+ "    <td>"+chem+"</td>"
					+ "    <td>100</td></tr>"
					+ "  </table>");
			
			out.println("<h2>Result</h2>");
			out.println("<table><tr>"
					+ "    <th>Total Marks</th>"
					+ "    <td>"+total+"</td></tr>"
					+ "    <th>Result</th>"
					+ "    <td style='color:#FF4500;'><b>"+result+"</b></td></tr>"
					+ "  </table><br>");
			out.println("<p><b>DISCLAIMER: </b>CBSE is not responsible for any inadvertent error that might have crept in the results being\r\n"
					+ "published through this Website. The results published on this Website are for the immediate information to\r\n"
					+ "the examinees. These cannot be treated as original marksheets. Original marksheets have been issued by the\r\n"
					+ "Board separately.</p>");
			}
			s.close();
			con.close();
		
		}catch (Exception e) {
			System.out.println(e);
			response.sendRedirect("error.html");
		}
		out.println("</body></html>");
	}

}
