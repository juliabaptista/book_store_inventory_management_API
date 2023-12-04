package org.example;

import org.example.model.Book;
import org.example.resources.BookResource;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Application {
    public void run() {
        String BASE_URI = "http://localhost:8080/";
        ResourceConfig resourceConfig = new ResourceConfig(BookResource.class);
        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig);
        System.out.println("Server started at: " + BASE_URI);
    }
}
