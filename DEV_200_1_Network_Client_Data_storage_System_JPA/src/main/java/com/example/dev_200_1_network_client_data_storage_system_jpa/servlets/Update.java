package com.example.dev_200_1_network_client_data_storage_system_jpa.servlets;


import com.example.dev_200_1_network_client_data_storage_system_jpa.beans.ReturningErrors;
import com.example.dev_200_1_network_client_data_storage_system_jpa.beans.UpdateBean;
import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.AddressDto;
import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.ClientDto;
import com.example.dev_200_1_network_client_data_storage_system_jpa.security.AuthorizationBean;
import com.example.dev_200_1_network_client_data_storage_system_jpa.service.AddressService;
import com.example.dev_200_1_network_client_data_storage_system_jpa.service.ClientService;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@WebServlet(name = "Update", value = "/Update")
public class Update extends HttpServlet {

    @EJB
    private ReturningErrors returningErrors;
    @EJB
    private AuthorizationBean authorizationBean;
    @EJB
    private UpdateBean updateBean;
    @EJB
    private AddressService addressService;
    @EJB
    private ClientService clientService;

    private String strId;
    private String macAdr;

    private void getHead(PrintWriter out) {
        out.println("<html><head>");
        out.println("<title>Update form</title>");
        out.println("</head><body>");
    }

    private void getBody(PrintWriter out, HttpServletRequest request) {
        Object obj1 = request.getSession().getAttribute("Error_field");
        Object obj2 = request.getSession().getAttribute("Error_reason");
        if (obj1 instanceof String || obj2 instanceof String) {
            String message1 = (String) request.getSession().getAttribute("Error_field");
            String message2 = (String) request.getSession().getAttribute("Error_reason");
            out.println("<h3 style=\"color: brown\">" + message1 + message2 + "</h3>");
            request.getSession().removeAttribute("Error_field");
            request.getSession().removeAttribute("Error_reason");
        }

        out.println("<form action=\"Update\" method=\"post\">");
        out.println("<div style=\"display: flex; flex-direction: column\">");
        out.println("<div style=\"margin: 15px; padding: 15px; border: 1px solid black; border-radius: 15px;\">");
    }

    private void getPageUpdateClientData(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        getHead(out);
        out.println("<h1 style=\"text-align: center\">Обновление данных о клиенте</h1>");
        getBody(out, request);
        out.println("<h2>Введите информацию о клиенте</h2><div>");
        ClientDto cli = clientService.findByClientId(Integer.parseInt(strId));
        out.println("наименование клиента: <input name=\"clientName\" value=\"" + cli.getClientName() + "\"><br>");
        out.println("тип клиента: <select name=\"clientType\">");
        out.println("<option>--> Выберите тип <--</option>");
        if (cli.getType().equals("Юридическое лицо")) {
            out.println("<option selected=\"selected\">Юридическое лицо</option>");
        } else {
            out.println("<option>Юридическое лицо</option>");
        }
        if (cli.getType().equals("Физическое лицо")) {
            out.println("<option selected=\"selected\">Физическое лицо</option>");
        } else {
            out.println("<option>Физическое лицо</option>");
        }
        out.println("</select><br><br>");
        out.println("<button type=\"submit\" name=\"UpdateDataClient\">Изменить данные клиента</button><br><br>");
        out.println("<button type=\"reset\">Очистить введенные данные</button></div></div>");
        out.println("</form></body></html>");
    }

    private void getPageUpdateAddressData(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        getHead(out);
        out.println("<h1 style=\"text-align: center\">Обновление данных о адресе</h1>");
        getBody(out, request);
        out.println("<h2>Введите информацию об адресе клиента</h2><div>");
        AddressDto addr = addressService.findByAddressMac(macAdr);
        out.println("сетевой адрес устройства (IP): <input name=\"clientIp\" value=\"" + addr.getIp() + "\"><br>");
        out.println("физический адрес устройства (MAC): <input name=\"clientMac\" value=\"" + addr.getMac() + "\"><br>");
        out.println("модель устройства:<input name=\"clientModel\" value= \"" + addr.getModel() + "\"><br>");
        out.println("адрес места нахождения:<input name=\"clientAddress\" value=\"" + addr.getAddress() + "\"><br>");
        out.println("</div></div></div>");
        out.println("<button type=\"submit\" name=\"UpdateClientAddress\">Обновить адрес</button><br><br>");
        out.println("<button type=\"reset\">Очистить введенные данные</button>");
        out.println("</form></body></html>");
    }


    private void getPageAddAddress(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        getHead(out);
        out.println("<h1 style=\"text-align: center\">Добавление адреса клиенту</h1>");
        getBody(out, request);
        out.println("<h2>Введите информацию об адресе клиента</h2><div>");
        out.println("сетевой адрес устройства (IP): <input name=\"clientIp\"><br>");
        out.println("физический адрес устройства (MAC): <input name=\"clientMac\"><br>");
        out.println("модель устройства:<input name=\"clientModel\"><br>");
        out.println("адрес места нахождения:<input name=\"clientAddress\"><br>");
        out.println("</div></div></div>");
        out.println("<button type=\"submit\" name=\"CreateClientAddress\">Добавить адрес</button><br><br>");
        out.println("<button type=\"reset\">Очистить введенные данные</button>");
        out.println("</form></body></html>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        strId = Objects.toString(request.getParameter("client-id"), "");
        macAdr = Objects.toString(request.getParameter("mac"), "");
        HttpSession session = request.getSession();
        String savedSessionId = Objects.toString(authorizationBean.getSessionId((String) session.getAttribute("login")), "");
        String currentSessionId = session.getId();
        if (savedSessionId.equals(currentSessionId)) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            if (request.getParameter("name").equals("UpdateClient")) {
                getPageUpdateClientData(response.getWriter(), request, response);
            }
            if (request.getParameter("name").equals("CreateAddress")) {
                getPageAddAddress(response.getWriter(), request, response);
            }
            if (request.getParameter("name").equals("UpdateAddress")) {
                getPageUpdateAddressData(response.getWriter(), request, response);
            }
        } else {
            response.sendRedirect("Authorization");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (request.getParameter("UpdateDataClient") != null) {
            String name = Objects.toString(request.getParameter("clientName"), "");
            String type = Objects.toString(request.getParameter("clientType"), "");
            if (!updateBean.updateClient(strId, name, type)) {
                request.getSession().setAttribute("Error_field", returningErrors.getErrorFieldType());
                request.getSession().setAttribute("Error_reason", returningErrors.getErrorReasonType());
                response.sendRedirect("http://localhost:8080/DEV_200_1_Network_Client_Data_storage_System_JPA-1.0-SNAPSHOT/Update?client-id=" + strId + "&name=UpdateClient");
            } else response.sendRedirect("ViewList");
        }
        if (request.getParameter("UpdateClientAddress") != null) {
            String newMac = Objects.toString(request.getParameter("clientMac"), "");
            String ip = Objects.toString(request.getParameter("clientIp"), "");
            String model = Objects.toString(request.getParameter("clientModel"), "");
            String address = Objects.toString(request.getParameter("clientAddress"), "");
            if (!updateBean.updateAddress(strId, macAdr, newMac, ip, model, address)) {
                request.getSession().setAttribute("Error_field", returningErrors.getErrorFieldType());
                request.getSession().setAttribute("Error_reason", returningErrors.getErrorReasonType());
                response.sendRedirect("http://localhost:8080/DEV_200_1_Network_Client_Data_storage_System_JPA-1.0-SNAPSHOT/Update?client-id=" + strId + "&name=UpdateAddress&mac=" + macAdr);
            } else response.sendRedirect("ViewList");
        }
        if (request.getParameter("CreateClientAddress") != null) {
            String ip = Objects.toString(request.getParameter("clientIp"), "");
            String mac = Objects.toString(request.getParameter("clientMac"), "");
            String model = Objects.toString(request.getParameter("clientModel"), "");
            String address = Objects.toString(request.getParameter("clientAddress"), "");
            if (!updateBean.createAddress(strId, ip, mac, model, address)) {
                request.getSession().setAttribute("Error_field", returningErrors.getErrorFieldType());
                request.getSession().setAttribute("Error_reason", returningErrors.getErrorReasonType());
                response.sendRedirect("http://localhost:8080/DEV_200_1_Network_Client_Data_storage_System_JPA-1.0-SNAPSHOT/Update?client-id=" + strId + "&name=CreateAddress");
            } else response.sendRedirect("ViewList");
        }
    }
}