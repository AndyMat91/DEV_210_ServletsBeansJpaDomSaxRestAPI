package com.example.dev_200_1_network_client_data_storage_system_jpa.beans;

import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.AddressDto;
import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.ClientAddressDto;
import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.ClientDto;
import com.example.dev_200_1_network_client_data_storage_system_jpa.entity.AddressEntity;
import com.example.dev_200_1_network_client_data_storage_system_jpa.repository.ClientRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;


@Stateless
public class SelectBean {

    @EJB
    private ClientRepository clientRepository;

    public List<ClientAddressDto> findAllClient() {
        List<ClientAddressDto> list = new ArrayList<>();
        clientRepository.findAllClient().forEach(client -> {
            if (client.getAddresses() != null && !client.getAddresses().isEmpty()) {
                client.getAddresses().forEach(clientAdr -> list.add(new ClientAddressDto(ClientDto.getClientDtoByEntity(client), AddressDto.getAddressDtoByEntity(clientAdr))));
            } else {
                list.add(new ClientAddressDto(ClientDto.getClientDtoByEntity(client), null));
            }
        });
        return list;
    }

    public List<ClientAddressDto> findAllClientWithNameOrAddress(String data) {
        List<ClientAddressDto> list = new ArrayList<>();
        clientRepository.findAllClient().forEach(client -> {
            if (client.getAddresses() != null && !client.getAddresses().isEmpty() && data != null && !data.isEmpty()) {
                if (client.getClientName().toLowerCase().contains(data.toLowerCase())) {
                    client.getAddresses().forEach(clientAdr -> list.add
                            (new ClientAddressDto(ClientDto.getClientDtoByEntity(client)
                                    , AddressDto.getAddressDtoByEntity(clientAdr))));
                }  if (client.getAddresses()
                        .stream()
                        .map(AddressEntity::getAddress)
                        .anyMatch(d -> d.toLowerCase().contains(data.toLowerCase()))) {
                    client.getAddresses().forEach(clientAdr -> {
                        if (clientAdr.getAddress().toLowerCase().contains(data.toLowerCase())) {
                            list.add(new ClientAddressDto(ClientDto.getClientDtoByEntity(client)
                                    , AddressDto.getAddressDtoByEntity(clientAdr)));
                        }
                    });
                }
            } else {
                client.getAddresses().forEach(clientAdr -> list.add(new ClientAddressDto(ClientDto.getClientDtoByEntity(client),
                        AddressDto.getAddressDtoByEntity(clientAdr))));
            }
        });
        return list;
    }

    public List<ClientAddressDto> findAllClientWithType(String type, List<ClientAddressDto> list) {
        List<ClientAddressDto> newList = new ArrayList<>();
        if (type != null && !type.isEmpty() && !type.equals("--> Выберите тип <--")) {
            List<ClientAddressDto> finalNewList = newList;
            list.forEach(client -> {
                if (client.getType().equals(type)) {
                    finalNewList.add(client);
                }
            });
        } else newList = list;
        return newList;
    }
}

