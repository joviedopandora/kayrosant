/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

/**
 *
 * @author Administrador
 */
public enum EnOpcionFactura {

    PROCESAR("P"),
    ANULAR("A"),
    NO_PROCESAR("N");

    private final String codigo;

    private EnOpcionFactura(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

}
