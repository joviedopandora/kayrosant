/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author luis
 */
public class SessionFilter implements Filter {

    private ArrayList<String> urlList;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String urls = filterConfig.getInitParameter("avoid-urls");
        StringTokenizer token = new StringTokenizer(urls, ",");

        urlList = new ArrayList<>();

        while (token.hasMoreTokens()) {
            urlList.add(token.nextToken());

        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String url = httpServletRequest.getServletPath();
        boolean allowedRequest = false;
        
        if (urlList.contains(url)) {
            allowedRequest = true;
        }

        if (!allowedRequest) {
            HttpSession session = httpServletRequest.getSession(false);
            if (session == null) {
                //httpServletResponse.sendRedirect("/Error.jsf");
                //httpServletRequest.getServletContext().getRequestDispatcher("/index.jsf").forward(request, response);
            } else if (session.getAttribute("usrLog") == null) {
               // httpServletRequest.getServletContext().getRequestDispatcher("/index.jsf").forward(request, response);
               
            }
        }

        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {
    }
}
