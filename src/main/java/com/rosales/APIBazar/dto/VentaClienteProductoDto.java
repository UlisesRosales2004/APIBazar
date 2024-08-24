
package com.rosales.APIBazar.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class VentaClienteProductoDto {
    private Long codigo_venta;
    private double total;
    private int cantidad_total_de_productos; 
    private String nombre_del_cliente;
    private String apellido_del_cliente;

    public VentaClienteProductoDto() {
    }

    public VentaClienteProductoDto(Long codigo_venta, double total, int cantidad_total_de_productos, String nombre_del_cliente, String apellido_del_cliente) {
        this.codigo_venta = codigo_venta;
        this.total = total;
        this.cantidad_total_de_productos = cantidad_total_de_productos;
        this.nombre_del_cliente = nombre_del_cliente;
        this.apellido_del_cliente = apellido_del_cliente;
    }

    
    
}
