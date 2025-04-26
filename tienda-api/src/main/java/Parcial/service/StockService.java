package Parcial.service;

import Parcial.model.Stock;
import Parcial.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> listar() {
        return stockRepository.findAll();
    }

    public Optional<Stock> buscarPorId(Long id) {
        return stockRepository.findById(id);
    }

    public Stock guardar(Stock stock) {
        return stockRepository.save(stock);
    }

    public void eliminar(Long id) {
        stockRepository.deleteById(id);
    }
}
