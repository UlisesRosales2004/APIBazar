
package com.rosales.APIBazar.service;

import com.rosales.APIBazar.model.Cliente;
import java.util.List;

public interface IClienteService {
    
    public String SaveCliente(Cliente clie);
    public List<Cliente> GetClientes();
    public Cliente FindCliente(Long id);
    public String DeleteCliente(Long id);
    public String EditCliente(Long id,Cliente clie);
}
