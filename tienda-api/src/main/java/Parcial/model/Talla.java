package Parcial.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tallas")
public class Talla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idtalla;
    
    private String nombre;
    
    @OneToMany(mappedBy = "talla")
    @JsonIgnore
    private List<Camisa> camisas;

    public Talla() { }

    public Talla(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdtalla() {
        return idtalla;
    }

    public void setIdtalla(Long idtalla) {
        this.idtalla = idtalla;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Camisa> getCamisas() {
        return camisas;
    }

    public void setCamisas(List<Camisa> camisas) {
        this.camisas = camisas;
    }

}