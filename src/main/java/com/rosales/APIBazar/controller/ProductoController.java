package com.rosales.APIBazar.controller;

import com.rosales.APIBazar.dto.ProductoVentaDto;
import com.rosales.APIBazar.model.Producto;
import com.rosales.APIBazar.service.IProductoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "*")
@RestController
public class ProductoController {
    @Autowired
    IProductoService produServ;
    @PostMapping("/productos/crear")
    public String saveProducto(@RequestBody Producto produ){
        return produServ.SaveProducto(produ);
    }
    @GetMapping("/productos")
    public List<Producto> getProductos(){
        return produServ.GetProductos();
    }
    @GetMapping("/productos/{id_producto}")
    public Producto findProducto(@PathVariable Long id_producto){
        return produServ.FindProducto(id_producto);
    }
    @DeleteMapping("/productos/eliminar/{id_producto}")
    public String deleteProducto(@PathVariable Long id_producto){
        return produServ.DeleteProducto(id_producto);
    }
    @PutMapping("/productos/editar/{id_producto}")
    public String editProducto(@PathVariable Long id_producto,@RequestBody Producto produ){
        return produServ.EditProducto(id_producto, produ);
    }
    @GetMapping("/productos/falta_stock")
    public List<Producto> getProductosSinStock(){
        return produServ.GetProductoSinStock();
    }
    @GetMapping("/ventas/productos/{codigo_venta}")
    public List<ProductoVentaDto> GetProductoPorVenta(@PathVariable Long codigo_venta){
        return produServ.GetProductoPorVenta(codigo_venta);
    }
}
