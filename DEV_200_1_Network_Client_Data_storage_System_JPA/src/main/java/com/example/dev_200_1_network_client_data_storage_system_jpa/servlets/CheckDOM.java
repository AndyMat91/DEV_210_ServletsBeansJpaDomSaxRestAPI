package com.example.dev_200_1_network_client_data_storage_system_jpa.servlets;

import com.example.dev_200_1_network_client_data_storage_system_jpa.beans.XML.DemoDOM;
import com.example.dev_200_1_network_client_data_storage_system_jpa.beans.XML.Transformer;
import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.ClientAddressDto;
import com.example.dev_200_1_network_client_data_storage_system_jpa.security.AuthorizationBean;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "CheckDOM", value = "/CheckDOM")
public class CheckDOM extends HttpServlet {

    @EJB
    private DemoDOM demoDOM;
    @EJB
    private Transformer transformer;
    @EJB
    private AuthorizationBean authorizationBean;
    private List<ClientAddressDto> persons;

    private void getPage(PrintWriter out) {
        out.println("<html><body>");
        out.println("<h1 style=\"text-align: center\">Таблица на основе файла Clients.xml (DOM парсер)</h1>");
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
        out.println("</tr>\n");
        persons.forEach(client -> {
            out.println("<tr>\n");
            out.println("<td>" + client.getClientId() + "</td>\n");
            out.println("<td>" + client.getClientName() + "</td>\n");
            out.println("<td>" + client.getType() + "</td>\n");
            out.println("<td>" + client.getAdded() + "</td>\n");
            out.println("<td>" + client.getIp() + "</td>\n");
            out.println("<td>" + client.getMac() + "</td>\n");
            out.println("<td>" + client.getModel() + "</td>\n");
            out.println("<td>" + client.getAddress() + "</td>\n");
            out.println("</tr>\n");
        });
        out.println("</table><br><br>");
        out.println("<form action=\"ViewList\" method=\"doGet\">");
        out.println("<button type=\"submit\">Назад</button><br><br></form>");
        out.println("<h2> Поиск объектов в XML-документе по содержимому поля «Имя клиента»</h2>");
        out.println("<form action=\"CheckDOM\" method=\"post\">");
        out.println("Наименование клиента: <input name=\"client-name\"/>");
        out.println("<button type=\"submit\" name=\"name\" value = \"Search\">Поиск</button> <br><br>");
        out.println("</form>");
        out.println("</body></html>");
    }

    private void getSearchPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        out.println("<html><body>");
        out.println("<h1 style=\"text-align: center\">Таблица на основе файла Clients.xml (DOM парсер)</h1>");
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
        out.println("</tr>\n");
        if(!persons.isEmpty()) {
            persons.forEach(client -> {
                out.println("<tr>\n");
                out.println("<td>" + client.getClientId() + "</td>\n");
                out.println("<td>" + client.getClientName() + "</td>\n");
                out.println("<td>" + client.getType() + "</td>\n");
                out.println("<td>" + client.getAdded() + "</td>\n");
                out.println("<td>" + client.getIp() + "</td>\n");
                out.println("<td>" + client.getMac() + "</td>\n");
                out.println("<td>" + client.getModel() + "</td>\n");
                out.println("<td>" + client.getAddress() + "</td>\n");
                out.println("</tr>\n");
            });
        } else {
            out.println("<h2 style=\"color: red\">Клиент с таким именем в файле Clients.xml не найден</h2>");
        }
        out.println("</table><br><br>");
        out.println("<form action=\"ViewList\" method=\"doGet\">");
        out.println("<button type=\"submit\">Назад</button><br><br></form>");
        out.println("<h2> Поиск объектов в XML-документе по содержимому поля «Имя клиента»</h2>");
        out.println("<form action=\"CheckDOM\" method=\"post\">");
        out.println("Наименование клиента: <input name=\"client-name\"/>");
        out.println("<button type=\"submit\" name=\"name\" value = \"Search\">Поиск</button> <br><br>");
        out.println("</form>");
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
            File newFile = new File("clients.xml");
            transformer.createXmlFile(newFile);
            demoDOM = new DemoDOM();
            demoDOM.setParam("all");
            persons = demoDOM.readFileXml(newFile);
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
            if (request.getParameter("name").equals("Search")) {
                File newFile = new File("clients.xml");
                transformer.createXmlFile(newFile);
                demoDOM = new DemoDOM();
                demoDOM.setParam(Objects.toString(request.getParameter("client-name"), "").trim());
                persons = demoDOM.readFileXml(newFile);
                getSearchPage(response.getWriter(), request, response);
            } else {
                getPage(response.getWriter());}
        } else {
            response.sendRedirect("Authorization");
        }
    }
}
