package userinfo.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import userinfo.service.IUserInfoService;
import userinfo.service.UserInfoService;
import vo.UserInfo;

@WebServlet(name = "UserInfoImageServlet",urlPatterns = "/UserInfoImageServlet")
public class UserInfoImageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserInfoService userInfoService = new UserInfoService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        FileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
        servletFileUpload.setFileSizeMax(1024*1024*6*10);
        List<FileItem> fileItemList = null;
        try {
            fileItemList = servletFileUpload.parseRequest(request);//2、将form表单中每个携带数据的标签转换成每个FileItem对象并存到集合中
        } catch (FileUploadException e) {
            e.printStackTrace();
            out.write("2");
            out.flush();
            out.close();
            return;
        } 
        String fileName=null;//新图片
        String imageName=null;//原图片

        for(FileItem fileItem : fileItemList){//3、获取form表单所携带的数据
            System.out.println(fileItem);
            if(fileItem.isFormField()){//为true表示为非文件类型的标签（即文本类型数据）
                if (fileItem.getFieldName().equals("old_imageName")){
                    imageName = fileItem.getString();
                }
            }else{//文件类型
                fileName = fileItem.getName();
                if (!"".equals(fileName)){
                    try {
                        fileName=fileName.substring(fileName.lastIndexOf("."));
                        fileName= UUID.randomUUID().toString()+fileName;
                        System.out.println(fileName);
                        FileOutputStream fileOutputStream = new FileOutputStream("D:\\images\\userinfo\\"+fileName);
                        IOUtils.copy(fileItem.getInputStream(), fileOutputStream);
                        IOUtils.closeQuietly(fileOutputStream);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        String userName = userInfo.getUserName();
        boolean flag = userInfoService.imageUpdate(userName, imageName, fileName);
        System.out.println("servlet:"+flag);
        System.out.println(fileName);
        if (flag){
            out.write("1");
            out.flush();
            out.close();
        }
        out.write("0");
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/userinfo/imageUpdate.jsp").forward(request,response);
    }
}
