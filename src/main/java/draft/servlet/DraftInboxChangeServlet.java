package draft.servlet;

import draft.service.DraftService;
import vo.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DraftInboxChangeServlet",urlPatterns = "/DraftInboxChangeServlet")
public class DraftInboxChangeServlet extends HttpServlet {
    private DraftService draftService = new DraftService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String to = request.getParameter("to");
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        String userName = userInfo.getUserName();
        PrintWriter out = response.getWriter();
        if (draftService.change(userName,content,subject,to,id)){
            out.write("1");
        }else out.write("0");
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
