
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        out.println("<h2>Hello Servlet</h2>");
        out.println("<form action='/carservlet/hello' method='post'>");
        out.println("Name: <input type='text' name='name'><br>");
        out.println("Password: <input type='password' name='password'><br>");
        out.println("<input type='submit' value='Submit'>");
        out.println("</form>");

        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        // Validate input (in a real-world application, you should perform proper validation)

        Connection conn = DBConnection.getConnection();

        try {
            // Insert user into the database with isadmin set to 0
            String sql = "INSERT INTO users (username, password, isadmin) VALUES (?, ?, 0)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, password);
                statement.executeUpdate();
            }

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h2>User added successfully!</h2>");
            out.println("<p>Name: " + name + "</p>");
            out.println("<p>Password: " + password + "</p>");
            out.println("</body></html>");
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
        } finally {
            DBConnection.closeConnection();
        }
    }
}
