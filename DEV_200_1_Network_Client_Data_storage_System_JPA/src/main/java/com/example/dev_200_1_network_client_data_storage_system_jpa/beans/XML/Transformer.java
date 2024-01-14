package com.example.dev_200_1_network_client_data_storage_system_jpa.beans.XML;
import com.example.dev_200_1_network_client_data_storage_system_jpa.entity.AddressEntity;
import com.example.dev_200_1_network_client_data_storage_system_jpa.entity.ClientEntity;
import com.example.dev_200_1_network_client_data_storage_system_jpa.repository.ClientRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lombok.SneakyThrows;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class Transformer {

    @EJB
    private ClientRepository clientRepository;
    @SneakyThrows
    public void createXmlFile(File newFile){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        List<ClientEntity> clients = clientRepository.findAllClient();
        System.out.println(newFile.getAbsolutePath());
        Document document = builder.newDocument();
        Element clientsElement = document.createElement("clients");
        document.appendChild(clientsElement);
        clients.forEach(client -> {
            Element clientElement = document.createElement("client");
            clientElement.setAttribute("clientId", client.getClientId().toString());
            clientElement.setAttribute("client_name", client.getClientName());
            clientElement.setAttribute("client_type", client.getType());
            clientElement.setAttribute("datereg", client.getAdded().toString());
            clientsElement.appendChild(clientElement);
            List<AddressEntity> addresses = new ArrayList<>(client.getAddresses());
            if(addresses!=null && !addresses.isEmpty()){
                addresses.forEach(address -> {
                    Element addressElement = document.createElement("address");
                    addressElement.setAttribute("mac", address.getMac());
                    addressElement.setAttribute("ip", address.getIp());
                    addressElement.setAttribute("model", address.getModel());
                    addressElement.setAttribute("address", address.getAddress());
                    clientElement.appendChild(addressElement);
                });
            }
        });
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(newFile);
        transformer.transform(source, result);
    }
}
