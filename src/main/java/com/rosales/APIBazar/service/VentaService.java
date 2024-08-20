package com.rosales.APIBazar.service;

import com.rosales.APIBazar.dto.VentaClienteProductoDto;
import com.rosales.APIBazar.model.Venta;
import com.rosales.APIBazar.repository.IVentaRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService implements IVentaService {

    @Autowired
    IVentaRepository ventaRepo;

    @Override
    @Transactional
    public String SaveVenta(Venta venta) {
        ventaRepo.save(venta);
        return "Se ha guardado la venta exitosamente";
    }

    @Override
    public List<Venta> GetVentas() {
        return ventaRepo.findAll();
    }

    @Override
    public Venta FindVenta(Long id) {
        return ventaRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public String DeleteVenta(Long codigoVenta) {
        // Buscar la venta que se desea eliminar
        Venta venta = this.FindVenta(codigoVenta);

        if (venta == null) {
            return "Venta no encontrada";
        }
        ventaRepo.deleteById(codigoVenta);
        return "Venta eliminada y stock restaurado";
    }

    @Override
    @Transactional
    public String EditVenta(Long id, Venta ventaa) {
        Venta venta=this.FindVenta(id);
        if (venta == null) {
            return "Venta no encontrada";
        }
        venta.setFecha_venta(ventaa.getFecha_venta());
        venta.setListaProductos(ventaa.getListaProductos());
        venta.setTotal(ventaa.getTotal());
        venta.setUnCliente(ventaa.getUnCliente());
        this.SaveVenta(venta);
        return "Venta editada correctamente";
    }

    @Override
    public String MontoYVentasPorDia(LocalDate fecha_venta) {
        int cont=0;
        double total=0;
        List<Venta>listaVentas=this.GetVentas();
        for(Venta venta:listaVentas){
            if (venta.getFecha_venta().equals(fecha_venta)) {
                cont=cont+1;
                total=total+venta.getTotal();
            }
        }
        return "El dia "+fecha_venta+" hubieron un total de "+cont+" ventas y se vendio un total de $"+total;
    }

    @Override
    public VentaClienteProductoDto MayorVenta() {
        VentaClienteProductoDto datosVentaMayor = new VentaClienteProductoDto();
        double montoMayor=0;
        Venta ventaMayor=new Venta();
        List<Venta>listaVentas=this.GetVentas();
        for(Venta venta:listaVentas){
            if (venta.getTotal()>montoMayor) {
                montoMayor=venta.getTotal();
                ventaMayor=venta;
            }
        }
        datosVentaMayor.setTotal(ventaMayor.getTotal());
        datosVentaMayor.setNombre_del_cliente(ventaMayor.getUnCliente().getNombre());
        datosVentaMayor.setApellido_del_cliente(ventaMayor.getUnCliente().getApellido());
        datosVentaMayor.setCodigo_venta(ventaMayor.getCodigo_venta());
        datosVentaMayor.setCantidad_de_productos(ventaMayor.getListaProductos().size());
        return datosVentaMayor;
    }
    

    

}
