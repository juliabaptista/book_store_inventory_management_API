package org.example.dao;

import org.example.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    //    query for db operations
    private static final String INSERT_BOOK_SQL = "INSERT INTO books (title, author, price, quantity) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_BOOKS_SQL = "SELECT * FROM books";
    private static final String SELECT_BOOK_BY_ID_SQL = "SELECT * FROM books WHERE id = ?";

    public static void insertBook(Connection connection, Book book) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOK_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setDouble(3, book.getPrice());
            preparedStatement.setInt(4, book.getQuantity());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating book failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Book> getAllBooks(Connection connection) {
        List<Book> books = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_BOOKS_SQL)) {

            while (resultSet.next()) {
                Book book = createBookFromResultSet(resultSet);
                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public static Book getBookById(Connection connection, int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createBookFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Book createBookFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        double price = resultSet.getDouble("price");
        int quantity = resultSet.getInt("quantity");

        return new Book(id, title, author, price, quantity);
    }
}
