package com.example.dev_200_1_network_client_data_storage_system_jpa.repository;

import com.example.dev_200_1_network_client_data_storage_system_jpa.entity.ClientEntity;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Singleton
public class ClientRepositoryImpl implements ClientRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(ClientEntity client) {
        em.persist(client);
    }

    @Override
    public ClientEntity createMerge(ClientEntity client){
        return em.merge(client);
    }

    @Override
    public void remove(ClientEntity client) {
        remove(client.getClientId());
    }

    @Override
    public void remove(int id) {
        ClientEntity client = findByClientId(id);
        em.remove(client);
    }

    @Override
    public void update(int clientId, String name, String type) {
        ClientEntity client = findByClientId(clientId);
        client.setClientName(name);
        client.setType(type);
        em.merge(client);
    }

    @Override
    public ClientEntity update(ClientEntity client) {
        return em.merge(client);
    }

    @Override
    public List<ClientEntity> findAllClient() {
        return em.createNativeQuery("select * from client_db.clients", ClientEntity.class).getResultList();
    }

    @Override
    public ClientEntity findByClientId(int id) {
        return em.find(ClientEntity.class, id);
    }

}
