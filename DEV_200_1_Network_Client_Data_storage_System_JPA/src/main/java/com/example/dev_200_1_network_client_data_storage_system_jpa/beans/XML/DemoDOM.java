package com.example.dev_200_1_network_client_data_storage_system_jpa.beans.XML;

import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.AddressDto;
import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.ClientAddressDto;
import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.ClientDto;
import jakarta.ejb.Stateless;
import lombok.Setter;
import lombok.SneakyThrows;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class DemoDOM {

    @Setter
    private String param;

    @SneakyThrows
    public List<ClientAddressDto> readFileXml(File file){
        List<ClientAddressDto> clients = new LinkedList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        NodeList list = document.getElementsByTagName("clients");
        Node clientsTag = list.item(0);
        NodeList clientsTagChildNodes = clientsTag.getChildNodes();
        ClientDto client = new ClientDto();
        AddressDto address = new AddressDto();
        if (param.equals("all") || param.isEmpty()) {
            for (int i = 0; i < clientsTagChildNodes.getLength(); i++) {
                Node clientTag = clientsTagChildNodes.item(i);
                if (clientTag.getNodeName().equals("client")) {
                    NamedNodeMap clientTagAttributes = clientTag.getAttributes();
                    read(clientTagAttributes,client,address,clients,clientTag);
                }
            }
        } else {
            for (int i = 0; i < clientsTagChildNodes.getLength(); i++) {
                Node clientTag = clientsTagChildNodes.item(i);
                if (clientTag.getNodeName().equals("client")) {
                    NamedNodeMap clientTagAttributes = clientTag.getAttributes();
                    if (clientTagAttributes.getNamedItem("client_name").getNodeValue().equals(param)) {
                        read(clientTagAttributes,client,address,clients,clientTag);
                    }
                }
            }
        }
        return clients;
    }
    private void read (NamedNodeMap clientTagAttributes, ClientDto client, AddressDto address, List<ClientAddressDto> clients, Node clientTag){
        client.setClientId(Integer.parseInt(clientTagAttributes.getNamedItem("clientId").getNodeValue()));
        client.setClientName(clientTagAttributes.getNamedItem("client_name").getNodeValue());
        client.setType(clientTagAttributes.getNamedItem("client_type").getNodeValue());
        client.setAdded(Instant.parse(clientTagAttributes.getNamedItem("datereg").getNodeValue()));
        NodeList clientNodeList = clientTag.getChildNodes();
        for (int ii = 0; ii < clientNodeList.getLength(); ii++) {
            Node addressTag = clientNodeList.item(ii);
            if (addressTag.getNodeName().equals("address")) {
                NamedNodeMap addressTagAttributes = addressTag.getAttributes();
                address.setMac(addressTagAttributes.getNamedItem("mac").getNodeValue());
                address.setIp(addressTagAttributes.getNamedItem("ip").getNodeValue());
                address.setModel(addressTagAttributes.getNamedItem("model").getNodeValue());
                address.setAddress(addressTagAttributes.getNamedItem("address").getNodeValue());
                client.getAddresses().add(address);
                clients.add(new ClientAddressDto(client, address));
            }
        }
    }
}
