package com.how2java;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "loginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        Subject subject = SecurityUtils.getSubject();//前面的三步在web.xml的listner和filter中处理了
        UsernamePasswordToken token = new UsernamePasswordToken(name,password);

        try {
            subject.login(token);
            Session session=subject.getSession();
            session.setAttribute("subject", subject);

            resp.sendRedirect("/");
        } catch (AuthenticationException e) {
            req.setAttribute("error", "验证失败");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }

    }
}
