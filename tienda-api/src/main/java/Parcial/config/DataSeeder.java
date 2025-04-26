package Parcial.config;

import Parcial.model.*;
import Parcial.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(
            ColorRepository colorRepository,
            TallaRepository tallaRepository,
            ClienteRepository clienteRepository,
            CategoriaRepository categoriaRepository,
            CamisaRepository camisaRepository,
            StockRepository stockRepository
    ) {
        return args -> {

            if (colorRepository.count() == 0) {
                List<Color> colores = List.of(
                        new Color("Rojo"),
                        new Color("Azul"),
                        new Color("Verde"),
                        new Color("Negro"),
                        new Color("Blanco")
                );
                colorRepository.saveAll(colores);
            }

            if (tallaRepository.count() == 0) {
                List<Talla> tallas = List.of(
                        new Talla("S"),
                        new Talla("M"),
                        new Talla("L"),
                        new Talla("XL"),
                        new Talla("XXL")
                );
                tallaRepository.saveAll(tallas);
            }

            if (clienteRepository.count() == 0) {
                List<Cliente> clientes = List.of(
                        new Cliente("Juan Pérez", "juan@gmail.com", "123456789", "Calle 11 #11-11"),
                        new Cliente("María López", "maria@gmail.com", "987654321", "Calle 12 #12-12"),
                        new Cliente("Carlos García", "carlos@gmail.com", "111222333", "Calle 13 #13-13"),
                        new Cliente("Ana Torres", "ana@gmail.com", "444555666", "Calle 14 #14-14")
                );
                clienteRepository.saveAll(clientes);
            }

            if (categoriaRepository.count() == 0) {
                List<Categoria> categorias = List.of(
                        new Categoria("Casual"),
                        new Categoria("Deportiva"),
                        new Categoria("Formal"),
                        new Categoria("Invierno"),
                        new Categoria("Verano")
                );
                categoriaRepository.saveAll(categorias);
            }

            // Obtener referencias para Camisas
            Optional<Categoria> casual = categoriaRepository.findByNombre("Casual");
            Optional<Categoria> deportiva = categoriaRepository.findByNombre("Deportiva");

            Optional<Color> rojo = colorRepository.findByNombre("Rojo");
            Optional<Color> azul = colorRepository.findByNombre("Azul");

            Optional<Talla> tallaM = tallaRepository.findByNombre("M");
            Optional<Talla> tallaL = tallaRepository.findByNombre("L");

            if (camisaRepository.count() == 0 && casual.isPresent() && deportiva.isPresent() && rojo.isPresent() && azul.isPresent() && tallaM.isPresent() && tallaL.isPresent()) {
                List<Camisa> camisas = List.of(
                        new Camisa("Camisa Roja Casual", "Camisa de algodón", new BigDecimal("29.99"), casual.get(), rojo.get(), tallaM.get()),
                        new Camisa("Camisa Azul Deportiva", "Camisa ligera para ejercicio", new BigDecimal("35.99"), deportiva.get(), azul.get(), tallaL.get())
                );
                camisaRepository.saveAll(camisas);
            }

            // Obtener referencias de las camisas para Stock
            List<Camisa> camisasGuardadas = camisaRepository.findAll();
            if (stockRepository.count() == 0 && !camisasGuardadas.isEmpty()) {
                List<Stock> stockList = List.of(
                        new Stock(50, camisasGuardadas.get(0)),
                        new Stock(30, camisasGuardadas.get(1))
                );
                stockRepository.saveAll(stockList);
            }

        };
    }
}
