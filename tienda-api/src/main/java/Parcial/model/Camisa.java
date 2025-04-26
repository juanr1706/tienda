package Parcial.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "camisas")
public class Camisa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcamisa;
    
    private String nombre;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    private BigDecimal precio;
    
    @ManyToOne
    @JoinColumn(name = "idcategoria")
    private Categoria categoria;
    
    @ManyToOne
    @JoinColumn(name = "idcolor")
    private Color color;
    
    @ManyToOne
    @JoinColumn(name = "idtalla")
    private Talla talla;

    public Camisa() { }

    public Camisa(String nombre, String descripcion, BigDecimal precio, Categoria categoria, Color color, Talla talla) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.color = color;
        this.talla = talla;
    }

    public Long getIdcamisa() {
        return idcamisa;
    }

    public void setIdcamisa(Long idcamisa) {
        this.idcamisa = idcamisa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Talla getTalla() {
        return talla;
    }

    public void setTalla(Talla talla) {
        this.talla = talla;
    }

}