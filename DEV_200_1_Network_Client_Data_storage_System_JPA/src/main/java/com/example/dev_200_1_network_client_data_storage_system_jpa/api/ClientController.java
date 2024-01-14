package com.example.dev_200_1_network_client_data_storage_system_jpa.api;

import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.ClientDto;
import com.example.dev_200_1_network_client_data_storage_system_jpa.service.ClientService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.*;

import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/client")
public class ClientController {

    @EJB
    private ClientService clientService;

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public ClientDto getClient(@PathParam("id") int id){
        return clientService.findByClientId(id);
    }

    @GET
    @Produces("application/json")
    public List<ClientDto> getAllClient(){
        return new ArrayList<>(clientService.findAll());
    }

    @PUT
    @Consumes("application/json")
    public void updateClient(ClientDto client){
        clientService.updateClient(client);
    }

    @DELETE
    @Path("/{id}")
    public void deleteClient(@PathParam("id") int id){
        clientService.delete(id);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public ClientDto create(ClientDto client){
        return clientService.create(client);
    }
}
