package draft.servlet;

import draft.service.DraftService;
import draft.service.IDraftService;
import vo.Draft;
import vo.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DraftInboxShowServlet",urlPatterns = "/DraftInboxShowServlet")
public class DraftInboxShowServlet extends HttpServlet {
    private IDraftService draftService = new DraftService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        String userName = userInfo.getUserName();
        Draft draft = draftService.show(id, userName);
        request.setAttribute("draft",draft);
        request.getRequestDispatcher("WEB-INF/view/draftboxshow.jsp").forward(request,response);
    }
}
