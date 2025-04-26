package Parcial.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "colores")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcolor;
    
    private String nombre;
    
    @OneToMany(mappedBy = "color")
    @JsonIgnore
    private List<Camisa> camisas;

    public Color() {}

    public Color(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdcolor() {
        return idcolor;
    }

    public void setIdcolor(Long idcolor) {
        this.idcolor = idcolor;
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