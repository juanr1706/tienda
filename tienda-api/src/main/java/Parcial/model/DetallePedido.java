package Parcial.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "detallespedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iddetalle;
    
    private int cantidad;
    private BigDecimal subtotal;
    
    @ManyToOne
    @JoinColumn(name = "idcamisa")
    private Camisa camisa;
    
    @ManyToOne
    @JoinColumn(name = "idpedido")
    @JsonIgnore
    private Pedido pedido;

    public DetallePedido() { }

    public DetallePedido(int cantidad, BigDecimal subtotal, Camisa camisa, Pedido pedido) {
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.camisa = camisa;
        this.pedido = pedido;
    }

    public Long getIddetalle() {
        return iddetalle;
    }

    public void setIddetalle(Long iddetalle) {
        this.iddetalle = iddetalle;
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

    public Camisa getCamisa() {
        return camisa;
    }

    public void setCamisa(Camisa camisa) {
        this.camisa = camisa;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

}