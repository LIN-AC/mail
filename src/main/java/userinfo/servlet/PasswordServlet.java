package userinfo.servlet;

import tool.MD5Tool;
import userinfo.service.IUserInfoService;
import userinfo.service.UserInfoService;
import vo.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PasswordServlet",urlPatterns = "/PasswordServlet")
public class PasswordServlet extends HttpServlet {
    private IUserInfoService userInfoService = new UserInfoService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        String userName = userInfo.getUserName();
        PrintWriter out = response.getWriter();
        System.out.println(userInfoService.update(userName,password));
        if (userInfoService.update(userName,password)){
            out.write("1");
        }else out.write("0");
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String old = request.getParameter("password");
        old = MD5Tool.encrypt(old);
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        PrintWriter out = response.getWriter();
        String password = userInfo.getPassword();
        if (password.equals(old)){
            out.write("1");
        }else {
            out.write("0");
        }
        out.flush();
        out.close();
    }
}
