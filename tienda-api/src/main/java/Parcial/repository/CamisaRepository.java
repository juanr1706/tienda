package Parcial.repository;

import Parcial.model.Camisa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CamisaRepository extends JpaRepository<Camisa, Long> {
}
