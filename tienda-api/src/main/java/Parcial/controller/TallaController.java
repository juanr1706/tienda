package Parcial.controller;

import Parcial.model.Talla;
import Parcial.service.TallaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tallas")
public class TallaController {

    @Autowired
    private TallaService tallaService;

    @GetMapping
    public List<Talla> listas() {
        return tallaService.listar();
    }

    @GetMapping("/{id}")
    public Optional<Talla> buscarPorId(@PathVariable Long id) {
        return tallaService.buscarPorId(id);
    }

    @PostMapping
    public Talla guardar(@RequestBody Talla talla) {
        return tallaService.guardar(talla);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        tallaService.eliminar(id);
    }
}
