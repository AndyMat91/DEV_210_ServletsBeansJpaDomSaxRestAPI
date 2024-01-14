package com.example.dev_200_1_network_client_data_storage_system_jpa.dto;

import com.example.dev_200_1_network_client_data_storage_system_jpa.entity.ClientEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
public class ClientDto {
    private Integer clientId;
    private String clientName;
    private String type;
    private Instant added;
    private Set<AddressDto> addresses = new LinkedHashSet<>();

    public ClientDto(Integer clientId, String clientName, String type, Instant added, Set<AddressDto> addresses) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.type = type;
        this.added = added;
        this.addresses = addresses;
    }

    public ClientDto(Integer clientId, String clientName, String type, Instant added) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.type = type;
        this.added = added;
    }

    public ClientDto(String clientName, String type, Instant added) {
        this.clientName = clientName;
        this.type = type;
        this.added = added;
    }

    public ClientDto() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDto clientDto = (ClientDto) o;
        return Objects.equals(clientId, clientDto.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId);
    }

    public static ClientDto getClientDtoByEntity(ClientEntity clientEntity) {
        ClientDto clientDto = new ClientDto();
        if (clientEntity != null) {
            clientDto.clientId = clientEntity.getClientId();
            clientDto.clientName = clientEntity.getClientName();
            clientDto.type = clientEntity.getType();
            clientDto.added = clientEntity.getAdded();
            clientDto.addresses = clientEntity.getAddresses()
                    .stream()
                    .map(AddressDto::getAddressDtoByEntity)
                    .collect(Collectors.toSet());
        } else clientDto = null;
        return clientDto;
    }

    public static ClientEntity getEntityByClientDto(ClientDto clientDto) {
        ClientEntity entity = new ClientEntity();
        entity.setClientId(clientDto.getClientId());
        entity.setClientName(clientDto.getClientName());
        entity.setAdded(clientDto.getAdded());
        entity.setType(clientDto.getType());
        entity.setAddresses(clientDto.getAddresses().stream()
                .map(AddressDto::getEntityByAddressDto).collect(Collectors.toSet()));
        return entity;
    }

    @Override
    public String toString() {
        return "ClientDto{" +
                "clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", type='" + type + '\'' +
                ", added=" + added +
                ", addresses=" + addresses +
                '}';
    }
}
