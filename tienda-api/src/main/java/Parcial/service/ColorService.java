package Parcial.service;

import Parcial.model.Color;
import Parcial.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColorService {

    @Autowired
    private ColorRepository colorRepository;

    public List<Color> listar() {
        return colorRepository.findAll();
    }

    public Optional<Color> buscarPorId(Long id) {
        return colorRepository.findById(id);
    }

    public Color guardar(Color color) {
        return colorRepository.save(color);
    }

    public void eliminar(Long id) {
        colorRepository.deleteById(id);
    }
}
