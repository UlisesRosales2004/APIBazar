
package com.rosales.APIBazar.service;

import com.rosales.APIBazar.model.Cliente;
import com.rosales.APIBazar.repository.IClienteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements IClienteService{
    
    @Autowired
    IClienteRepository clieRepo;

    @Override
    public String SaveCliente(Cliente clie) {
        clieRepo.save(clie);
        return "Se a Guardado Correctamente";
    }

    @Override
    public List<Cliente> GetClientes() {
        return clieRepo.findAll();
    }

    @Override
    public Cliente FindCliente(Long id) {
        return clieRepo.findById(id).orElse(null);
    }

    @Override
    public String DeleteCliente(Long id) {
        Cliente cliente= this.FindCliente(id);
        if (cliente == null) {
            return "Cliente no encontrado";
        }
        clieRepo.deleteById(id);
        return "Se a eliminado correctamente";
    }

    @Override
    public String EditCliente(Long id,Cliente clie) {
        Cliente cliente= this.FindCliente(id);
        if (cliente == null) {
            return "Cliente no encontrado";
        }
        cliente.setNombre(clie.getNombre());
        cliente.setApellido(clie.getApellido());
        cliente.setDni(clie.getDni());
        clieRepo.save(cliente);
        return "Se a editado correctamente";
    }
}
