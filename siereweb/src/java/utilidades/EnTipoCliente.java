/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

/**
 *
 * @author Breyner Robles
 */
public enum EnTipoCliente {

    COORPORATIVO(1),
    KIDS(2),
    SAS(3);
    Integer id;

    private EnTipoCliente(Integer id) {
        this.id = id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return
     */
    public Integer getId() {
        return id;
    }
}
