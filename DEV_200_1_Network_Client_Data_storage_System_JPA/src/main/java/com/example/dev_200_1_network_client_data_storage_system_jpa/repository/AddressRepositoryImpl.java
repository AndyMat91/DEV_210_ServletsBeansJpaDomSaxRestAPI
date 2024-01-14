package com.example.dev_200_1_network_client_data_storage_system_jpa.repository;

import com.example.dev_200_1_network_client_data_storage_system_jpa.entity.AddressEntity;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Singleton
public class AddressRepositoryImpl implements AddressRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(AddressEntity address) {
        em.persist(address);
    }

    @Override
    public void removeEntity(AddressEntity address) {
        remove(address.getMac());
    }

    @Override
    public void remove(String mac) {
        AddressEntity address = findByAddressMac(mac);
        em.remove(address);
    }

    @Override
    public AddressEntity update(AddressEntity address) {
        return em.merge(address);
    }

    @Override
    public void update(String mac, AddressEntity address) {
        remove(mac);
        em.merge(address);
    }

    @Override
    public List<AddressEntity> findAllAddress() {
        return em.createNativeQuery("select * from client_db.address", AddressEntity.class).getResultList();
    }

    @Override
    public AddressEntity findByAddressMac(String mac) {
        return em.find(AddressEntity.class, mac);
    }

    @Override
    public AddressEntity createMerge(AddressEntity address){
        return em.merge(address);
    }
}
