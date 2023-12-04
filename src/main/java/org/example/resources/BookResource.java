package org.example.resources;

import org.example.dao.BookDAO;
import org.example.model.Book;
import org.example.util.DBConnection;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.util.List;

@Path("/books")
public class BookResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        try (Connection connection = DBConnection.openConnection()) {
            List<Book> books = BookDAO.getAllBooks(connection);
            return Response.ok(books).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving books").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById(@PathParam("id") int id) {
        try (Connection connection = DBConnection.openConnection()) {
            Book book = BookDAO.getBookById(connection, id);
            if (book != null) {
                return Response.ok(book).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Book not found").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving book").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(Book book) {
        try (Connection connection = DBConnection.openConnection()) {
            BookDAO.insertBook(connection, book);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error adding book").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") int id, Book updatedBook) {
        try (Connection connection = DBConnection.openConnection()) {
            boolean updated = BookDAO.updateBook(connection, id, updatedBook);
            if (updated) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Book not found").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating book").build();
        }
    }
}
