package Parcial.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcategoria;
    
    private String nombre;
    
    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private List<Camisa> camisas;

    public Categoria() { }

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Long idcategoria) {
        this.idcategoria = idcategoria;
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