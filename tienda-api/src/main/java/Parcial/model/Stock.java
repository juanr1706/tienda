package Parcial.model;

import jakarta.persistence.*;

@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idstock;
    
    private int cantidad;
    
    @OneToOne
    @JoinColumn(name = "idcamisa")
    private Camisa camisa;

    public Stock() { }

    public Stock(int cantidad, Camisa camisa) {
        this.cantidad = cantidad;
        this.camisa = camisa;
    }

    public Long getIdstock() {
        return idstock;
    }

    public void setIdstock(Long idstock) {
        this.idstock = idstock;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Camisa getCamisa() {
        return camisa;
    }

    public void setCamisa(Camisa camisa) {
        this.camisa = camisa;
    }

}
