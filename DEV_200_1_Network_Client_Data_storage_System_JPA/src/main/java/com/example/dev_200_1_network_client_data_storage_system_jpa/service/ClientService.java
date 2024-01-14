package com.example.dev_200_1_network_client_data_storage_system_jpa.service;

import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.ClientDto;
import com.example.dev_200_1_network_client_data_storage_system_jpa.entity.AddressEntity;
import com.example.dev_200_1_network_client_data_storage_system_jpa.entity.ClientEntity;
import com.example.dev_200_1_network_client_data_storage_system_jpa.repository.ClientRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
public class ClientService {
    @EJB
    private ClientRepository clientRepository;
    @EJB
    private AddressService addressService;

    @Transactional
    public ClientDto findByClientId(int id){
        return ClientDto.getClientDtoByEntity(clientRepository.findByClientId(id));
    }

    public Set<ClientDto> findAll(){
        List<ClientEntity> entities = clientRepository.findAllClient();
        return entities.stream()
                .map(ClientDto::getClientDtoByEntity)
                .collect(Collectors.toSet());
    }

    public void updateClient(ClientDto client){
        ClientEntity entity = clientRepository.findByClientId(client.getClientId());
        ClientEntity temp = ClientDto.getEntityByClientDto(client);
        entity.setClientName(temp.getClientName());
        entity.setType(temp.getType());
        entity.setAdded(temp.getAdded());
        Set<AddressEntity> addresses = client.getAddresses().stream()
                .filter(e -> e.getMac()!=null)
                .map(e -> addressService.update(e)).collect(Collectors.toSet());
        entity.setAddresses(addresses);
    }

    public void delete(int id){
        clientRepository.remove(id);
    }

    public ClientDto create(ClientDto dto){
        dto.setAdded(Instant.now());
        ClientEntity entity = clientRepository.createMerge(ClientDto.getEntityByClientDto(dto));
        return ClientDto.getClientDtoByEntity(entity);
    }
}
