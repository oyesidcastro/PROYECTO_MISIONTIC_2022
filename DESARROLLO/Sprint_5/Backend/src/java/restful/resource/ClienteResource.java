/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful.resource;

import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import restful.Model.ClienteModel;
import restful.service.ClienteService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;

/**
 *
 * @author SENA
 */
@Path("clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    ClienteService servicio = new ClienteService();

    @GET
    public ArrayList<ClienteModel> getClientes() {

        return servicio.getClientes();
    }

    @Path("/{ClienteId}")
    @GET
    public ClienteModel getCliente(@PathParam("ClienteId") int codigo) {

        return servicio.getCliente(codigo);
    }

    @POST
    public ClienteModel addCliente(String JSON) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        ClienteModel cliente = gson.fromJson(JSON, ClienteModel.class);
        return servicio.addCliente(cliente);
    }

    @DELETE
    @Path("/{ClienteId}")
    public String delCliente(@PathParam("ClienteId") int codigo) {

        return servicio.delCliente(codigo);

    }

    @PUT
    public ClienteModel updateCliente(String JSON) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        ClienteModel cliente = gson.fromJson(JSON, ClienteModel.class);
        return servicio.updateCliente(cliente);
    }

}
