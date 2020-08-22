package record.servlet;

import record.service.IRecordService;
import record.service.RecordService;
import vo.Email;
import vo.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "RecordOutboxServlet",urlPatterns = "/RecordOutboxServlet")
public class RecordOutboxServlet extends HttpServlet {
    IRecordService recordService = new RecordService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取客户端数据
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        String to = request.getParameter("to");
        //获取session中的数据
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        String userName = userInfo.getUserName();
        //调用service层方法
        List<Email> list = recordService.page(to, userName, pageNo);
        int count = recordService.getCount(to, userName);
        //处理数据
        int pageSize = 3;
        request.setAttribute("emils",list);
        request.setAttribute("count",count);
        request.setAttribute("pageTotal",count%pageSize==0?count/pageSize:count/pageSize+1);

        //响应客户端
        request.getRequestDispatcher("WEB-INF/view/outboxFrag.jsp").forward(request,response);
    }
}
