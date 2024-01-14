package com.example.dev_200_1_network_client_data_storage_system_jpa.service;

import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.AddressDto;
import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.ClientDto;
import com.example.dev_200_1_network_client_data_storage_system_jpa.entity.AddressEntity;
import com.example.dev_200_1_network_client_data_storage_system_jpa.repository.AddressRepository;
import com.example.dev_200_1_network_client_data_storage_system_jpa.repository.ClientRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
public class AddressService {
    @EJB
    private AddressRepository addressRepository;

    @EJB
    private ClientRepository clientRepository;

    public AddressDto findByAddressMac(String mac) {
        return AddressDto.getAddressDtoByEntity(addressRepository.findByAddressMac(mac));
    }

    public AddressEntity update(AddressDto dto){
        AddressEntity entity = addressRepository.findByAddressMac(dto.getMac());
        AddressEntity temp = AddressDto.getEntityByAddressDto(dto);
        entity.setAddress(temp.getAddress());
        entity.setModel(temp.getModel());
        entity.setIp(temp.getIp());
        return addressRepository.update(entity);
    }

    public Set<AddressDto> findAll(){
        List<AddressEntity> entities = addressRepository.findAllAddress();
        return entities.stream()
                .map(AddressDto::getAddressDtoByEntity)
                .collect(Collectors.toSet());
    }

    public void updateAddress(AddressDto address){
        AddressEntity entity = addressRepository.findByAddressMac(address.getMac());
        AddressEntity temp = AddressDto.getEntityByAddressDto(address);
        entity.setIp(temp.getIp());
        entity.setModel(temp.getModel());
        entity.setAddress(temp.getAddress());
    }

    public void delete(String mac){
        addressRepository.remove(mac);
    }

    public AddressDto create(AddressDto dto){
        dto.setClient(ClientDto.getClientDtoByEntity(clientRepository.findByClientId(dto.getClId())));
        AddressEntity entity = addressRepository.createMerge(AddressDto.getEntityByAddressDto(dto));
        return AddressDto.getAddressDtoByEntity(entity);
    }
}
