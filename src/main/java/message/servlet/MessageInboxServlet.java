package message.servlet;

import message.service.IMessageService;
import message.service.MessageService;
import vo.Email;
import vo.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MessageInboxServlet",urlPatterns = "/MessageInboxServlet")
public class MessageInboxServlet extends HttpServlet {
    private IMessageService messageService = new MessageService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取客户端数据
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        String from = request.getParameter("from");
        //获取session中的数据
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        String userName = userInfo.getUserName();
        //调用service层方法处理数据
        List<Email> emails = messageService.inboxPage(from,userName, pageNo);
        int count = messageService.getCount(from, userName);
        int pageSize = 3;
        request.setAttribute("emails",emails);
        request.setAttribute("count",count);
        request.setAttribute("pageTotal",count%pageSize==0?count/pageSize:count/pageSize+1);
        //响应客户端
        request.getRequestDispatcher("WEB-INF/view/inboxFrag.jsp").forward(request,response);
    }
}
