package Parcial.service;

import Parcial.model.Talla;
import Parcial.repository.TallaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TallaService {

    @Autowired
    private TallaRepository tallaRepository;

    public List<Talla> listar() {
        return tallaRepository.findAll();
    }

    public Optional<Talla> buscarPorId(Long id) {
        return tallaRepository.findById(id);
    }

    public Talla guardar(Talla talla) {
        return tallaRepository.save(talla);
    }

    public void eliminar(Long id) {
        tallaRepository.deleteById(id);
    }
}
