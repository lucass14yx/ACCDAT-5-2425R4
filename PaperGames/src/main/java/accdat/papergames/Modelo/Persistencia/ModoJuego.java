/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accdat.papergames.Modelo.Persistencia;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import java.io.Serializable;


/**
 *
 * @author rezzt && lxxkass
 */
@Entity
@Table(name = "MODO_JUEGO")

@NamedQueries({
  @NamedQuery(name = "ModoJuego.findAll", query = "SELECT m FROM ModoJuego m"),
  @NamedQuery(name = "ModoJuego.findByNombreModoJuego", query = "SELECT m FROM ModoJuego m WHERE m.nombreModoJuego = :nombreModoJuego")})
public class ModoJuego implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  private String nombreModoJuego;

  @ManyToMany(mappedBy = "modoJuegoCollection")
  private List<Videojuego> videojuegoCollection;

  public ModoJuego() {
  }

  public ModoJuego(String nombreModoJuego) {
    this.nombreModoJuego = nombreModoJuego;
  }

  public String getNombreModoJuego() {
    return nombreModoJuego;
  }

  public void setNombreModoJuego(String nombreModoJuego) {
    this.nombreModoJuego = nombreModoJuego;
  }


  public List<Videojuego> getVideojuegoCollection() {
    return videojuegoCollection;
  }

  public void setVideojuegoCollection(List<Videojuego> videojuegoCollection) {
    this.videojuegoCollection = videojuegoCollection;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (nombreModoJuego != null ? nombreModoJuego.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof ModoJuego)) {
      return false;
    }
    ModoJuego other = (ModoJuego) object;
    if ((this.nombreModoJuego == null && other.nombreModoJuego != null) || (this.nombreModoJuego != null && !this.nombreModoJuego.equals(other.nombreModoJuego))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "accdat.papergames.Modelo.Persistencia.ModoJuego[ nombreModoJuego=" + nombreModoJuego + " ]";
  }
  
}
