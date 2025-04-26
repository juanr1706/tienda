package Parcial.service;

import Parcial.dto.PedidoRequest;
import Parcial.model.*;
import Parcial.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CamisaRepository camisaRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido guardar(PedidoRequest pedidoRequest) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(pedidoRequest.getIdcliente());
        if (clienteOpt.isEmpty()) {
            throw new IllegalStateException("Cliente no encontrado");
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(clienteOpt.get());
        pedido.setFechapedido(LocalDate.now()); // Se asigna la fecha actual
        pedido.setTotal(pedidoRequest.getTotal());

        List<DetallePedido> detalles = pedidoRequest.getDetalles().stream().map(detalleReq -> {
            Optional<Camisa> camisaOpt = camisaRepository.findById(detalleReq.getIdcamisa());
            if (camisaOpt.isEmpty()) {
                throw new IllegalStateException("Camisa con ID " + detalleReq.getIdcamisa() + " no encontrada");
            }

            Camisa camisa = camisaOpt.get();
            Optional<Stock> stockOpt = stockRepository.findByCamisa(camisa);

            if (stockOpt.isEmpty() || stockOpt.get().getCantidad() < detalleReq.getCantidad()) {
                throw new IllegalStateException("No hay suficiente stock para la camisa: " + camisa.getNombre());
            }

            // Descontar stock
            Stock stock = stockOpt.get();
            stock.setCantidad(stock.getCantidad() - detalleReq.getCantidad());
            stockRepository.save(stock);

            return new DetallePedido(detalleReq.getCantidad(), detalleReq.getSubtotal(), camisa, pedido);
        }).collect(Collectors.toList());

        pedido.setDetalles(detalles);
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        detallePedidoRepository.saveAll(detalles);

        return pedidoGuardado;
    }

    public void eliminar(Long id) {
        pedidoRepository.deleteById(id);
    }
}
