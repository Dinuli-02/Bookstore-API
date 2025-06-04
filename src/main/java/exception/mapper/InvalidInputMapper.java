/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception.mapper;

import exception.InvalidInputException;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;

@Provider
public class InvalidInputMapper implements ExceptionMapper<InvalidInputException> {

    @Override
    public Response toResponse(InvalidInputException ex) {
        // Create the error message to send in the response
        ErrorMessage errorMessage = new ErrorMessage("Invalid Input", ex.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    // ErrorMessage class to structure the response
    public static class ErrorMessage {
        private String error;
        private String message;

        public ErrorMessage(String error, String message) {
            this.error = error;
            this.message = message;
        }

        // Getters for the error and message
        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }
}
