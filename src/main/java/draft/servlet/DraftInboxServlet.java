package draft.servlet;

import draft.service.DraftService;
import draft.service.IDraftService;
import vo.Draft;
import vo.Email;
import vo.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DraftInboxServlet",urlPatterns = "/DraftInboxServlet")
public class DraftInboxServlet extends HttpServlet {
    private IDraftService draftService = new DraftService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取客户端数据
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        String to = request.getParameter("to");
        //获取session中的数据
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        String userName = userInfo.getUserName();
        //调用service层方法处理数据
        List<Draft> drafts = draftService.page(to,userName, pageNo);
        int count = draftService.getCount(to, userName);
        int pageSize = 3;
        request.setAttribute("drafts",drafts);
        request.setAttribute("count",count);
        request.setAttribute("pageTotal",count%pageSize==0?count/pageSize:count/pageSize+1);
        //响应客户端
        request.getRequestDispatcher("WEB-INF/view/draftboxFrag.jsp").forward(request,response);
    }
}
