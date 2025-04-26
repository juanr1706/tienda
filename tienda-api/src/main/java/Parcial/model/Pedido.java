package Parcial.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idpedido;
    
    private LocalDate fechapedido;
    private BigDecimal total;
    
    @ManyToOne
    @JoinColumn(name = "idcliente")
    private Cliente cliente;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles;

    public Pedido() { }

    public Long getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(Long idpedido) {
        this.idpedido = idpedido;
    }

    public LocalDate getFechapedido() {
        return fechapedido;
    }

    public void setFechapedido(LocalDate fechapedido) {
        this.fechapedido = fechapedido;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

}
