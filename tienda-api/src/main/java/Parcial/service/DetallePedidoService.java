package Parcial.service;

import Parcial.model.DetallePedido;
import Parcial.repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    public List<DetallePedido> listar() {
        return detallePedidoRepository.findAll();
    }

    public Optional<DetallePedido> buscarPorId(Long id) {
        return detallePedidoRepository.findById(id);
    }

    public DetallePedido guardar(DetallePedido detallePedido) {
        return detallePedidoRepository.save(detallePedido);
    }

    public void eliminar(Long id) {
        detallePedidoRepository.deleteById(id);
    }
}
