package org.example.util;

import org.example.model.Book;

import java.sql.Connection;

public class TestDBConnection {
    public static void main(String[] args) {
        Connection connection = DBConnection.openConnection();

//        db CRUD operations
        Book book5 = new Book(5,"Book 5", "Author 5", 59.99, 50);
    }
}