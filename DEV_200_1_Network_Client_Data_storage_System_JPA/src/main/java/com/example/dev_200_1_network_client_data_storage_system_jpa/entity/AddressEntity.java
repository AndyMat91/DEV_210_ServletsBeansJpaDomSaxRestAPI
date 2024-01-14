package com.example.dev_200_1_network_client_data_storage_system_jpa.entity;

import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.AddressDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "address", schema = "client_db")
@Setter
@Getter
public class AddressEntity {

    @Id
    @Column(name = "mac", nullable = false, length = 20)
    private String mac;

    @Column(name = "ip", length = 25)
    private String ip;

    @Column(name = "model", length = 200)
    private String model;

    @Column(name = "address", length = 200)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cl_id")
    private ClientEntity client;


    public static AddressEntity getAddressEntityByDto(AddressDto addressDto) {
        AddressEntity addressEntity = new AddressEntity();
        if (addressDto != null) {
            addressEntity.mac = addressDto.getMac();
            addressEntity.ip = addressDto.getIp();
            addressEntity.model = addressDto.getModel();
            addressEntity.address = addressDto.getAddress();
        } else addressEntity = null;
        return addressEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntity that = (AddressEntity) o;
        return Objects.equals(mac, that.mac);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mac);
    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "mac='" + mac + '\'' +
                ", ip='" + ip + '\'' +
                ", model='" + model + '\'' +
                ", address='" + address +
                '}';
    }
}