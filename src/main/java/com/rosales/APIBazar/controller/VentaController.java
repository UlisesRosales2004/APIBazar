
package com.rosales.APIBazar.controller;

import com.rosales.APIBazar.dto.VentaClienteProductoDto;
import com.rosales.APIBazar.model.Venta;
import com.rosales.APIBazar.service.IVentaService;
import java.time.LocalDate;
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
@CrossOrigin(origins = "")
@RestController
public class VentaController {
    @Autowired
    IVentaService ventaServ;
    @PostMapping("/ventas/crear")
    public String saveVenta(@RequestBody Venta venta){
        return ventaServ.SaveVenta(venta);
    }
    @GetMapping("/ventas")
    public List<Venta> getVentas(){
        return ventaServ.GetVentas();
    }
    @GetMapping("/ventas/{id_venta}")
    public Venta findVenta(@PathVariable Long id_venta){
        return ventaServ.FindVenta(id_venta);
    }
    @DeleteMapping("/ventas/eliminar/{id_venta}")
    public String deleteVenta(@PathVariable Long id_venta){
        return ventaServ.DeleteVenta(id_venta);
    }
    @PutMapping("/ventas/editar/{id_venta}")
    public String editVenta(@PathVariable Long id_venta,@RequestBody Venta venta){
        return ventaServ.EditVenta(id_venta, venta);
    }
    @GetMapping("/ventas/fecha/{fecha_venta}")
    public String MontoYVentasPorDia(@PathVariable LocalDate fecha_venta) {
        return ventaServ.MontoYVentasPorDia(fecha_venta);
    }
    @GetMapping("/ventas/mayor_venta")
    public VentaClienteProductoDto MayorVenta(){
        return ventaServ.MayorVenta();
    }
}
