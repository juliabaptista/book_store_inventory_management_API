package org.example.util;

import org.example.dao.BookDAO;
import org.example.model.Book;

import java.sql.Connection;

public class TestDBConnection {
    public static void main(String[] args) {
        Connection connection = DBConnection.openConnection();

//        db CRUD operations
        Book book5 = new Book(5,"Book 5", "Author 5", 59.99, 50);
        BookDAO.insertBook(connection, book5);
        System.out.println("Insertion completed");

//        DBConnection.closeConnection();
    }
}
