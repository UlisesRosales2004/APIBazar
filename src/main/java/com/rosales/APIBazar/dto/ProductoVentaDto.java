package com.rosales.APIBazar.dto;

import com.rosales.APIBazar.model.Producto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
@Entity
public class ProductoVentaDto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private Producto producto;

    private Double cantidadVendida;

    public ProductoVentaDto() {
    }

    public ProductoVentaDto(Long id, Producto producto, Double cantidadVendida) {
        this.id = id;
        this.producto = producto;
        this.cantidadVendida = cantidadVendida;
    }
    
}
