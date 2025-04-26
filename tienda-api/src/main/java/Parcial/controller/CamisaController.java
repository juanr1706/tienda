package Parcial.controller;

import Parcial.model.Camisa;
import Parcial.service.CamisaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/camisas")
public class CamisaController {

    @Autowired
    private CamisaService camisaService;

    @GetMapping
    public List<Camisa> listas() {
        return camisaService.listar();
    }

    @GetMapping("/{id}")
    public Optional<Camisa> buscarPorId(@PathVariable Long id) {
        return camisaService.buscarPorId(id);
    }

    @PostMapping
    public Camisa guardar(@RequestBody Camisa camisa) {
        return camisaService.guardar(camisa);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        camisaService.eliminar(id);
    }
}
