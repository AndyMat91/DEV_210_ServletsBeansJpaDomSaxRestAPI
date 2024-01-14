package com.example.dev_200_1_network_client_data_storage_system_jpa.beans.XML;

import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.AddressDto;
import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.ClientAddressDto;
import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.ClientDto;
import com.example.dev_200_1_network_client_data_storage_system_jpa.storage.AppStore;
import jakarta.ejb.Stateless;
import lombok.Setter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class DemoSAX extends DefaultHandler {
    private List<ClientAddressDto> clients;
    private ClientDto client;
    private AddressDto address;

    @Setter
    private String param;

    @Override
    public void startDocument() throws SAXException {
        clients = new LinkedList<>();
    }

    @Override
    public void endDocument() throws SAXException {
        AppStore.clients=clients;
        clients = new LinkedList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        param = param != null ? (param.equals("all") ? "" : param) : "";
        param = param.trim();
        switch (qName) {
            case "client" -> {
                String clientName = attributes.getValue("client_name");
                if (clientName.toLowerCase().contains(param.toLowerCase())) {
                    client(attributes);
                }
            }
            case "address" -> {
                if (client != null) {
                    address(attributes);
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("client")) {
            client = null;
        }
        if (qName.equals("address")) {
            address = null;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
    }

    private void address(Attributes attributes) {
        address = new AddressDto();
        address.setMac(attributes.getValue("mac"));
        address.setIp(attributes.getValue("ip"));
        address.setModel(attributes.getValue("model"));
        address.setAddress(attributes.getValue("address"));
        client.getAddresses().add(address);
        clients.add(new ClientAddressDto(client, address));
    }

    private void client(Attributes attributes) {
        client = new ClientDto();
        client.setClientId(Integer.parseInt(attributes.getValue("clientId")));
        client.setClientName(attributes.getValue("client_name"));
        client.setType(attributes.getValue("client_type"));
        client.setAdded(Instant.parse(attributes.getValue("datereg")));
    }
}
