
package com.rosales.APIBazar.service;

import com.rosales.APIBazar.dto.ProductoVentaDto;
import com.rosales.APIBazar.model.Producto;
import java.util.List;

public interface IProductoService {
    
    public String SaveProducto(Producto produ);
    public List<Producto> GetProductos();
    public Producto FindProducto(Long id);
    public String DeleteProducto(Long id);
    public String EditProducto(Long id,Producto produ);
    public List<Producto> GetProductoSinStock();
    public List<ProductoVentaDto> GetProductoPorVenta(Long codigoVenta);
}
