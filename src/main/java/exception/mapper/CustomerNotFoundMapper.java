/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception.mapper;

import exception.CustomerNotFoundException;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;

@Provider
public class CustomerNotFoundMapper implements ExceptionMapper<CustomerNotFoundException> {
    public CustomerNotFoundMapper() {
        System.out.println(">>> CustomerNotFoundMapper LOADED");
    }
    
    @Override
    public Response toResponse(CustomerNotFoundException exception) {
        // Build the response with a 404 status and a detailed error message
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorMessage(exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public static class ErrorMessage {
        private String error;
        private String message;

        public ErrorMessage(String message) {
            this.error = "Customer Not Found";
            this.message = message;
        }

        // Getters and Setters
        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
