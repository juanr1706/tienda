package Parcial.controller;

import Parcial.dto.PedidoRequest;
import Parcial.model.Pedido;
import Parcial.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> listas() {
        return pedidoService.listar();
    }

    @GetMapping("/{id}")
    public Optional<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody PedidoRequest pedidoRequest) {
        try {
            Pedido nuevoPedido = pedidoService.guardar(pedidoRequest);
            return ResponseEntity.ok(nuevoPedido);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        pedidoService.eliminar(id);
    }
}
