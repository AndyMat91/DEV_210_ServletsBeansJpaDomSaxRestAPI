package com.example.dev_200_1_network_client_data_storage_system_jpa.api;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class RestConf extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(ClientController.class);
        resources.add(AddressController.class);
        return resources;
    }
}
