package com.rosales.APIBazar.service;

import com.rosales.APIBazar.dto.ProductoVentaDto;
import com.rosales.APIBazar.model.Producto;
import com.rosales.APIBazar.repository.IProductoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private IProductoRepository prodRepo;
    @Autowired
    private IVentaService ventaServ;

    @Override
    public String SaveProducto(Producto produ) {
        prodRepo.save(produ);
        return "Se a guardado correctamente";
    }

    @Override
    public List<Producto> GetProductos() {
        return prodRepo.findAll();
    }

    @Override
    public Producto FindProducto(Long id) {
        return prodRepo.findById(id).orElse(null);
    }

    @Override
    public String DeleteProducto(Long id) {
        Producto producto = this.FindProducto(id);
        if (producto == null) {
            return "Producto no encontrado";
        }
        try {
            prodRepo.deleteById(id);
            return "Se a eliminado correctamente";
        } catch (Exception e) {
            return "Primero debe eliminar las ventas relacionadas al producto";
        }
    }

    @Override
    public String EditProducto(Long id, Producto produ) {
        Producto producto = this.FindProducto(id);
        if (producto == null) {
            return "Producto no encontrado";
        }
        producto.setCantidad_disponible(produ.getCantidad_disponible());
        producto.setCosto(produ.getCosto());
        producto.setMarca(produ.getMarca());
        producto.setNombre(produ.getNombre());
        prodRepo.save(producto);
        return "Se a editado correctamente";
    }

    @Override
    public List<Producto> GetProductoSinStock() {
        List<Producto> listaProductos = this.GetProductos();
        List<Producto> listProdSinStock = new ArrayList<>();
        for (Producto produ : listaProductos) {
            if (produ.getCantidad_disponible() < 5.0) {
                listProdSinStock.add(produ);
            }
        }
        return listProdSinStock;
    }

    @Override
    public List<ProductoVentaDto> GetProductoPorVenta(Long codigoVenta) {
        List<ProductoVentaDto> listaProductos = ventaServ.FindVenta(codigoVenta).getProductosConCantidad();
        return listaProductos;
    }

}
