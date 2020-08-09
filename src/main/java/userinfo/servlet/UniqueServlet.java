package userinfo.servlet;

import userinfo.service.IUserInfoService;
import userinfo.service.UserInfoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UniqueServlet",urlPatterns = "/UniqueServlet")
public class UniqueServlet extends HttpServlet {
    private IUserInfoService userInfoService = new UserInfoService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("user_name");
        PrintWriter out = response.getWriter();
        if (userInfoService.unique(userName)){
            out.write("1");
            out.flush();
            out.close();
        }else {
            out.write("0");
            out.flush();
            out.close();
        }
    }
}
