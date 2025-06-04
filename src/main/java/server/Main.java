/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

/**
 *
 * @author D E L L
 */


import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class Main {
    public static final String BASE_URI = "http://localhost:9090/api/";

    public static HttpServer startServer() {
        final URI baseUri = UriBuilder.fromUri(BASE_URI).build();

        // Basic Jersey ResourceConfig without DI
        final org.glassfish.jersey.server.ResourceConfig config = 
                new org.glassfish.jersey.server.ResourceConfig()
                .packages("resource"); // Ensure this is the correct package for your resource classes
        
        return GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println("Jersey app started at " + BASE_URI);
        System.out.println("Hit enter to stop...");
        System.in.read();
        server.shutdownNow();
    }
}

