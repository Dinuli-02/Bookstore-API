/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resource;

import model.Order;
import service.DataStore;
import exception.CustomerNotFoundException;
import exception.OrderNotFoundException;
import exception.ErrorMessage;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    // Create a new order for a specific customer
    @POST
    public Response createOrder(@PathParam("customerId") int customerId, Order order) {
        // Validate if the customer exists
        if (!DataStore.customers.containsKey(customerId)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage("Customer Not Found", "Customer with ID " + customerId + " does not exist."))
                    .build();
        }

        // Set customer ID for the order
        order.setCustomerId(customerId);

        // Assign ID and store order
        int id = DataStore.orderIdCounter.getAndIncrement();
        order.setId(id);
        order.setOrderDate(new Date()); // Set current date
        DataStore.orders.put(id, order);

        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    // Get all orders for a specific customer
    @GET
    public Response getOrdersByCustomerId(@PathParam("customerId") int customerId) {
        // Validate if the customer exists
        if (!DataStore.customers.containsKey(customerId)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage("Customer Not Found", "Customer with ID " + customerId + " does not exist."))
                    .build();
        }

        // Filter orders for the specific customer
        List<Order> customerOrders = new ArrayList<>();
        for (Order order : DataStore.orders.values()) {
            if (order.getCustomerId() == customerId) {
                customerOrders.add(order);
            }
        }

        if (customerOrders.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage("No Orders Found", "No orders found for customer with ID " + customerId))
                    .build();
        }

        return Response.ok(customerOrders).build();
    }

    // Get a specific order for a specific customer by orderId
    @GET
    @Path("/{orderId}")
    public Response getOrderById(@PathParam("customerId") int customerId, @PathParam("orderId") int orderId) {
        // Validate if the customer exists
        if (!DataStore.customers.containsKey(customerId)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage("Customer Not Found", "Customer with ID " + customerId + " does not exist."))
                    .build();
        }

        // Retrieve the order for the specific customer
        Order order = DataStore.orders.get(orderId);
        if (order == null || order.getCustomerId() != customerId) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage("Order Not Found", "Order with ID " + orderId + " not found for customer with ID " + customerId))
                    .build();
        }

        return Response.ok(order).build();
    }

    // Update an order for a specific customer
    @PUT
    @Path("/{orderId}")
    public Response updateOrder(@PathParam("customerId") int customerId, @PathParam("orderId") int orderId, Order updatedOrder) {
        // Validate if the customer exists
        if (!DataStore.customers.containsKey(customerId)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage("Customer Not Found", "Customer with ID " + customerId + " does not exist."))
                    .build();
        }

        // Retrieve the order and validate it belongs to the customer
        Order existingOrder = DataStore.orders.get(orderId);
        if (existingOrder == null || existingOrder.getCustomerId() != customerId) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage("Order Not Found", "Order with ID " + orderId + " not found for customer with ID " + customerId))
                    .build();
        }

        // Update order details
        updatedOrder.setId(orderId);
        updatedOrder.setCustomerId(customerId);
        updatedOrder.setOrderDate(new Date()); // update the order date to now
        DataStore.orders.put(orderId, updatedOrder);

        return Response.ok(updatedOrder).build();
    }

    // Delete an order for a specific customer
    @DELETE
    @Path("/{orderId}")
    public Response deleteOrder(@PathParam("customerId") int customerId, @PathParam("orderId") int orderId) {
        // Validate if the customer exists
        if (!DataStore.customers.containsKey(customerId)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage("Customer Not Found", "Customer with ID " + customerId + " does not exist."))
                    .build();
        }

        // Retrieve and validate the order belongs to the customer
        Order removedOrder = DataStore.orders.remove(orderId);
        if (removedOrder == null || removedOrder.getCustomerId() != customerId) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage("Order Not Found", "Order with ID " + orderId + " not found for customer with ID " + customerId))
                    .build();
        }

        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
