package com.example.dev_200_1_network_client_data_storage_system_jpa.servlets;

import com.example.dev_200_1_network_client_data_storage_system_jpa.beans.ReturningErrors;
import com.example.dev_200_1_network_client_data_storage_system_jpa.beans.UpdateBean;
import com.example.dev_200_1_network_client_data_storage_system_jpa.security.AuthorizationBean;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@WebServlet(name = "Create", value = "/Create")
public class Create extends HttpServlet {

    @EJB
    private UpdateBean updateBean;

    @EJB
    private ReturningErrors returningErrors;

    @EJB
    private AuthorizationBean authorizationBean;

    private void getPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        out.println("<html><head>");
        out.println("<title>Create form</title>");
        out.println("</head><body>");
        out.println("<h1 style=\"text-align: center\">Создание клиента</h1>");

        Object obj1 = request.getSession().getAttribute("Error_field");
        Object obj2 = request.getSession().getAttribute("Error_reason");
        if (obj1 instanceof String || obj2 instanceof String) {
            String message1 = (String) request.getSession().getAttribute("Error_field");
            String message2 = (String) request.getSession().getAttribute("Error_reason");
            out.println("<h3 style=\"color: brown\">" + message1 + message2 + "</h3>");
            request.getSession().removeAttribute("Error_field");
            request.getSession().removeAttribute("Error_reason");
        }

        out.println("<form action=\"Create\" method=\"post\">");
        out.println("<div style=\"display: flex; flex-direction: column\">");
        out.println("<div style=\"margin: 15px; padding: 15px; border: 1px solid black; border-radius: 15px;\">");
        out.println("<h2>Введите информацию о клиенте</h2><div>");
        out.println("наименование клиента: <input name=\"clientName\"><br>");
        out.println("тип клиента: <select name=\"clientType\">");
        out.println("<option>--> Выберите тип <--</option>");
        out.println("<option>Юридическое лицо</option>");
        out.println("<option>Физическое лицо</option>");
        out.println("</select></div></div>");
        out.println("<div style=\"margin: 15px; padding: 15px; border: 1px solid black; border-radius: 15px;\">");
        out.println("<h2>Введите информацию об адресе клиента</h2><div>");
        out.println("сетевой адрес устройства (IP): <input name=\"clientIp\"><br>");
        out.println("физический адрес устройства (MAC): <input name=\"clientMac\"><br>");
        out.println("модель устройства:<input name=\"clientModel\"><br>");
        out.println("адрес места нахождения:<input name=\"clientAddress\"><br>");
        out.println("</div></div></div>");
        out.println("<button type=\"submit\">Добавить клиента</button><br><br>");
        out.println("<button type=\"reset\">Очистить введенные данные</button>");
        out.println("</form>");
        out.println("<form action=\"ViewList\" method=\"doGet\">");
        out.println("<button type=\"submit\">Таблица клиент-адрес</button><br><br>");
        out.println("</form></body></html>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String savedSessionId = Objects.toString(authorizationBean.getSessionId((String) session.getAttribute("login")), "");
        String currentSessionId = session.getId();
        if (savedSessionId.equals(currentSessionId)) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            getPage(response.getWriter(), request, response);
        } else {
            response.sendRedirect("Authorization");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = Objects.toString(request.getParameter("clientName"), "");
        String type = Objects.toString(request.getParameter("clientType"), "");
        String ip = Objects.toString(request.getParameter("clientIp"), "");
        String mac = Objects.toString(request.getParameter("clientMac"), "");
        String model = Objects.toString(request.getParameter("clientModel"), "");
        String address = Objects.toString(request.getParameter("clientAddress"), "");

        if (!updateBean.createClient(name, type, ip, mac, model, address)) {
            request.getSession().setAttribute("Error_field", returningErrors.getErrorFieldType());
            request.getSession().setAttribute("Error_reason", returningErrors.getErrorReasonType());
            response.sendRedirect("Create");
        } else response.sendRedirect("ViewList");


    }
}

