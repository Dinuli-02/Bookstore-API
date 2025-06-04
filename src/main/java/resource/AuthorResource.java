/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resource;
        
import model.Author;
import model.Book;
import service.DataStore;
import exception.AuthorNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

/**
 *
 * @author D E L L
 */
public class AuthorResource {
    @POST
    public Response createAuthor(Author author) {
        int id = DataStore.authorIdCounter.getAndIncrement();
        author.setId(id);
        DataStore.authors.put(id, author);
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    @GET
    public Response getAllAuthors() {
        return Response.ok(new ArrayList<>(DataStore.authors.values())).build();
    }

    @GET
    @Path("/{id}")
    public Response getAuthorById(@PathParam("id") int id) {
        Author author = DataStore.authors.get(id);
        if (author == null) {
            throw new AuthorNotFoundException(id);
        }
        return Response.ok(author).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") int id, Author updatedAuthor) {
        if (!DataStore.authors.containsKey(id)) {
            throw new AuthorNotFoundException(id);
        }
        updatedAuthor.setId(id);
        DataStore.authors.put(id, updatedAuthor);
        return Response.ok(updatedAuthor).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        Author removed = DataStore.authors.remove(id);
        if (removed == null) {
            throw new AuthorNotFoundException(id);
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{id}/books")
    public Response getBooksByAuthor(@PathParam("id") int id) {
        if (!DataStore.authors.containsKey(id)) {
            throw new AuthorNotFoundException(id);
        }
        List<Book> booksByAuthor = new ArrayList<>();
        for (Book book : DataStore.books.values()) {
            if (book.getAuthorId() == id) {
                booksByAuthor.add(book);
            }
        }
        return Response.ok(booksByAuthor).build();
    }
    
}
