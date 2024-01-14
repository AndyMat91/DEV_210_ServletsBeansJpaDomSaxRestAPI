package com.example.dev_200_1_network_client_data_storage_system_jpa.entity;

import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.ClientDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "clients", schema = "client_db")
@Setter
@Getter
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false)
    private Integer clientId;

    @Column(name = "client_name", length = 100)
    private String clientName;

    @Column(name = "client_type", length = 20)
    private String type;

    @Column(name = "datereg")
    private Instant added;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<AddressEntity> addresses = new LinkedHashSet<>();


    public static ClientEntity getClientEntityByDto(ClientDto clientDto) {
        ClientEntity clientEntity = new ClientEntity();
        if (clientDto != null) {
            clientEntity.clientId = clientDto.getClientId();
            clientEntity.clientName = clientDto.getClientName();
            clientEntity.type = clientDto.getType();
            clientEntity.added = clientDto.getAdded();
        } else clientEntity = null;
        return clientEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientEntity client = (ClientEntity) o;
        return Objects.equals(clientId, client.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId);
    }

    @Override
    public String toString() {
        return "ClientEntity{" +
                "clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", type='" + type + '\'' +
                ", added=" + added +
                ", addresses=" + addresses +
                '}';
    }
}