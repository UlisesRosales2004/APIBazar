
package com.rosales.APIBazar.controller;

import com.rosales.APIBazar.model.Cliente;
import com.rosales.APIBazar.service.IClienteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {
    @Autowired
    IClienteService cliServ;
    @PostMapping("/clientes/crear")
    public String saveCliente(@RequestBody Cliente clie){
        return cliServ.SaveCliente(clie);
    }
    @GetMapping("/clientes")
    public List<Cliente> getClientes(){
        return cliServ.GetClientes();
    }
    @GetMapping("/clientes/{id_cliente}")
    public Cliente findCliente(@PathVariable Long id_cliente){
        return cliServ.FindCliente(id_cliente);
    }
    @DeleteMapping("/clientes/eliminar/{id_cliente}")
    public String deleteCliente(@PathVariable Long id_cliente){
        return cliServ.DeleteCliente(id_cliente);
    }
    @PutMapping("/clientes/editar/{id_cliente}")
    public String editCliente(@PathVariable Long id_cliente,@RequestBody Cliente clie){
        return cliServ.EditCliente(id_cliente, clie);
    }
}
