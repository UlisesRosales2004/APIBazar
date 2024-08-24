package com.rosales.APIBazar.model;

import com.rosales.APIBazar.dto.ProductoVentaDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
@Getter@Setter
@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigo_venta;
    private LocalDate fecha_venta;
    private Double total;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ProductoVentaDto> productosConCantidad;
    @ManyToOne
    private Cliente unCliente;

    public Venta() {}

    public Venta(Long codigo_venta, LocalDate fecha_venta, List<ProductoVentaDto> productosConCantidad, Cliente unCliente) {
        this.codigo_venta = codigo_venta;
        this.fecha_venta = fecha_venta;
        this.productosConCantidad = productosConCantidad;
        this.unCliente = unCliente;
    }
    public Double calcularTotal(){
        Double total=0.0;
        List<ProductoVentaDto> listaProductos = this.productosConCantidad;
        for (ProductoVentaDto produVenta : listaProductos) {
            Producto producto = produVenta.getProducto();
            Double cantidad = produVenta.getCantidadVendida();
            total=total+(cantidad*producto.getCosto());
        }
        return total;
    }

}