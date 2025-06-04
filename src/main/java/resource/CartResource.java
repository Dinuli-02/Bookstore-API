/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resource;

import model.CartItem;
import service.DataStore;
import exception.CartNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
 
import java.util.*;

@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    // Add a cart item for a customer
    @POST
    @Path("/items")
    public Response addCartItem(@PathParam("customerId") int customerId, CartItem cartItem) {
        // Ensure customer has a cart and add cart item to the list
        DataStore.cartItems.putIfAbsent(customerId, new ArrayList<>());
        DataStore.cartItems.get(customerId).add(cartItem);
        return Response.status(Response.Status.CREATED).entity(cartItem).build();
    }

    // Get all cart items for a customer
    @GET
    public Response getCartItems(@PathParam("customerId") int customerId) {
        List<CartItem> items = DataStore.cartItems.get(customerId);
        if (items == null || items.isEmpty()) {
            throw new CartNotFoundException(customerId);
        }
        return Response.ok(items).build();
    }

    // Update a specific cart item for a customer
    @PUT
    @Path("/items/{bookId}")
    public Response updateCartItem(@PathParam("customerId") int customerId, 
                                   @PathParam("bookId") int bookId, 
                                   CartItem updatedCartItem) {
        List<CartItem> items = DataStore.cartItems.get(customerId);
        if (items == null || items.isEmpty()) {
            throw new CartNotFoundException(customerId);
        }

        // Find the cart item to update
        CartItem itemToUpdate = null;
        for (CartItem item : items) {
            if (item.getBookId() == bookId) {
                itemToUpdate = item;
                break;
            }
        }

        if (itemToUpdate == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Item with bookId " + bookId + " not found in the cart.")
                           .build();
        }

        itemToUpdate.setQuantity(updatedCartItem.getQuantity());
        return Response.ok(itemToUpdate).build();
    }

    // Delete a specific cart item for a customer
    @DELETE
    @Path("/items/{bookId}")
    public Response deleteCartItem(@PathParam("customerId") int customerId, 
                                   @PathParam("bookId") int bookId) {
        List<CartItem> items = DataStore.cartItems.get(customerId);
        if (items == null || items.isEmpty()) {
            throw new CartNotFoundException(customerId);
        }

        // Find and remove the cart item
        CartItem itemToRemove = null;
        for (CartItem item : items) {
            if (item.getBookId() == bookId) {
                itemToRemove = item;
                break;
            }
        }

        if (itemToRemove == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Item with bookId " + bookId + " not found in the cart.")
                           .build();
        }

        items.remove(itemToRemove);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}

