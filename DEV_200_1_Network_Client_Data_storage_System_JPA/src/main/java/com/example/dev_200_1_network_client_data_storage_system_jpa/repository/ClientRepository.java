package com.example.dev_200_1_network_client_data_storage_system_jpa.repository;

import com.example.dev_200_1_network_client_data_storage_system_jpa.entity.ClientEntity;
import jakarta.ejb.Local;

import java.util.List;

@Local
public interface ClientRepository {
    void create(ClientEntity client);
    public ClientEntity createMerge(ClientEntity client);


    void remove(ClientEntity client);

    void remove(int id);

    void update(int clientId, String name, String type);

    ClientEntity update(ClientEntity client);

    List<ClientEntity> findAllClient();

    ClientEntity findByClientId(int id);
}
