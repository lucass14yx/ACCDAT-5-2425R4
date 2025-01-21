/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accdat.papergames.Modelo.Persistencia;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author rezzt
 */
@Entity
@Table(name = "PLATAFORMA")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Plataforma.findAll", query = "SELECT p FROM Plataforma p"),
  @NamedQuery(name = "Plataforma.findByNombrePlataforma", query = "SELECT p FROM Plataforma p WHERE p.nombrePlataforma = :nombrePlataforma"),
  @NamedQuery(name = "Plataforma.findByTipo", query = "SELECT p FROM Plataforma p WHERE p.tipo = :tipo")})
public class Plataforma implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100) // Refleja el tamaño máximo definido en el script SQL
  @Column(name = "NOMBRE_PLATAFORMA", nullable = false, length = 100)
  private String nombrePlataforma;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 50) // Refleja el tamaño máximo definido en el script SQL
  @Column(name = "TIPO", nullable = false, length = 50)
  private String tipo;

  @ManyToMany(mappedBy = "plataformaCollection")
  private Collection<Videojuego> videojuegoCollection;

  @OneToMany(mappedBy = "nombrePlataforma")
  private Collection<Videojuego> videojuegoCollection1;

  public Plataforma() {
  }

  public Plataforma(String nombrePlataforma) {
    this.nombrePlataforma = nombrePlataforma;
  }

  public Plataforma(String nombrePlataforma, String tipo) {
    this.nombrePlataforma = nombrePlataforma;
    this.tipo = tipo;
  }

  public String getNombrePlataforma() {
    return nombrePlataforma;
  }

  public void setNombrePlataforma(String nombrePlataforma) {
    this.nombrePlataforma = nombrePlataforma;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  @XmlTransient
  public Collection<Videojuego> getVideojuegoCollection() {
    return videojuegoCollection;
  }

  public void setVideojuegoCollection(Collection<Videojuego> videojuegoCollection) {
    this.videojuegoCollection = videojuegoCollection;
  }

  @XmlTransient
  public Collection<Videojuego> getVideojuegoCollection1() {
    return videojuegoCollection1;
  }

  public void setVideojuegoCollection1(Collection<Videojuego> videojuegoCollection1) {
    this.videojuegoCollection1 = videojuegoCollection1;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (nombrePlataforma != null ? nombrePlataforma.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Plataforma)) {
      return false;
    }
    Plataforma other = (Plataforma) object;
    if ((this.nombrePlataforma == null && other.nombrePlataforma != null) || (this.nombrePlataforma != null && !this.nombrePlataforma.equals(other.nombrePlataforma))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "accdat.papergames.Modelo.Persistencia.Plataforma[ nombrePlataforma=" + nombrePlataforma + " ]";
  }
  
}
