
package com.rosales.APIBazar.service;

import com.rosales.APIBazar.dto.VentaClienteProductoDto;
import com.rosales.APIBazar.model.Venta;
import java.time.LocalDate;
import java.util.List;

public interface IVentaService {    
    
    public String SaveVenta(Venta venta);
    public List<Venta> GetVentas();
    public Venta FindVenta(Long id);
    public String DeleteVenta(Long id);
    public String EditVenta(Long id,Venta venta);
    public String MontoYVentasPorDia(LocalDate fecha_venta);
    public VentaClienteProductoDto MayorVenta();
}
