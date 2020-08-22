package message.servlet;

import message.service.IMessageService;
import message.service.MessageService;
import vo.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MessageSendServlet",urlPatterns = "/MessageSendServlet")
public class MessageSendServlet extends HttpServlet {
    private IMessageService messageService = new MessageService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取客户端数据
        String toUser = request.getParameter("toUser");//获取收件人
        String subject = request.getParameter("subject");//获取主题
        String content = request.getParameter("content");//获取邮件内容
        System.out.println(toUser);
        System.out.println(subject);
        System.out.println(content);
        //获取session数据
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        String userName = userInfo.getUserName();
        String password = userInfo.getPassword();
        //调用service方法
        boolean flag = messageService.send(userName, password, toUser, subject, content);
        PrintWriter out = response.getWriter();
        //返回结果
        if (flag) out.write("1");
        else out.write("0");
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("111111");

    }
}
