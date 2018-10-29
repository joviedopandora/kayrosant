/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pandora.web.inventario;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Luis Fernando
 */
@Path("/codigobarras")
public class CodigoDeBarrasRFWS {
    
    @POST
    @Path("/agregarprod")
    @Consumes(MediaType.APPLICATION_JSON)
    public void agregarProductos(){}
}
