package Parcial.dto;

import java.math.BigDecimal;

public class DetallePedidoRequest {

    private Long idcamisa;
    private int cantidad;
    private BigDecimal subtotal;

    public Long getIdcamisa() {
        return idcamisa;
    }

    public void setIdcamisa(Long idcamisa) {
        this.idcamisa = idcamisa;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

}
