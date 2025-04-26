package Parcial.controller;

import Parcial.model.Stock;
import Parcial.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping
    public List<Stock> listas() {
        return stockService.listar();
    }

    @GetMapping("/{id}")
    public Optional<Stock> buscarPorId(@PathVariable Long id) {
        return stockService.buscarPorId(id);
    }

    @PostMapping
    public Stock guardar(@RequestBody Stock stock) {
        return stockService.guardar(stock);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        stockService.eliminar(id);
    }
}
