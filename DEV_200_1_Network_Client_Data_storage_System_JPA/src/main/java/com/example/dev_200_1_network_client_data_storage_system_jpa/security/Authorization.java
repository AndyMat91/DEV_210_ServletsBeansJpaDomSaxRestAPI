package com.example.dev_200_1_network_client_data_storage_system_jpa.security;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "Authorization", value = "/Authorization")
public class Authorization extends HttpServlet {

    @EJB
    private AuthorizationBean authorizationBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/DEV_200_1_Network_Client_Data_storage_System-1.0-SNAPSHOT/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = Objects.toString(request.getParameter("login"), "");
        String password = Objects.toString(request.getParameter("password"), "");
        HttpSession session = request.getSession();

        if (login.equals("admin") && password.equals("admin")) {
            request.getSession().setAttribute("login", login);
            authorizationBean.addLoginSession(login, session.getId());
            response.sendRedirect("Create");
        } else {
            authorizationBean.removeLogin(login);
            request.getSession().setAttribute("Error_auth", "Неправильный логин или пароль!");
            response.sendRedirect("/DEV_200_1_Network_Client_Data_storage_System_JPA-1.0-SNAPSHOT/index.jsp");
        }
    }
}

