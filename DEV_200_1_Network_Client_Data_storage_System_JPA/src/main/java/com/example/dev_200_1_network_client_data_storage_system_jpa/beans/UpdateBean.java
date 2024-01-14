package com.example.dev_200_1_network_client_data_storage_system_jpa.beans;

import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.AddressDto;
import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.ClientDto;
import com.example.dev_200_1_network_client_data_storage_system_jpa.entity.AddressEntity;
import com.example.dev_200_1_network_client_data_storage_system_jpa.entity.ClientEntity;
import com.example.dev_200_1_network_client_data_storage_system_jpa.repository.AddressRepository;
import com.example.dev_200_1_network_client_data_storage_system_jpa.repository.ClientRepository;
import com.example.dev_200_1_network_client_data_storage_system_jpa.service.ClientService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.servlet.ServletException;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

@Stateless
public class UpdateBean {

    @EJB
    private ClientRepository clientRepository;
    @EJB
    private AddressRepository addressRepository;

    @EJB
    private ValidatorBean validatorBean;

    @EJB
    private ClientService clientService;

    public void deleteClient(int clientId) {
        ClientDto client = clientService.findByClientId(clientId);
        if (client != null) {
            clientRepository.remove(clientId);

        }
    }


    public boolean updateClient(String clientId, String name, String type) throws ServletException, IOException {
        boolean validateClienResult = validatorBean.validateClientList(name, type);
        if (validateClienResult) {
            int id = Integer.parseInt(clientId);
            clientRepository.update(id, name, type);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean updateAddress(String clientId, String mac, String newMac, String ip, String model, String address) throws ServletException, IOException {
        boolean validateClienResult;
        if (mac.equals(newMac)) {
            validateClienResult = validatorBean.validateAddressListNotMac(ip, model, address);
        } else {
            validateClienResult = validatorBean.validateAddressListWithMac(ip, newMac, model, address);
        }
        if (validateClienResult) {
            ClientEntity clientEntity = clientRepository.findByClientId(Integer.parseInt(clientId));
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setMac(newMac);
            addressEntity.setIp(ip);
            addressEntity.setModel(model);
            addressEntity.setAddress(address);
            Set<AddressEntity> list = clientEntity.getAddresses();
            list.removeIf(o -> o.getMac().equals(mac));
            list.add(addressEntity);
            addressEntity.setClient(clientEntity);
            addressRepository.update(mac, addressEntity);
            return true;
        } else {
            return false;
        }
    }

    public void deleteAddress(int id, String mac) {
        ClientDto client = clientService.findByClientId(id);
        Set<AddressDto> list = client.getAddresses();
        if (list.size() == 1) {
            clientRepository.remove(id);
        } else {
            addressRepository.remove(mac);
        }
    }

    @Transactional
    public boolean createClient(String name, String type, String ip, String mac, String model, String address) throws ServletException, IOException {
        boolean validateClienResult = validatorBean.validateClientList(name, type);
        boolean validateAddressResult = validatorBean.validateAddressListWithMac(ip, mac, model, address);
        if (validateClienResult && validateAddressResult) {
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setIp(ip);
            addressEntity.setMac(mac);
            addressEntity.setModel(model);
            addressEntity.setAddress(address);
            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setClientName(name);
            clientEntity.setType(type);
            clientEntity.setAdded(Instant.now());
            addressEntity.setClient(clientEntity);
            clientEntity.setAddresses(new LinkedHashSet<>(Collections.singletonList(addressEntity)));
            clientRepository.create(clientEntity);
            addressRepository.create(addressEntity);
            return true;
        } else {
            return false;
        }
    }

    public boolean createAddress(String id, String ip, String mac, String model, String address) throws ServletException, IOException {
        boolean validateAddressResult = validatorBean.validateAddressListWithMac(ip, mac, model, address);
        if (validateAddressResult) {
            ClientEntity clientEntity = clientRepository.findByClientId(Integer.parseInt(id));
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setIp(ip);
            addressEntity.setMac(mac);
            addressEntity.setModel(model);
            addressEntity.setAddress(address);
            Set<AddressEntity> list = clientEntity.getAddresses();
            addressEntity.setClient(clientEntity);
            list.add(addressEntity);
            clientEntity.setAddresses(list);
            addressRepository.create(addressEntity);
            return true;
        } else {
            return false;
        }
    }
}
