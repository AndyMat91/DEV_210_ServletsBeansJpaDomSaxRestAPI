package com.example.dev_200_1_network_client_data_storage_system_jpa.api;

import com.example.dev_200_1_network_client_data_storage_system_jpa.dto.AddressDto;
import com.example.dev_200_1_network_client_data_storage_system_jpa.service.AddressService;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.*;

import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/address")
public class AddressController {

    @EJB
    private AddressService addressService;

    @GET
    @Path("/{mac}")
    @Produces("application/json")
    public AddressDto getAddress(@PathParam("mac") String mac){
        return addressService.findByAddressMac(mac);
    }

    @GET
    @Produces("application/json")
    public List<AddressDto> getAllAddress(){
        return new ArrayList<>(addressService.findAll());
    }

    @PUT
    @Consumes("application/json")
    public void updateAddress(AddressDto address){
        addressService.updateAddress(address);
    }

    @DELETE
    @Path("/{mac}")
    public void deleteAddress(@PathParam("mac") String mac){
        addressService.delete(mac);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public AddressDto create(AddressDto address){
        return addressService.create(address);
    }
}

