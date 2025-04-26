package Parcial.dto;

import java.math.BigDecimal;
import java.util.List;

public class PedidoRequest {

    private Long idcliente;
    private BigDecimal total;
    private List<DetallePedidoRequest> detalles;

    public Long getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Long idcliente) {
        this.idcliente = idcliente;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<DetallePedidoRequest> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedidoRequest> detalles) {
        this.detalles = detalles;
    }

}
