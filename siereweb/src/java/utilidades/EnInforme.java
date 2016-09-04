/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

/**
 *
 * @author breyner.robles
 */
public enum EnInforme {

    FACTURA_VENTA(1),
    REMISION_VENTA(2),
    ORDEN_DE_PRODUCCION_DE_CLIENTES_COORPORATIVOS(3),
    ORDEN_DE_PRODUCCION_KIDS(4),
    FACTURAS_COORPORATIVO(5),
    FACTURAS_KIDS(6),
    CONSOLIDADO_DE_VENTAS(7),
    CONSOLIDADO_DE_LLAMADAS(8),
    COTIZACION_POR_DETALLE_CLIENTE(9),
    FACTURA_SAS(10),
    NOMINA(11);
    Integer id;

    EnInforme(Integer id) {
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
