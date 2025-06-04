/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package resource;

import model.Book;
import service.DataStore;
import exception.BookNotFoundException;
import exception.InvalidInputException;
import exception.ErrorMessage;

import javax.ws.rs.*; // for @Path, @GET, @POST, etc.
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;

import javax.ws.rs.core.*;
import java.util.*;

import java.time.Year;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

     @POST
    public Response createBook(Book book) {
        // Validate Author ID
        if (!DataStore.authors.containsKey(book.getAuthorId())) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity(new ErrorMessage("Author Not Found", "Author with ID " + book.getAuthorId() + " does not exist."))
                           .build();
        }

        // Validate Publication Year
        int currentYear = Year.now().getValue();
        if (book.getPublicationYear() > currentYear) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(new ErrorMessage("Invalid Input", "Publication year cannot be in the future."))
                           .build();
        }

        // Set ID and store the book
        int id = DataStore.bookIdCounter.getAndIncrement();
        book.setId(id);
        DataStore.books.put(id, book);
        
        // Return success response
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @GET
    public Response getAllBooks() {
        return Response.ok(new ArrayList<>(DataStore.books.values())).build();
    }

    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") int id) {
        Book book = DataStore.books.get(id);
        if (book == null) {
            throw new BookNotFoundException(id);
        }
        return Response.ok(book).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") int id, Book updatedBook) {
        Book book = DataStore.books.get(id);
        if (book == null) {
            throw new BookNotFoundException(id);
        }
        updatedBook.setId(id);
        DataStore.books.put(id, updatedBook);
        return Response.ok(updatedBook).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        Book removed = DataStore.books.remove(id);
        if (removed == null) {
            throw new BookNotFoundException(id);
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}


