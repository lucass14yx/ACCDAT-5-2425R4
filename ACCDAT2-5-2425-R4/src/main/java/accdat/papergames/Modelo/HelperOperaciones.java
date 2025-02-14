/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accdat.papergames.Modelo;

import accdat.papergames.Modelo.Controllers.GeneroJpaController;
import accdat.papergames.Modelo.Controllers.ModoJuegoJpaController;
import accdat.papergames.Modelo.Controllers.PlataformaJpaController;
import accdat.papergames.Modelo.Controllers.VideojuegoJpaController;
import accdat.papergames.Modelo.Persistencia.Videojuego;
import accdat.papergames.Modelo.Persistencia.Plataforma;
import accdat.papergames.Modelo.Persistencia.Genero;
import accdat.papergames.Modelo.Persistencia.ModoJuego;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rezzt
 */
public class HelperOperaciones {
   // constantes & atributos ->
    // instacia | patron singleton =>
  static EntityManager entityManager;
  private static HelperOperaciones instance;
  ModeloService modeloServicio = new ModeloService();
  
    // variables necesarias =>
  private EntityManagerFactory emFactory;
  private VideojuegoJpaController vController;
  private PlataformaJpaController pController;
  private GeneroJpaController gController;
  private ModoJuegoJpaController mjController;
  
 //----------------------------------------------------------------------------------------->
   // constructores -> 
  public HelperOperaciones () {
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
    vController = new VideojuegoJpaController(emFactory);
    pController = new PlataformaJpaController(emFactory);
    gController = new GeneroJpaController(emFactory);
    mjController = new ModoJuegoJpaController(emFactory);
  }
  public static HelperOperaciones getInstance () {
    if (instance == null) {
      instance = new HelperOperaciones();
    }
    
    return instance;
  }
  
  private void abrirConexion() {
    boolean serverDisponible = false;

    // Intentar comprobar si el servidor ObjectDB está disponible en localhost:6136
    try (Socket socket = new Socket("localhost", 6136)) {
        serverDisponible = true;
        System.out.println("✅ Servidor ObjectDB disponible. Conectando...");
    } catch (Exception e) {
        System.err.println("⚠️ Servidor ObjectDB no disponible. Se usará la base de datos embebida.");
    }

    try {
        if (serverDisponible) {
            // Intentar conectar al servidor ObjectDB
            emFactory = Persistence.createEntityManagerFactory("objectdb://localhost:6136/proyecto.odb;user=admin;password=admin");
            System.out.println("✅ Conexión establecida con el servidor ObjectDB.");
        } else {
            // Usar la base de datos embebida si el servidor no está disponible
            emFactory = Persistence.createEntityManagerFactory("./db/proyecto.odb");
            System.out.println("✅ Usando base de datos embebida.");
        }
    } catch (Exception e) {
        System.err.println("❌ No se pudo establecer ninguna conexión. Error: " + e.getMessage());
    }

    // Verificar si la base de datos se inicializó correctamente
    if (emFactory != null) {
        entityManager = emFactory.createEntityManager();
    } else {
        System.err.println("❌ No se pudo inicializar EntityManagerFactory.");
    }
  }
  
  private void cerrarConexion () {
    emFactory.close();
  }
  
 //----------------------------------------------------------------------------------------->
   // metodos publico ->
    // metodo publico | devolverListaVideojuegos =>
  public List<Videojuego> devolverListaVideojuegos () {
    abrirConexion();
    List<Videojuego> listaCompletaVideojuegos = new ArrayList<>();
    
    try {
      listaCompletaVideojuegos = vController.findVideojuegoEntities();
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      cerrarConexion();
    }
    
    return listaCompletaVideojuegos;
  }
    // metodo publico | devolverListaPlataformas =>
  public List<Plataforma> devolverListaPlataformas () {
    abrirConexion();
    List<Plataforma> listaCompletaPlataformas = new ArrayList<>();
    try {
      listaCompletaPlataformas = pController.findPlataformaEntities();
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      cerrarConexion();
    }
    
    return listaCompletaPlataformas;
  }
    // metodo publico | devolverListaGeneros =>
  public List<Genero> devolverListaGeneros () {
    abrirConexion();
    List<Genero> listaCompletaGeneros = new ArrayList<>();
    try {
      listaCompletaGeneros = gController.findGeneroEntities();
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      cerrarConexion();
    }
    
    return listaCompletaGeneros;
  }
  
    // metodo publico | devolverListaModosJuego =>
  public List<ModoJuego> devolverListaModosJuego () {
    abrirConexion();
    List<ModoJuego> listaCompletaModosJuego = new ArrayList<>();
    
    try {
      listaCompletaModosJuego = mjController.findModoJuegoEntities();
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      cerrarConexion();
    }
    
    return listaCompletaModosJuego;
  }
  
    // metodo publico | devolverListaPEGIs =>
  public List<Short> listaCompletaPEGI () {
    abrirConexion();
    List<Short> listaCompletaPEGIs = new ArrayList<>();
    try {
      listaCompletaPEGIs = vController.obtenerListaPEGI();
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      cerrarConexion();
    }
    
    return listaCompletaPEGIs;
  }
 //----------------------------------------------------------------------------------------->
  
 //----------------------------------------------------------------------------------------->
  public void insertarVideojuego (String inputTitulo, String inputDescripcion, short inputAnioSalida, 
          short inputPegi, String inputGenero, List<Plataforma> inputPlataformas, List<ModoJuego> inputModosJuego) {
    Videojuego nuevoVideojuego = new Videojuego(Long.valueOf((long) 0));
    nuevoVideojuego.setTitulo(inputTitulo);
    nuevoVideojuego.setDescripcion(inputDescripcion);
    nuevoVideojuego.setAño(inputAnioSalida);
    nuevoVideojuego.setPegi(inputPegi);
    nuevoVideojuego.setNombreGenero(buscarGeneroPorNombre(inputGenero));
    nuevoVideojuego.setPlataformaCollection(inputPlataformas);
    nuevoVideojuego.setModoJuegoCollection(inputModosJuego);
    
    modeloServicio.insertaVideojuego(nuevoVideojuego);
  }
  
 //----------------------------------------------------------------------------------------->
  public Genero buscarGeneroPorNombre (String inputNombreGenero) {
    return gController.findGenero(inputNombreGenero);
  }
  public Plataforma buscarPlataformaPorNombre (String inputNombrePlataforma) {
    return pController.findPlataforma(inputNombrePlataforma);
  }
  public ModoJuego buscarModoJuegoPorNombre (String inputNombreModoJuego) {
    return mjController.findModoJuego(inputNombreModoJuego);
  }
}
