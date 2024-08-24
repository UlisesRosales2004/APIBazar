package com.rosales.APIBazar.service;

import com.rosales.APIBazar.dto.ProductoVentaDto;
import com.rosales.APIBazar.dto.VentaClienteProductoDto;
import com.rosales.APIBazar.model.Producto;
import com.rosales.APIBazar.model.Venta;
import com.rosales.APIBazar.repository.IProductoRepository;
import com.rosales.APIBazar.repository.IVentaRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private IVentaRepository ventaRepo;
    @Autowired
    private IProductoRepository produRepo;

    @Override
    @Transactional
    public String SaveVenta(Venta venta) {
        List<ProductoVentaDto> listaProductos = venta.getProductosConCantidad();
        // Verificar stock disponible y actualizar cantidades en una sola pasada
        for (ProductoVentaDto produVenta : listaProductos) {
            Producto producto = produVenta.getProducto();
            Double cantidadSolicitada = produVenta.getCantidadVendida();
            Producto produR = produRepo.findById(producto.getCodigo_producto()).orElse(null);
            if (cantidadSolicitada > produR.getCantidad_disponible()) {
                return "No hay stock suficiente para " + producto.getNombre();
            }
            // Reducir stock disponible
            producto.setCantidad_disponible(produR.getCantidad_disponible() - cantidadSolicitada);
        }
        // Guardar los productos actualizados y la venta en una transacción
        try {
            for (ProductoVentaDto produVenta : listaProductos) {
                Producto producto = produVenta.getProducto();
                produRepo.save(producto);
            }
            venta.setTotal(venta.calcularTotal());
            System.out.println(venta.getTotal());
            ventaRepo.save(venta);
        } catch (Exception e) {
            return "Error al guardar la venta: " + e.getMessage();
        }

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
        List<ProductoVentaDto> listaProductos = venta.getProductosConCantidad();
        for (ProductoVentaDto produVenta : listaProductos) {
            Producto producto = produVenta.getProducto();
            Double cantidadSolicitada = produVenta.getCantidadVendida();
            Producto produR = produRepo.findById(producto.getCodigo_producto()).orElse(null);
            // Devolver el stock
            producto.setCantidad_disponible(produR.getCantidad_disponible() + cantidadSolicitada);
        }
        try {
            for (ProductoVentaDto produVenta : listaProductos) {
                Producto producto = produVenta.getProducto();
                produRepo.save(producto);
            }
            ventaRepo.deleteById(codigoVenta);
        } catch (Exception e) {
            return "Error al eliminar la venta: " + e.getMessage();
        }
        return "Venta eliminada y stock restaurado";
    }

    @Override
    @Transactional
    public String EditVenta(Long id, Venta ventaa) {
        Venta ventaRep = this.FindVenta(id);
        if (ventaRep == null) {
            return "Venta no encontrada";
        }

        List<ProductoVentaDto> listaProductosOriginales = ventaRep.getProductosConCantidad();
        List<ProductoVentaDto> listaProductosNuevos = ventaa.getProductosConCantidad();
        Map<Long, Double> cantidadProductosNueva = new HashMap();
        for (ProductoVentaDto produVenta : listaProductosOriginales) {
            Producto producto = produVenta.getProducto();
            Double cantidadSolicitada = produVenta.getCantidadVendida();
            Producto produR = produRepo.findById(producto.getCodigo_producto()).orElse(null);
            cantidadProductosNueva.put(producto.getCodigo_producto(), produR.getCantidad_disponible() + cantidadSolicitada);
        }

        // Verificar si hay suficiente stock para los nuevos productos
        for (ProductoVentaDto produVenta : listaProductosNuevos) {
            Producto producto = produVenta.getProducto();
            Double cantidadSolicitada = produVenta.getCantidadVendida();
            Producto produR = produRepo.findById(producto.getCodigo_producto()).orElse(null);
            if (produR != null) {
                if (cantidadSolicitada > cantidadProductosNueva.getOrDefault(producto.getCodigo_producto(), Double.MAX_VALUE)) {
                    return "No hay stock suficiente para " + producto.getNombre();
                } else if (producto.getCodigo_producto()==null && cantidadSolicitada > produR.getCantidad_disponible()) {
                    return "No hay stock suficiente para " + producto.getNombre();
                }
            }
        }
        // Revertir el stock de los productos en la venta original
        for (ProductoVentaDto produVenta : listaProductosOriginales) {
            Producto producto = produVenta.getProducto();
            Double cantidadSolicitada = produVenta.getCantidadVendida();
            Producto produR = produRepo.findById(producto.getCodigo_producto()).orElse(null);
            if (produR != null) {
                producto.setCantidad_disponible(produR.getCantidad_disponible() + cantidadSolicitada);
            }
        }

        // Actualizar la venta existente con los nuevos datos
        ventaRep.setFecha_venta(ventaa.getFecha_venta());
        ventaRep.setProductosConCantidad(ventaa.getProductosConCantidad());
        ventaRep.setTotal(ventaRep.calcularTotal());
        ventaRep.setUnCliente(ventaa.getUnCliente());

        try {
            // Actualizar el stock de los nuevos productos
            for (ProductoVentaDto produVenta : listaProductosNuevos) {
                Producto producto = produVenta.getProducto();
                Double cantidadSolicitada = produVenta.getCantidadVendida();
                Producto produR = produRepo.findById(producto.getCodigo_producto()).orElse(null);
                if (produR != null) {
                    producto.setCantidad_disponible(produR.getCantidad_disponible() - cantidadSolicitada);
                    produRepo.save(producto);
                }
            }

            // Guardar la venta actualizada
            ventaRepo.save(ventaRep);
            return "Venta editada exitosamente";
        } catch (Exception e) {
            return "Error al editar la venta: " + e.getMessage();
        }
    }

    @Override
    public String MontoYVentasPorDia(LocalDate fecha_venta) {
        int cont = 0;
        double total = 0;
        List<Venta> listaVentas = this.GetVentas();
        for (Venta venta : listaVentas) {
            if (venta.getFecha_venta().equals(fecha_venta)) {
                cont = cont + 1;
                total = total + venta.getTotal();
            }
        }
        return "El dia " + fecha_venta + " hubieron un total de " + cont + " ventas y se vendio un total de $" + total;
    }

    @Override
    public VentaClienteProductoDto MayorVenta() {
        VentaClienteProductoDto datosVentaMayor = new VentaClienteProductoDto();
        double montoMayor = 0;
        Venta ventaMayor = new Venta();
        int cantidadDeProductos=0;
        List<Venta> listaVentas = this.GetVentas();
        for (Venta venta : listaVentas) {
            if (venta.getTotal() > montoMayor) {
                montoMayor = venta.getTotal();
                ventaMayor = venta;
            }
        }
        datosVentaMayor.setTotal(ventaMayor.getTotal());
        datosVentaMayor.setNombre_del_cliente(ventaMayor.getUnCliente().getNombre());
        datosVentaMayor.setApellido_del_cliente(ventaMayor.getUnCliente().getApellido());
        datosVentaMayor.setCodigo_venta(ventaMayor.getCodigo_venta());
        List<ProductoVentaDto> listaProductos = ventaMayor.getProductosConCantidad();
        for (ProductoVentaDto produVenta : listaProductos) {
            Producto producto = produVenta.getProducto();
            Double cantidadVendidaXproducto = produVenta.getCantidadVendida();
            cantidadDeProductos=(int) (cantidadVendidaXproducto+cantidadDeProductos);
        }
        datosVentaMayor.setCantidad_total_de_productos(cantidadDeProductos);
        return datosVentaMayor;
    }

}
