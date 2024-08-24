package com.rosales.APIBazar.model;

import com.rosales.APIBazar.dto.ProductoVentaDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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

    public Venta(Long codigo_venta, LocalDate fecha_venta, Double total, List<ProductoVentaDto> productosConCantidad, Cliente unCliente) {
        this.codigo_venta = codigo_venta;
        this.fecha_venta = fecha_venta;
        this.total = total;
        this.productosConCantidad = productosConCantidad;
        this.unCliente = unCliente;
    }

}