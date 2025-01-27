/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accdat.papergames.Controlador;

import accdat.papergames.Modelo.Filtros.FiltrosJPAController;
import accdat.papergames.Modelo.HelperOperaciones;
import accdat.papergames.Modelo.ModeloService;
import accdat.papergames.Modelo.Persistencia.Genero;
import accdat.papergames.Modelo.Persistencia.ModoJuego;
import accdat.papergames.Modelo.Persistencia.Plataforma;
import accdat.papergames.Modelo.Persistencia.Videojuego;
import accdat.papergames.Vista.InterfazVista;
import accdat.papergames.Vista.VentanaCreateVideojuego;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rezzt
 */
public class ControladorCreacion implements ActionListener {
  private InterfazVista vista;
  private final HelperOperaciones modeloOperaciones;
  private final ModeloService modeloServicios;
  private final FiltrosJPAController modeloFiltros;
  private Videojuego modVideojuego;
  
 //------------------------------------------------------------------------------------------>
  public ControladorCreacion (InterfazVista inputVIsta) {
    this.vista = inputVIsta;
    this.modeloOperaciones = new HelperOperaciones();
    this.modeloServicios = new ModeloService();
    this.modeloFiltros = new FiltrosJPAController();
  }
  
 //------------------------------------------------------------------------------------------>
  @Override
  public void actionPerformed(ActionEvent evento) {
    switch (evento.getActionCommand()) {
      case InterfazVista.OPERACION_CREAR_VIDEOJUEGO -> {
        modificarVideojuego(vista.getVideojuegoSelected());
      }
    }
  }
  
  //------------------------------------------------------------------------------------>
   // metodos publicos | complementarios ->
    // metodo | cargarVideojuegos ->
  public List<Videojuego> cargarVideojuegos () {
    return this.modeloOperaciones.devolverListaVideojuegos();
  }
  public List<String> cargarNombresVideojuegos () {
    List<String> listaVideojuegos = new ArrayList<>();
    for (Videojuego aux : cargarVideojuegos()) {
      listaVideojuegos.add(aux.getTitulo());
    }
    
    
    return listaVideojuegos;
  }
  
    // metodo | cargarPlataformas ->
  public List<Plataforma> cargarPlataformas () {
    return this.modeloOperaciones.devolverListaPlataformas();
  }
  public List<String> cargarNombresPlataformas () {
    List<String> listaPlataformas = new ArrayList<>();
    for (Plataforma aux : cargarPlataformas()) {
      listaPlataformas.add(aux.getNombrePlataforma());
    }
      
    return listaPlataformas;
  }
  
    // metodo | cargarGeneros ->
  public List<Genero> cargarGeneros () {
    return this.modeloOperaciones.devolverListaGeneros();
  }
  
  public List<String> cargarNombresGeneros () {
    List<String> listaGeneros = new ArrayList<>();
    for (Genero aux : cargarGeneros()) {
      listaGeneros.add(aux.getNombreGenero());
    }
    
    return listaGeneros;
  }
  
    // metodo | cargarModosJuego ->
  public List<ModoJuego> cargarModosJuego () {
    return this.modeloOperaciones.devolverListaModosJuego();
  }
  public List<String> cargarNombresModoJuego () {
    List<String> listaModosJuego = new ArrayList<>();
    for (ModoJuego aux : cargarModosJuego()) {
      listaModosJuego.add(aux.getNombreModoJuego());
    }
    
    return listaModosJuego;
  }
  public List<Integer> cargarPEGIs () {
    List<Integer> listaPEGIs = new ArrayList<>();
    for (Short aux : this.modeloOperaciones.listaCompletaPEGI()) {
      listaPEGIs.add(Integer.valueOf(aux));
    }
    
    return listaPEGIs;
  }
  
  public void modificarVideojuego (Videojuego inputVideojuego) {
    modeloServicios.modificarVideojuego(inputVideojuego);
  }
  
  public void crearNuevoVideojuego (String inputTitulo, String inputDescripcion, short inputAnioSalida, 
          short inputPegi, String inputGenero, List<Plataforma> inputPlataformas, List<ModoJuego> inputModosJuego) {
    this.modeloOperaciones.insertarVideojuego(inputTitulo, inputDescripcion, inputAnioSalida, inputPegi, inputGenero, inputPlataformas, inputModosJuego);
  }
  
  public void eliminarVideojuego (Videojuego inputVideojuego) {
    modeloServicios.borraVideojuego(inputVideojuego);
  }
  
  public void rellenarDatos(InterfazVista inputVista) {
    List<Videojuego> listaVideojuegos = cargarVideojuegos();
    inputVista.agregarVisores(listaVideojuegos);
  }
  
  public Genero encontrarGenero(String inputNombre) {
    return modeloOperaciones.buscarGeneroPorNombre(inputNombre);
  }
  
  public Plataforma encontrarPlataforma (String inputNombre) {
    return modeloOperaciones.buscarPlataformaPorNombre(inputNombre);
  }
  
  public ModoJuego encontrarModoJuego (String inputNombre) {
    return modeloOperaciones.buscarModoJuegoPorNombre(inputNombre);
  }
  
  public Videojuego encontrarVideojuego (Long inputId) {
    return modeloServicios.listarUnVideojuego(inputId);
  }
}
