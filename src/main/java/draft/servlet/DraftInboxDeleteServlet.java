package draft.servlet;

import draft.service.DraftService;
import draft.service.IDraftService;
import vo.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DraftInboxDeleteServlet",urlPatterns = "/DraftInboxDeleteServlet")
public class DraftInboxDeleteServlet extends HttpServlet {
    private IDraftService draftService = new DraftService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        String userName = userInfo.getUserName();
        PrintWriter out = response.getWriter();
        if (draftService.delete(userName,id))
            out.write("1");
        else out.write("0");
        out.flush();
        out.close();
    }
}
