package Parcial.service;

import Parcial.model.Categoria;
import Parcial.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }
}
