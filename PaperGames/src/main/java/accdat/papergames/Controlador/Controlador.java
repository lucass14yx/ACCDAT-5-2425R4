/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accdat.papergames.Controlador;

import accdat.papergames.Modelo.CollectData;
import accdat.papergames.Modelo.Filtros.FiltrosBBDD_JPQL;
import accdat.papergames.Modelo.Filtros.FiltrosJPAController;
import accdat.papergames.Modelo.HelperOperaciones;
import accdat.papergames.Modelo.ModeloService;
import accdat.papergames.Modelo.Persistencia.Genero;
import accdat.papergames.Modelo.Persistencia.ModoJuego;
import accdat.papergames.Modelo.Persistencia.Plataforma;
import accdat.papergames.Modelo.Persistencia.Videojuego;
import accdat.papergames.Vista.InterfazVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author USUARIO
 */
public class Controlador implements ActionListener{
  private final InterfazVista vista;
  private final HelperOperaciones modeloOperaciones;
  private final ModeloService modeloServicios;
  private final FiltrosJPAController modeloFiltros;
  private final FiltrosBBDD_JPQL modeloFiltrosJPQL;
  private final CollectData cargaDeDatos;


  public Controlador(InterfazVista inputVista, HelperOperaciones inputModeloOper, 
          ModeloService inputModeloServicios, FiltrosJPAController inputModeloFiltros, FiltrosBBDD_JPQL inputModeloFiltrosJPQL) {
    this.vista = inputVista;
    this.modeloOperaciones = inputModeloOper;
    this.modeloServicios = inputModeloServicios;
    this.modeloFiltros = inputModeloFiltros;
    this.modeloFiltrosJPQL = inputModeloFiltrosJPQL;
    this.vista.setControlador(this);
    this.vista.agregarVisores(cargarVideojuegos());
    this.cargaDeDatos = new CollectData();
  }
  
  public Controlador (InterfazVista inputVista) {
    this.vista = inputVista;
    this.modeloOperaciones = new HelperOperaciones();
    this.modeloFiltros = new FiltrosJPAController();
    this.modeloServicios = new ModeloService();
    this.modeloFiltrosJPQL = new FiltrosBBDD_JPQL();
    this.vista.setControlador(this);
    this.vista.agregarVisores(cargarVideojuegos());
    this.cargaDeDatos = new CollectData();
  }


  @Override
  public void actionPerformed(ActionEvent evento) {
    switch (evento.getActionCommand()){
      case InterfazVista.CARGAR_DATOS -> {
        this.cargaDeDatos.generarGeneros();
        this.cargaDeDatos.generarModosJuego();
        this.cargaDeDatos.generarPlataformas();
        this.cargaDeDatos.generarVideojuegos();
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
  
 //-------------------------------------------------------------------------------------------------------------->
  public List<Videojuego> recuperarJuegosFiltroPlataformas (List<String> inputListaFiltros) {
    List<Videojuego> listaVideojuegosFiltrada = new ArrayList<>();
    listaVideojuegosFiltrada = modeloFiltros.consultaVideojuegoPorPlataforma(inputListaFiltros);
    return listaVideojuegosFiltrada;
  }
  
  public List<Videojuego> recuperarJuegosFiltroGeneros (List<String> inputListaFiltros) {
    List<Videojuego> listaVideojuegosFiltrada = new ArrayList<>();
    listaVideojuegosFiltrada = modeloFiltrosJPQL.consultaVideojuegoPorGenero(inputListaFiltros);
    return listaVideojuegosFiltrada;
  }
  
  public List<Videojuego> recuperarJuegosFiltroModosJuego (List<String> inputListaFiltros) {
    List<Videojuego> listaVideojuegosFiltrada = new ArrayList<>();
    listaVideojuegosFiltrada = modeloFiltrosJPQL.consultaVideojuegoPorModoJuego(inputListaFiltros);
    return listaVideojuegosFiltrada;
  }
  
  public List<Videojuego> recuperarJuegosFiltroPEGI (List<Integer> inputListaFiltros) {
    List<Videojuego> listaVideojuegosFiltrada = new ArrayList<>();
    listaVideojuegosFiltrada = modeloFiltros.consultaVideojuegoPorPEGI(inputListaFiltros);
    return listaVideojuegosFiltrada;
  }
  
  public List<Videojuego> recuperarJuegosFiltroAnioMinMax (int inputAnioMin, int inputAnioMax) {
    List<Videojuego> listaVideojuegosFiltrada = new ArrayList<>();
    listaVideojuegosFiltrada = modeloFiltros.consultaVideojuegoPorAnio(inputAnioMin, inputAnioMax);
    return listaVideojuegosFiltrada;
  }
  
 //-------------------------------------------------------------------------------------------------------------->
  public void crearNuevoVideojuego (String inputTitulo, String inputDescripcion, short inputAnioSalida, 
          short inputPegi, String inputGenero, List<Plataforma> inputPlataformas, List<ModoJuego> inputModosJuego) {
    this.modeloOperaciones.insertarVideojuego(inputTitulo, inputDescripcion, inputAnioSalida, inputPegi, inputGenero, inputPlataformas, inputModosJuego);
  }
  
 //-------------------------------------------------------------------------------------------------------------->
  
}
