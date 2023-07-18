package com.cbse;

import java.io.IOException;
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

public class Admin extends HttpServlet {
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
				+ "p,h1,h2{color:white;}</style>");*/
		
		String adminId=request.getParameter("id");
		String password=request.getParameter("password");
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","smsystem","smsystem");
			/*
			Statement s=con.createStatement();
			s.executeUpdate("create table admin_details(admin_id varchar2(20) NOT NULL primary key,password varchar2(20) NOT NULL)");
			s.close();
			*/
			
			/*PreparedStatement ps=con.prepareStatement("insert into admin_details values(?,?)");
			ps.setString(1,adminId);
			ps.setString(2,password);
			*/
			
			PreparedStatement ps=con.prepareStatement("select admin_id from admin_details where admin_id='"+adminId+"'and password='"+password+"'");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
			String admin=rs.getString("admin_id");
			out.println("<h1>Hello! "+admin+",</h1>");
			out.println("<h2><a href=''>1.Update Student Details</a></h2>");
			out.println("<h2><a href='result.html'>2.Add Student Result</a></h2>");
			out.println("<h2><a href=''>3.Search Student's Average marks</a></h2>");
			out.println("<p>goto CBSE@Board Home Page <a href='index.html'>click-here</a></p></div>");
			}
			//out.println("<div style='text-align: justify;width: 600px;height: 300px;margin: 20% 0% 0% 35%;'>");
	
			ps.close();
			con.close();
		
		}catch (Exception e) {
			System.out.println(e);
		}
	}

}
