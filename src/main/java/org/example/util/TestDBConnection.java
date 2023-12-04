package org.example.util;

import org.example.dao.BookDAO;
import org.example.model.Book;

import java.sql.Connection;
import java.util.List;

public class TestDBConnection {
    public static void main(String[] args) {
        Connection connection = DBConnection.openConnection();
        System.out.println("Connected");

//        db CRUD operations
//        create
        Book book5 = new Book(5,"Book 5", "Author 5", 59.99, 50);
        BookDAO.insertBook(connection, book5);
        System.out.println("Insertion completed");

//        read
        List<Book> allBooks = BookDAO.getAllBooks(connection);
        for (Book book : allBooks) {
            System.out.println(book);
        }

        int bookId = 20;
        Book bookById = BookDAO.getBookById(connection, bookId);
        System.out.println(bookById);

//        update
        int bookIdToUpdate = 18;
        Book updatedBook = new Book(bookIdToUpdate, "New Book 5", "New Author 5", 5.99, 500 );
        BookDAO.updateBook(connection, bookIdToUpdate, updatedBook);

//        delete
        BookDAO.deleteBookById(connection, bookId);

        DBConnection.closeConnection();
        System.out.println("Connection closed");
    }
}
