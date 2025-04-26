package Parcial.controller;

import Parcial.model.Color;
import Parcial.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/colores")
public class ColorController {

    @Autowired
    private ColorService colorService;

    @GetMapping
    public List<Color> listas() {
        return colorService.listar();
    }

    @GetMapping("/{id}")
    public Optional<Color> buscarPorId(@PathVariable Long id) {
        return colorService.buscarPorId(id);
    }

    @PostMapping
    public Color guardar(@RequestBody Color color) {
        return colorService.guardar(color);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        colorService.eliminar(id);
    }
}
