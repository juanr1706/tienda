package Parcial.repository;

import Parcial.model.Camisa;
import Parcial.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByCamisa(Camisa camisa);
}
