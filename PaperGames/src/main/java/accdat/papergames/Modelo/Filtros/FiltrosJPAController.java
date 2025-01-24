/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package accdat.papergames.Modelo.Filtros;

import accdat.papergames.Modelo.Controllers.DlcJpaController;
import accdat.papergames.Modelo.Controllers.VideojuegoJpaController;
import accdat.papergames.Modelo.Persistencia.Dlc;
import accdat.papergames.Modelo.Persistencia.ModoJuego;
import accdat.papergames.Modelo.Persistencia.Plataforma;
import accdat.papergames.Modelo.Persistencia.Videojuego;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author JGC by Juan Garcia Cazallas
 * version 1.0
 * created on 11 ene 2025
 */
public class FiltrosJPAController {
   // Constantes y atributos ->
  private static EntityManagerFactory emFactory;
  private static FiltrosJPAController instace;
  
  private VideojuegoJpaController videojuegoController;
  private DlcJpaController dlcController;
 
 //------------------------------------------------------------------------------->
   // constructores ->
  public FiltrosJPAController () {
    try {
      // Intenta conectar al servidor ObjectDB
      emFactory = Persistence.createEntityManagerFactory("objectdb://localhost/proyecto.odb;user=admin;password=admin");
      System.out.println("Conexión establecida con el servidor ObjectDB.");
    } catch (Exception e) {
      // Si falla, usa la versión embebida
      System.err.println("No se pudo conectar al servidor ObjectDB. " +
              "Se utilizará la base de datos embebida. Error: " + e.getMessage());
      emFactory = Persistence.createEntityManagerFactory("./db/proyecto.odb");
    }
    videojuegoController = new VideojuegoJpaController(emFactory);
    dlcController = new DlcJpaController(emFactory);
  }
  
  public static FiltrosJPAController getInstance () {
    if (instace == null) {
      instace = new FiltrosJPAController();
    }
    return instace;
  }
  
  private void abrirConexion () {
    try {
      // Intenta conectar al servidor ObjectDB
      emFactory = Persistence.createEntityManagerFactory("objectdb://localhost/proyecto.odb;user=admin;password=admin");
      System.out.println("Conexión establecida con el servidor ObjectDB.");
    } catch (Exception e) {
      // Si falla, usa la versión embebida
      System.err.println("No se pudo conectar al servidor ObjectDB. " +
              "Se utilizará la base de datos embebida. Error: " + e.getMessage());
      emFactory = Persistence.createEntityManagerFactory("./db/proyecto.odb");
    }
  }
  private void cerrarConexion() {
    emFactory.close();
  }
  
 //------------------------------------------------------------------------------->
   // metodos publicos | consultas de videojuego ->
    // metodo publico | consultaVideojuegoPorNombre =>
  public List<Videojuego> consultaVideojuegoPorNombre (String inputTitulo) {
    List<Videojuego> listaVideojuegos = new ArrayList<>();
    try {
      listaVideojuegos = videojuegoController.findVideojuegoByTitulo(inputTitulo);
    } catch (Exception ex) {
      ex.printStackTrace();
      listaVideojuegos = null;
    } finally {
      cerrarConexion();
    }
    
    return  listaVideojuegos;
  }
  
    // metodo publico | consultaVideojuegoPorAnio =>
  public List<Videojuego> consultaVideojuegoPorAnio (Integer inputAnioMin, Integer inputAnioMax) {
    List<Videojuego> listaVideojuegos = new ArrayList<>();
    abrirConexion();
    List<Videojuego> listaVideojuegosDB = videojuegoController.findVideojuegoEntities();
    
    for (Videojuego auxV : listaVideojuegosDB) {
      if ((auxV.getAño() >= inputAnioMin) && (auxV.getAño() <= inputAnioMax)) {
        listaVideojuegos.add(auxV);
      }
    }
    
    return  listaVideojuegos;
  }
  
    // metodo publico | consultaVideojuegoPorPlataforma =>
  public List<Videojuego> consultaVideojuegoPorPlataforma (List<String> inputPlataformas) {
    List<Videojuego> listaVideojuegos = new ArrayList<>();
    abrirConexion();
    List<Videojuego> listaVideojuegosDB = videojuegoController.findVideojuegoEntities();
    cerrarConexion();
    
    for (Videojuego auxV : listaVideojuegosDB) {
      for (String auxP : inputPlataformas) {
        if (auxV.getPlataformaCollection().contains(new Plataforma(auxP))) {
          listaVideojuegos.add(auxV);
        }
      }
    }
    
    return  listaVideojuegos;
  }
  
    // metodo publico | consultaVideojuegoPorModoJuego =>
  public List<Videojuego> consultaVideojuegoPorModoJuego (List<String> inputModosJuego) {
    List<Videojuego> listaVideojuegos = new ArrayList<>();
    abrirConexion();
    List<Videojuego> listaVideojuegosDB = videojuegoController.findVideojuegoEntities();
    cerrarConexion();
    
    for (Videojuego auxV : listaVideojuegosDB) {
      for (String auxMJ : inputModosJuego) {
        if (auxV.getModoJuegoCollection().contains(new ModoJuego(auxMJ))) {
          listaVideojuegos.add(auxV);
        }
      }
    }
    
    return  listaVideojuegos;
  }
  
    // metodo publico | consultaVideojuegoPorPEGI =>
  public List<Videojuego> consultaVideojuegoPorPEGI (List<Integer> inputPEGI) {
    List<Videojuego> listaVideojuegos = new ArrayList<>();
    abrirConexion();
    List<Videojuego> listaVideojuegosDB = videojuegoController.findVideojuegoEntities();
    cerrarConexion();
    
    for (Videojuego auxV : listaVideojuegosDB) {
      for (Integer auxP : inputPEGI) {
        if (auxV.getPegi() == auxP) {
          listaVideojuegos.add(auxV);
        }
      }
    }

    return  listaVideojuegos;
  }
  
 //------------------------------------------------------------------------------->
   // metodos publicos | consultas de dlc ->
    // metodo publico | consultaDLCPorNombre =>
  public List<Dlc> consultaDLCPorNombre (String inputNombreDLC) {
    List<Dlc> listaDlc = new ArrayList<>();
    try {
      listaDlc = dlcController.findDLCByNombre(inputNombreDLC);
    } catch (Exception ex) {
      ex.printStackTrace();
      listaDlc = null;
    } finally {
      cerrarConexion();
    }
    
    return listaDlc;
  }
  
    // metodo publico | consultaDLCPorNombre =>
  public List<Dlc> consultaDLCPorPrecio (Integer inputPrecioMin, Integer inputPrecioMax) {
    List<Dlc> listaDlc = new ArrayList<>();
    try {
      listaDlc = dlcController.findDLCByPrecioRange(inputPrecioMin, inputPrecioMax);
    } catch (Exception ex) {
      ex.printStackTrace();
      listaDlc = null;
    } finally {
      cerrarConexion();
    }
    
    return listaDlc;
  }
}
