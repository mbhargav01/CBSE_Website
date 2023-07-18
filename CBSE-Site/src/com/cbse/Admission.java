package com.cbse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Admission extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><body id='body'>");
		out.println("<style>"
				+ "#body {"
				+ "  background-image: url('img/background.jpg');"
				+ "  background-repeat: no-repeat;"
				+ "  background-size: cover;"
				+ "}"
				+ "p,h1,h2{color:white;}</style>");
		
		long id=Long.parseLong( request.getParameter("id"));
		String name=request.getParameter("name");
		long contact=Long.parseLong( request.getParameter("contact"));
		String mail=request.getParameter("mail");
		String address=request.getParameter("address");
		String date=request.getParameter("date");
		String fname=request.getParameter("fname");
		String mname=request.getParameter("mname");
		SimpleDateFormat sdate=new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		try {
			date1 = sdate.parse(date);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		long ms=date1.getTime();
		java.sql.Date dob=new java.sql.Date(ms);
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","smsystem","smsystem");
			
			/*Statement s=con.createStatement();
			s.executeUpdate("create table student_details(id number(10) NOT NULL primary key,name varchar2(30) NOT NULL,contact number(10),mail varchar2(50),"
					+ "address varchar2(50),DOB Date,father_name varchar2(30),mother_name varchar2(30))");
			s.close();*/
			
			PreparedStatement ps=con.prepareStatement("insert into student_details values(?,?,?,?,?,?,?,?)");
			ps.setLong(1,id);
			ps.setString(2,name);
			ps.setLong(3,contact);
			ps.setString(4,mail);
			ps.setString(5,address);
			ps.setDate(6,dob);
			ps.setString(7,fname);
			ps.setString(8,mname);
			ps.executeUpdate();
			out.println("<div style='text-align: justify;width: 600px;height: 300px;margin: 20% 0% 0% 35%;'>");
			 
			out.println("<h1>Hi! "+name+",</h1>");
			out.println("<h2>your admission registration successfully.</h2>");
			out.println("<p>goto CBSE@Board Home Page "+"<a href='index.html'>click-here</a></p></div>");
			ps.close();
			con.close();
		
		} catch (Exception e) {
			System.out.println(e);
			response.sendRedirect("error.html");
		}
		
	}

}
