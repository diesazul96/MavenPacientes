package com.dekses.jersey.docker.demo;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.Scanner;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/myapp/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.dekses.jersey.docker.demo package
        final ResourceConfig rc = new ResourceConfig().packages("com.dekses.jersey.docker.demo");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        /*final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();*/
        try {

            Client client = Client.create();
            WebResource webResource = null;
            ClientResponse response = null;
            int opcion = -1;
            Scanner lectura = new Scanner(System.in);
            System.out.println("Bienvenido sistema gestion patients");
         
            do {
                System.out.println("1. Listar 2. Crear. 3. Borrar. 4.  Salir");
                opcion = lectura.nextInt();
                switch (opcion) {
                    case 1:
                        webResource = client.resource("http://localhost:8080/myapp/patient");

                        response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

                        if (response.getStatus() != 200) {
                            throw new RuntimeException("Failed : HTTP error code : "
                                    + response.getStatus());
                        }

                        String output = response.getEntity(String.class);
                        System.out.println("Output from Server .... \n");
                        System.out.println(output);
                        break;

                    case 2:
                        webResource = client
                                .resource("http://localhost:8080/myApp/patient");

                        ObjectMapper mapper = new ObjectMapper();
                        Patient patient = new Patient();
                        System.out.println("Ingrese el id");
                        String codigo = lectura.next();
                        patient.setId(codigo);
                        
                        System.out.println("Ingrese el nombre");
                        String nombre = lectura.next();
                        patient.setNombre(nombre);
                        
                        System.out.println("Ingrese el apellido");
                        String apellido = lectura.next();
                        patient.setApellido(apellido);
                        
                        System.out.println("Ingrese el fecha");
                        String fecha = lectura.next();
                        patient.setFecha(fecha);
                        
                        System.out.println("Ingrese el direccion");
                        String dir = lectura.next();
                        patient.setDireccion(dir);
                        
                        System.out.println("Ingrese el telefono");
                        String tel = lectura.next();
                        patient.setTel(tel);
                        
                        System.out.println("Ingrese el Medinum");
                        String medNum = lectura.next();
                        patient.setMedNum(medNum);
                        
                        System.out.println("Ingrese el status");
                        String status = lectura.next();
                        patient.setStatus(status);
                        
                        String input = mapper.writeValueAsString(patient);
                        //Luego se utilizara Jackson
                        //String input = "{\"empNo\":\"E11\",\"empName\":\"" + nombre + "\",\"position\":\"Salesman\"}";

                        response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, input);

                        if (response.getStatus() != 200) {
                            System.out.println(response.toString());
                            throw new RuntimeException("Failed : HTTP error code : "
                                    + response.getStatus());
                        }

                        output = response.getEntity(String.class);
                        System.out.println(output);
                        break;

                    case 3:
                        System.out.println("Indique el ID del patient");
                        String id = lectura.next();
                        webResource = client.resource("http://localhost:8090/myApp/rest/patient/" + id);

                        response = webResource.accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);

                        if (response.getStatus() != 204) {
                            throw new RuntimeException("Failed : HTTP error code : "
                                    + response.getStatus());
                        }

                        break;
                    default:
                        System.out.println("Opcion invalida");

                }

            } while (opcion != 4);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}

