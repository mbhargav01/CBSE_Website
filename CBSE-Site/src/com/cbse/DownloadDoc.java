package com.cbse;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadDoc extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String remark1 = request.getParameter("remark");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "smsystem",
					"smsystem");

			String query = "select * from library where remarks='" + remark1 + "'";
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				String fileName = rs.getString(1);
                Blob blob = rs.getBlob(2);
                InputStream input = blob.getBinaryStream();

                int fileSize = (int) blob.length();

                ServletContext context = getServletContext();
                String mimeType = context.getMimeType(fileName);

                if (mimeType == null) {        
                    mimeType = "application/pdf";
                }

                response.setContentType(mimeType);
                response.setContentLength(fileSize);
                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=", fileName);
                response.setHeader(headerKey, headerValue);

                OutputStream output = response.getOutputStream();

                byte[] buffer = new byte[4096];
                int bytesRead = -1;

                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                input.close();
                output.close();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
