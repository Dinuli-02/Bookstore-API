/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resource;

import model.Customer;
import service.DataStore;
import exception.CustomerNotFoundException;
import exception.InvalidInputException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @POST
    public Response createCustomer(Customer customer) {
        int id = DataStore.customerIdCounter.getAndIncrement();
        customer.setId(id);
        DataStore.customers.put(id, customer);
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    @GET
    public Response getAllCustomers() {
        return Response.ok(new ArrayList<>(DataStore.customers.values())).build();
    }

    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") int id) {
        if (id <= 0) {
        throw new InvalidInputException("Customer ID must be greater than 0");
        }
        Customer customer = DataStore.customers.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException(id);  // Handle invalid customer ID
        }
        return Response.ok(customer).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") int id, Customer updatedCustomer) {
        if (!DataStore.customers.containsKey(id)) {
            throw new CustomerNotFoundException(id);  // Handle invalid customer ID
        }
        updatedCustomer.setId(id);
        DataStore.customers.put(id, updatedCustomer);
        return Response.ok(updatedCustomer).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        Customer removed = DataStore.customers.remove(id);
        if (removed == null) {
            throw new CustomerNotFoundException(id);  // Handle invalid customer ID
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}

