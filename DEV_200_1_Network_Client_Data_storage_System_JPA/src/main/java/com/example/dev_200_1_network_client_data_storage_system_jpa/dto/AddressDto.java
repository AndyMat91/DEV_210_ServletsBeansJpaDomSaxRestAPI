package com.example.dev_200_1_network_client_data_storage_system_jpa.dto;

import com.example.dev_200_1_network_client_data_storage_system_jpa.entity.AddressEntity;
import com.example.dev_200_1_network_client_data_storage_system_jpa.entity.ClientEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class AddressDto {
    private String mac;
    private String ip;
    private String model;
    private String address;
    private Integer clId;
    private ClientDto client;

    public AddressDto(String mac, String ip, String model, String address, ClientDto client) {
        this.mac = mac;
        this.ip = ip;
        this.model = model;
        this.address = address;
        this.client = client;
    }

    public AddressDto(String mac, String ip, String model, String address, ClientDto client, Integer clId) {
        this.mac = mac;
        this.ip = ip;
        this.model = model;
        this.address = address;
        this.client = client;
        this.clId = clId;
    }

    public AddressDto(String mac, String ip, String model, String address) {
        this.mac = mac;
        this.ip = ip;
        this.model = model;
        this.address = address;
    }

    public AddressDto() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDto that = (AddressDto) o;
        return Objects.equals(mac, that.mac);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mac);
    }

    public static AddressDto getAddressDtoByEntity(AddressEntity addressEntity) {
        AddressDto addressDto = new AddressDto();
        if (addressEntity != null) {
            addressDto.mac = addressEntity.getMac();
            addressDto.ip = addressEntity.getIp();
            addressDto.model = addressEntity.getModel();
            addressDto.address = addressEntity.getAddress();
        } else addressDto = null;
        return addressDto;
    }

    public static AddressEntity getEntityByAddressDto(AddressDto dto){
        AddressEntity entity = new AddressEntity();
        if (dto != null) {
            entity.setIp(dto.getIp());
            entity.setMac(dto.getMac());
            entity.setAddress(dto.getAddress());
            entity.setModel(dto.getModel());
            entity.setClient(ClientEntity.getClientEntityByDto(dto.getClient()));
        } else entity = null;
        return entity;
    }

    @Override
    public String toString() {
        return "AddressDto{" +
                "mac='" + mac + '\'' +
                ", ip='" + ip + '\'' +
                ", model='" + model + '\'' +
                ", address='" + address +
                '}';
    }
}
