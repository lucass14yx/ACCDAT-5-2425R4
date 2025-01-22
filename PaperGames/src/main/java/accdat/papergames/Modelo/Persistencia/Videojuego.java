/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accdat.papergames.Modelo.Persistencia;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author rezzt
 */
@Entity
@Table(name = "VIDEOJUEGO")
@NamedQueries({
  @NamedQuery(name = "Videojuego.findAll", query = "SELECT v FROM Videojuego v"),
  @NamedQuery(name = "Videojuego.findByIdVideojuego", query = "SELECT v FROM Videojuego v WHERE v.idVideojuego = :idVideojuego"),
  @NamedQuery(name = "Videojuego.findByTitulo", query = "SELECT v FROM Videojuego v WHERE v.titulo = :titulo"),
  @NamedQuery(name = "Videojuego.findByA\u00f1o", query = "SELECT v FROM Videojuego v WHERE v.a\u00f1o = :a\u00f1o"),
  @NamedQuery(name = "Videojuego.findByPegi", query = "SELECT v FROM Videojuego v WHERE v.pegi = :pegi"),
  @NamedQuery(name = "Videojuego.obtenerListaPEGI", query = "SELECT DISTINCT v.pegi FROM Videojuego v")
})
public class Videojuego implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @GeneratedValue(generator = "secuencia_videojuego")
  @SequenceGenerator(name="secuencia_videojuego", sequenceName = "videojuego_sequence", allocationSize = 1)
  private Long idVideojuego;

  @Basic(optional = false)
  private String titulo;

  private String descripcion;

  @Basic(optional = false)
  private short año;

  @Basic(optional = false)
  @Column(name = "PEGI", nullable = false)
  private short pegi; // 3 - 7 - 12 - 16 - 18

  @JoinTable(name = "VIDEOJUEGO_PLATAFORMAS", joinColumns = {
      @JoinColumn(name = "ID_VIDEOJUEGO", referencedColumnName = "ID_VIDEOJUEGO")}, inverseJoinColumns = {
      @JoinColumn(name = "NOMBRE_PLATAFORMA", referencedColumnName = "NOMBRE_PLATAFORMA")})
  @ManyToMany
  private List<Plataforma> plataformaCollection;

  @JoinTable(name = "VIDEOJUEGO_MODO_JUEGO", joinColumns = {
      @JoinColumn(name = "ID_VIDEOJUEGO", referencedColumnName = "ID_VIDEOJUEGO")}, inverseJoinColumns = {
      @JoinColumn(name = "NOMBRE_MODO_JUEGO", referencedColumnName = "NOMBRE_MODO_JUEGO")})
  @ManyToMany
  private List<ModoJuego> modoJuegoCollection;

  @JoinColumn(name = "NOMBRE_GENERO", referencedColumnName = "NOMBRE_GENERO")
  @ManyToOne
  private Genero nombreGenero;

  @JoinColumn(name = "NOMBRE_PLATAFORMA", referencedColumnName = "NOMBRE_PLATAFORMA")
  @ManyToOne
  private Plataforma nombrePlataforma;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVideojuego")
  private List<Dlc> dlcCollection;

  public Videojuego() {
  }

  public Videojuego(Long idVideojuego) {
    this.idVideojuego = idVideojuego;
  }

  public Videojuego(Long idVideojuego, String titulo, short año, short pegi) {
    this.idVideojuego = idVideojuego;
    this.titulo = titulo;
    this.año = año;
    this.pegi = pegi;
  }

  public Long getIdVideojuego() {
    return idVideojuego;
  }

  public void setIdVideojuego(Long idVideojuego) {
    this.idVideojuego = idVideojuego;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public short getAño() {
    return año;
  }

  public void setAño(short año) {
    this.año = año;
  }

  public short getPegi() {
    return pegi;
  }

  public void setPegi(short pegi) {
    this.pegi = pegi;
  }

  public List<Plataforma> getPlataformaCollection() {
    return plataformaCollection;
  }

  public void setPlataformaCollection(List<Plataforma> plataformaCollection) {
    this.plataformaCollection = plataformaCollection;
  }

  public List<ModoJuego> getModoJuegoCollection() {
    return modoJuegoCollection;
  }

  public void setModoJuegoCollection(List<ModoJuego> modoJuegoCollection) {
    this.modoJuegoCollection = modoJuegoCollection;
  }

  public Genero getNombreGenero() {
    return nombreGenero;
  }

  public void setNombreGenero(Genero nombreGenero) {
    this.nombreGenero = nombreGenero;
  }

  public Plataforma getNombrePlataforma() {
    return nombrePlataforma;
  }

  public void setNombrePlataforma(Plataforma nombrePlataforma) {
    this.nombrePlataforma = nombrePlataforma;
  }

  public List<Dlc> getDlcCollection() {
    return dlcCollection;
  }

  public void setDlcCollection(List<Dlc> dlcCollection) {
    this.dlcCollection = dlcCollection;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idVideojuego != null ? idVideojuego.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Videojuego)) {
      return false;
    }
    Videojuego other = (Videojuego) object;
    if ((this.idVideojuego == null && other.idVideojuego != null) || (this.idVideojuego != null && !this.idVideojuego.equals(other.idVideojuego))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "accdat.papergames.Modelo.Persistencia.Videojuego[ idVideojuego=" + idVideojuego + " ]";
  }
  
}
