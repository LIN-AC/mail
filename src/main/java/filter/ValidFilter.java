package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "ValidFilter",urlPatterns = "/*")
public class ValidFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String requestURL = request.getRequestURI();
        String [] jsps = {"MainServlet","main.jsp","imageUpdate.jsp","inboxFrag.jsp","outboxFrag.jsp","passwordFrag.jsp"};
        for (String jsp : jsps) {
            if (requestURL.endsWith(jsp)){//登录后访问的内容
                Object userInfo = request.getSession().getAttribute("userInfo");
                if (userInfo==null){
                    ((HttpServletResponse)resp).sendRedirect("LoginServlet");
                    return;
                }
            }
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
