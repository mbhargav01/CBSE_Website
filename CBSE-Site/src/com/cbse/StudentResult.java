package com.cbse;

import java.io.IOException;
import java.io.NotActiveException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentResult extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><body id='body'>");
		/*out.println("<style>"
				+ "#body {"
				+ "  background-image: url('img/background.jpg');"
				+ "  background-repeat: no-repeat;"
				+ "  background-size: cover;"
				+ "}"
				+ "p,h1,h2{color:white;}</style>");
		*/
		Long studentId=Long.parseLong( request.getParameter("id"));
		float comp=Float.parseFloat( request.getParameter("computer"));
		float eng=Float.parseFloat( request.getParameter("english"));
		float phy=Float.parseFloat( request.getParameter("physics"));
		float math=Float.parseFloat( request.getParameter("math"));
		float chem=Float.parseFloat( request.getParameter("chemistry"));
		float obtained=comp+eng+phy+math+chem;
		float percen=(comp+eng+phy+math+chem)/5;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","smsystem","smsystem");
			
			Statement s=con.createStatement();
			
			ResultSet rs=s.executeQuery("select id from STUDENT_DETAILS where id="+studentId+"");
			while(rs.next())
			{
			System.out.println(rs.getLong(1));
			}
			/*
			s.executeUpdate("create table student_result(id number(10) NOT NULL primary key,computer number,english number,physics number,"
					+ "math number,chemistry number,obtained_marks number,percentage number)");
			s.close();
			*/
			PreparedStatement ps=con.prepareStatement("insert into student_result values(?,?,?,?,?,?,?,?)");
			ps.setLong(1,studentId);
			ps.setFloat(2, comp);
			ps.setFloat(3, eng);
			ps.setFloat(4, phy);
			ps.setFloat(5, math);
			ps.setFloat(6, chem);
			ps.setFloat(7, obtained);
			ps.setFloat(8, percen);
			ps.executeUpdate();
			out.println("<div style='text-align: justify;width: 600px;height: 300px;margin: 20% 0% 0% 35%;'>");
			 
			out.println("<h1>Hi! "+studentId+",</h1>");
			out.println("<h2>student marks updated successfully.</h2>");
			out.println("<p>add more student marks <a href='result.html'>click-here</a></p>");
			out.println("<p>view student result <a href='view-result'>click-here</a></p>");
			out.println("<p>goto CBSE@Board Home Page <a href='index.html'>click-here</a></p></div>");
			ps.close();
			con.close();
		
		}
		catch (Exception e) {
			System.out.println(e);
			response.sendRedirect("error.html");
		}
		
	}

}
