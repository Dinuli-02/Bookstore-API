/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author D E L L
 */
package exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class CartNotFoundException extends WebApplicationException {
    public CartNotFoundException(int customerId) {
        super(Response.status(Response.Status.NOT_FOUND)
            .entity(createErrorMessage(customerId))
            .type(MediaType.APPLICATION_JSON)
            .build());
    }

    private static Map<String, String> createErrorMessage(int customerId) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Cart Not Found");
        error.put("message", "Cart for Customer ID " + customerId + " not found.");
        return error;
    }
}
