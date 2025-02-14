/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package accdat.papergames.Vista;

import accdat.papergames.Controlador.Controlador;
import accdat.papergames.Modelo.Persistencia.Videojuego;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public interface InterfazVista {
   // constantes & atributos ->
  static final String FILTRO_BUSQUEDA_NOMBRE = "filtro de campo de busqueda para el nombre";
  static final String FILTRO_ANIO_PUBLICACION = "filtro para minimo y maximo de anio de publicacion";
  static final String FILTRO_SELECT_PLATAFORMA = "filtro de seleccionar plataforma";
  static final String FILTRO_SELECT_PEGI = "filtro de seleccionar el pegi";
  static final String FILTRO_SELECT_MODO_JUEGO = "filtro de seleccionar el modo de juego";
  static final String OPERACION_MODIFICAR_VIDEOJUGO = "operacion de modificacion de videojuego";
  static final String OPERACION_CREAR_VIDEOJUEGO = "operacion de creacion de videojuego";
  static final String CARGAR_DATOS = "operacion de carga de datos";
  
 //-------------------------------------------------------------------------------------------------------------------->
  void agregarVisores(List<Videojuego> inputListaVideojuegos);  
  void setControlador(Controlador c);
  void arranca();
  Videojuego getVideojuegoSelected();
    
  void haceAlgo(String s);
}
