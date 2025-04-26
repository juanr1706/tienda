package Parcial.service;

import Parcial.model.Camisa;
import Parcial.repository.CamisaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CamisaService {
    
    @Autowired
    private CamisaRepository camisaRepository;

    public List<Camisa> listar() {
        return camisaRepository.findAll();
    }

    public Optional<Camisa> buscarPorId(Long id) {
        return camisaRepository.findById(id);
    }

    public Camisa guardar(Camisa camisa) {
        return camisaRepository.save(camisa);
    }

    public void eliminar(Long id) {
        camisaRepository.deleteById(id);
    }

}
