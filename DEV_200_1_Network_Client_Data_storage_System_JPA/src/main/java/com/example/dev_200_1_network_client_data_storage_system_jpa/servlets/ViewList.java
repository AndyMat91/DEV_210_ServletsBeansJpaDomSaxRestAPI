package com.example.dev_200_1_network_client_data_storage_system_jpa.servlets;

import com.example.dev_200_1_network_client_data_storage_system_jpa.beans.SelectBean;
import com.example.dev_200_1_network_client_data_storage_system_jpa.security.AuthorizationBean;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@WebServlet(name = "ViewList", value = "/ViewList")
public class ViewList extends HttpServlet {

    @EJB
    private SelectBean selectBean;

    @EJB
    private AuthorizationBean authorizationBean;

    private void getPage(PrintWriter out) {
        head(out);
        selectBean.findAllClient().forEach(client -> {
            out.println("<tr>\n");
            out.println("<td>" + client.getClientId() + "</td>\n");
            out.println("<td>" + client.getClientName() + "</td>\n");
            out.println("<td>" + client.getType() + "</td>\n");
            out.println("<td>" + client.getAdded() + "</td>\n");
            out.println("<td>" + Objects.toString(client.getIp(), "") + "</td>\n");
            out.println("<td>" + Objects.toString(client.getMac(), "") + "</td>\n");
            out.println("<td>" + Objects.toString(client.getModel(), "") + "</td>\n");
            out.println("<td>" + Objects.toString(client.getAddress(), "") + "</td>\n");
            out.println("<td><a href=\"http://localhost:8080/DEV_200_1_Network_Client_Data_storage_System_JPA-1.0-SNAPSHOT/Update?client-id=" + client.getClientId() + "&name=CreateAddress\">добавить адрес клиенту</a></td>\n");
            out.println("<td><a href=\"http://localhost:8080/DEV_200_1_Network_Client_Data_storage_System_JPA-1.0-SNAPSHOT/Delete?client-id=" + client.getClientId() + "&name=DeleteAddress&mac=" + client.getMac() + " \">удалить адрес</a></td>\n");
            out.println("<td><a href=\"http://localhost:8080/DEV_200_1_Network_Client_Data_storage_System_JPA-1.0-SNAPSHOT/Update?client-id=" + client.getClientId() + "&name=UpdateClient\">изменить данные о клиенте</a></td>\n");
            out.println("<td><a href=\"http://localhost:8080/DEV_200_1_Network_Client_Data_storage_System_JPA-1.0-SNAPSHOT/Update?client-id=" + client.getClientId() + "&name=UpdateAddress&mac=" + client.getMac() + " \">изменить адрес</a></td>\n");
            out.println("</tr>\n");
        });
        tail(out);
    }

    private void getFilterPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        head(out);
        selectBean.findAllClientWithType(Objects.toString(request.getParameter("clientType"), ""), selectBean.findAllClientWithNameOrAddress(Objects.toString(request.getParameter("client-name-address"), ""))).forEach(client -> {
            out.println("<tr>\n");
            out.println("<td>" + client.getClientId() + "</td>\n");
            out.println("<td>" + client.getClientName() + "</td>\n");
            out.println("<td>" + client.getType() + "</td>\n");
            out.println("<td>" + client.getAdded() + "</td>\n");
            out.println("<td>" + Objects.toString(client.getIp(), "") + "</td>\n");
            out.println("<td>" + Objects.toString(client.getMac(), "") + "</td>\n");
            out.println("<td>" + Objects.toString(client.getModel(), "") + "</td>\n");
            out.println("<td>" + Objects.toString(client.getAddress(), "") + "</td>\n");
            out.println("<td><a href=\"http://localhost:8080/DEV_200_1_Network_Client_Data_storage_System_JPA-1.0-SNAPSHOT/Update?client-id=" + client.getClientId() + "&name=CreateAddress\">добавить адрес клиенту</a></td>\n");
            out.println("<td><a href=\"http://localhost:8080/DEV_200_1_Network_Client_Data_storage_System_JPA-1.0-SNAPSHOT/Delete?client-id=" + client.getClientId() + "&name=DeleteAddress&mac=" + client.getMac() + " \">удалить адрес</a></td>\n");
            out.println("<td><a href=\"http://localhost:8080/DEV_200_1_Network_Client_Data_storage_System_JPA-1.0-SNAPSHOT/Update?client-id=" + client.getClientId() + "&name=UpdateClient\">изменить данные о клиенте</a></td>\n");
            out.println("<td><a href=\"http://localhost:8080/DEV_200_1_Network_Client_Data_storage_System_JPA-1.0-SNAPSHOT/Update?client-id=" + client.getClientId() + "&name=UpdateAddress&mac=" + client.getMac() + " \">изменить адрес</a></td>\n");
            out.println("</tr>\n");
        });
        tail(out);
    }

    private void head(PrintWriter out) {
        out.println("<html><body>");
        out.println("<h1 style=\"text-align: center\"> Таблица клиент - адрес </h1>");
        out.println("<table style=\"border-collapse: collapse\"; border=\"1 double black\"\n");
        out.println("<tr>\n");
        out.println("<th>ID</th>");
        out.println("<th>Имя</th>");
        out.println("<th>Тип</th>");
        out.println("<th>Время создания</th>");
        out.println("<th>IP-адрес</th>");
        out.println("<th>MAC-адрес</th>");
        out.println("<th>Модель</th>");
        out.println("<th>Адрес места нахождения</th>");
        out.println("<th colspan = \"4\">Изменение данных</th>");
        out.println("</tr>\n");
    }

    public void tail(PrintWriter out) {
        out.println("</table><br><br>");
        out.println("<form action=\"Delete\" method=\"doGet\">");
        out.println("ID клиента: <input name=\"client-id\"/>");
        out.println("<button type=\"submit\" name=\"name\" value = \"DeleteClient\">Удалить клиента</button> <br><br>");
        out.println("</form>");
        out.println("<h2> Фильтр по содержимому полей </h2>");
        out.println("<form action=\"ViewList\" method=\"post\">");
        out.println("Тип клиента: <select name=\"clientType\">");
        out.println("<option>--> Выберите тип <--</option>");
        out.println("<option>Юридическое лицо</option>");
        out.println("<option>Физическое лицо</option></select>");
        out.println("Имя клиента или его адрес: <input name=\"client-name-address\"/>");
        out.println("<button type=\"submit\" name=\"name\" value = \"Filter\">Поиск</button> <br><br>");
        out.println("</form>");
        out.println("<form action=\"Create\" method=\"doGet\">");
        out.println("<button type=\"submit\">Перейти на страницу добавления клиента</button><br><br></form>");
        out.println("<h2> SAX парсер </h2>");
        out.println("<form action=\"CheckSAX\" method=\"doGet\">");
        out.println("<button type=\"submit\">Разобрать файл Client.xml и вывести содержимое</button><br><br></form>");
        out.println("<h2> DOM парсер </h2>");
        out.println("<form action=\"CheckDOM\" method=\"doGet\">");
        out.println("<button type=\"submit\">Разобрать файл Client.xml и вывести содержимое</button><br><br></form>");
        out.println("</body></html>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String savedSessionId = Objects.toString(authorizationBean.getSessionId((String) session.getAttribute("login")), "");
        String currentSessionId = session.getId();
        if (savedSessionId.equals(currentSessionId)) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            getPage(response.getWriter());
        } else {
            response.sendRedirect("Authorization");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String savedSessionId = Objects.toString(authorizationBean.getSessionId((String) session.getAttribute("login")), "");
        String currentSessionId = session.getId();
        if (savedSessionId.equals(currentSessionId)) {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            if (request.getParameter("name").equals("Filter")) {
                getFilterPage(response.getWriter(), request, response);
            } else {
                getPage(response.getWriter());}
        } else {
            response.sendRedirect("Authorization");
        }
    }
}
