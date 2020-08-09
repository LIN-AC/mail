package userinfo.servlet;

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

@WebServlet(name = "LoginServlet",urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    private IUserInfoService userInfoService = new UserInfoService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("user_name");
        String password = request.getParameter("password");
        PrintWriter out = response.getWriter();
        UserInfo userInfo = userInfoService.login(userName, password);
        if (userInfo.getId()!=null){
            request.getSession().setAttribute("userInfo",userInfo);
            out.write("1");
        }else out.write("0");
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/userinfo/login.jsp").forward(request,response);
    }
}
