package com.example.dev_200_1_network_client_data_storage_system_jpa.repository;

import com.example.dev_200_1_network_client_data_storage_system_jpa.entity.AddressEntity;
import jakarta.ejb.Local;

import java.util.List;

@Local
public interface AddressRepository {
    void create(AddressEntity address);

    void removeEntity(AddressEntity address);

    void remove(String mac);

    void update(String mac, AddressEntity address);

    AddressEntity update(AddressEntity address);

    List<AddressEntity> findAllAddress();

    AddressEntity findByAddressMac(String mac);

    AddressEntity createMerge(AddressEntity address);
}
