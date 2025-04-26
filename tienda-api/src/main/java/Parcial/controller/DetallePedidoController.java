package Parcial.controller;

import Parcial.model.DetallePedido;
import Parcial.service.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/detalles-pedido")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @GetMapping
    public List<DetallePedido> listas() {
        return detallePedidoService.listar();
    }

    @GetMapping("/{id}")
    public Optional<DetallePedido> buscarPorId(@PathVariable Long id) {
        return detallePedidoService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        detallePedidoService.eliminar(id);
    }

}
