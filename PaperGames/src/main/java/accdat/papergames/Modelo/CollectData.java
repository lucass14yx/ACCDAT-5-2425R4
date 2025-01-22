/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accdat.papergames.Modelo;

import accdat.papergames.Modelo.Controllers.DlcJpaController;
import accdat.papergames.Modelo.Controllers.GeneroJpaController;
import accdat.papergames.Modelo.Controllers.ModoJuegoJpaController;
import accdat.papergames.Modelo.Controllers.PlataformaJpaController;
import accdat.papergames.Modelo.Controllers.VideojuegoJpaController;
import accdat.papergames.Modelo.Persistencia.Genero;
import accdat.papergames.Modelo.Persistencia.ModoJuego;
import accdat.papergames.Modelo.Persistencia.Plataforma;
import accdat.papergames.Modelo.Persistencia.Videojuego;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rezzt
 * utilizar solo la primera vez que se incie el proyecto, para cargar los datos en la bbdd
 */
public class CollectData {
   // constantes & variables ->
    // entityManager =>
  private static EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("accdat_PaperGames_jar_1.0-SNAPSHOTPU");
  
    // controladores persistencia =>
  private static DlcJpaController dlcController = new DlcJpaController(emFactory);
  private static GeneroJpaController genController = new GeneroJpaController(emFactory);
  private static ModoJuegoJpaController modoJuegoController = new ModoJuegoJpaController(emFactory);
  private static PlataformaJpaController platController = new PlataformaJpaController(emFactory);
  private static VideojuegoJpaController videojuegoController = new VideojuegoJpaController(emFactory);
  
  
 //------------------------------------------------------------------------------------------------->
   // metodo complementario | generarGeneros ->
  public void generarGeneros () {
    List<Genero> listaCrearGeneros = new ArrayList<>();
    int contInserts = 0;
    
    Genero genShooter = new Genero("Shooter");
    Genero genSandbox = new Genero("Sandbox");
    Genero genTerror = new Genero("Terror");
    Genero genMOBA = new Genero("MOBA");
    Genero genCarreras = new Genero("Carreras");
    
    listaCrearGeneros.add(genCarreras);
    listaCrearGeneros.add(genShooter);
    listaCrearGeneros.add(genSandbox);
    listaCrearGeneros.add(genTerror);
    listaCrearGeneros.add(genMOBA);
    
    for (Genero aux : listaCrearGeneros) {
      try {
        genController.create(aux);
        contInserts++;
      } catch (Exception ex) {
        Logger.getLogger(CollectData.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    System.out.println(" Traza -> Se han insertado " + contInserts + " generos.");
  }
  
  
  public void generarModosJuego () {
    List<ModoJuego> listaCrearModosJuego = new ArrayList<>();
    int contInserts = 0;
    
    ModoJuego mjMultijugador = new ModoJuego("Multijugador");
    ModoJuego mjSingleplayer = new ModoJuego("Singleplayer");
    ModoJuego mjCooperativo = new ModoJuego("Cooperativo");
    ModoJuego mjLocal = new ModoJuego("Local");
    ModoJuego mjSesionCompartida = new ModoJuego("Sesion Compartida");
    
    listaCrearModosJuego.add(mjMultijugador);
    listaCrearModosJuego.add(mjSingleplayer);
    listaCrearModosJuego.add(mjCooperativo);
    listaCrearModosJuego.add(mjLocal);
    listaCrearModosJuego.add(mjSesionCompartida);
    
    for (ModoJuego aux : listaCrearModosJuego) {
      try {
        modoJuegoController.create(aux);
        contInserts++;
      } catch (Exception ex) {
        Logger.getLogger(CollectData.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    System.out.println(" Traza -> Se han insertado " + contInserts + " modos de juego.");
  }
  
  
  public void generarPlataformas () {
    List<Plataforma> listaCrearPlataformas = new ArrayList<>();
    int contInserts = 0;
    
    Plataforma pWindows = new Plataforma("Windows", "PC");
    Plataforma pMax = new Plataforma("Mac", "PC");
    Plataforma pLinux = new Plataforma("Linux", "PC");
    Plataforma pPlaystation = new Plataforma("PlayStation", "Consola");
    Plataforma pXBox = new Plataforma("XBox", "Consola");
    Plataforma pAndroid = new Plataforma("Android", "Mobile");
    Plataforma pIos = new Plataforma("iOS", "Mobile");
    
    listaCrearPlataformas.add(pWindows);
    listaCrearPlataformas.add(pMax);
    listaCrearPlataformas.add(pLinux);
    listaCrearPlataformas.add(pPlaystation);
    listaCrearPlataformas.add(pXBox);
    listaCrearPlataformas.add(pAndroid);
    listaCrearPlataformas.add(pIos);
    
    try {
      for (Plataforma aux : listaCrearPlataformas) {
        platController.create(aux);
        contInserts++;
      }
    } catch (Exception ex) {
      Logger.getLogger(CollectData.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    System.out.println(" Traza -> Se han insertado " + contInserts + " plataformas.");
  }
  
  
  public void generarVideojuegos () {
    List<Videojuego> listaCrearVideojuegos = new ArrayList<>();
    int contInserts = 0;
    
    Videojuego minecraft = new Videojuego(toLong(0), "Minecraft", toShort(2011), toShort(7));
    Videojuego counterStrike = new Videojuego(toLong(0), "Counter Strike 2", toShort(2023), toShort(18));
    Videojuego leagueOL = new Videojuego(toLong(0), "League of Legends", toShort(2009), toShort(12));
    Videojuego valorant = new Videojuego(toLong(0), "VALORANT", toShort(2020), toShort(16));
    
    minecraft.setNombreGenero(genController.findGenero("Sandbox"));
    counterStrike.setNombreGenero(genController.findGenero("Shooter"));
    leagueOL.setNombreGenero(genController.findGenero("MOBA"));
    valorant.setNombreGenero(genController.findGenero("Shooter"));
    
    List<String> listaPlatMinecraft = Arrays.asList("Windows", "Mac", "Linux", "PlayStation", "XBox", "Android", "iOS");
    List<String> listaPlatCS = Arrays.asList("Windows", "Mac", "Linux");
    List<String> listaPlatLOL = Arrays.asList("Windows", "Mac");
    List<String> listaPlatValorant  = Arrays.asList("Windows");
    
    List<String> listaModoJuegoMinecraft = Arrays.asList("Multijugador", "Singleplayer", "Cooperativo", "Local", "Sesion Compartida");
    List<String> listaModoJuegoCS = Arrays.asList("Multijugador", "Cooperativo");
    List<String> listaModoJuegoLOL = Arrays.asList("Multijugador", "Cooperativo");
    List<String> listaModoJuegoValorant  = Arrays.asList("Multijugador", "Cooperativo");
    
    minecraft.setPlataformaCollection(agregarPlataformasJuego(minecraft, listaPlatMinecraft));
    counterStrike.setPlataformaCollection(agregarPlataformasJuego(counterStrike, listaPlatCS));
    leagueOL.setPlataformaCollection(agregarPlataformasJuego(leagueOL, listaPlatLOL));
    valorant.setPlataformaCollection(agregarPlataformasJuego(valorant, listaPlatValorant));
    
    minecraft.setModoJuegoCollection(agregarModosJuegoJuego(minecraft, listaModoJuegoMinecraft));
    counterStrike.setModoJuegoCollection(agregarModosJuegoJuego(counterStrike, listaModoJuegoCS));
    leagueOL.setModoJuegoCollection(agregarModosJuegoJuego(leagueOL, listaModoJuegoLOL));
    valorant.setModoJuegoCollection(agregarModosJuegoJuego(valorant, listaModoJuegoValorant));
    
    minecraft.setDescripcion("Juego de exploración y construcción en un mundo infinito, ideal para creatividad y supervivencia, solo o con amigos.");
    counterStrike.setDescripcion("Shooter táctico multijugador que redefine el combate en equipo con gráficos avanzados y jugabilidad precisa. Exclusivo para PC.");
    leagueOL.setDescripcion("MOBA competitivo donde equipos de 5 jugadores luchan para destruir la base enemiga usando campeones únicos.");
    valorant.setDescripcion("FPS táctico por equipos con agentes que poseen habilidades especiales. Perfecto para acción estratégica y competitiva.");
    
    listaCrearVideojuegos.add(minecraft);
    listaCrearVideojuegos.add(counterStrike);
    listaCrearVideojuegos.add(leagueOL);
    listaCrearVideojuegos.add(valorant);
    
    try {
      for (Videojuego aux : listaCrearVideojuegos) {
        videojuegoController.create(aux);
        contInserts++;
      }
    } catch (Exception ex) {
      Logger.getLogger(CollectData.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    System.out.println(" Traza -> Se han insertado " + contInserts + " videojuegos.");
  }
  
 //------------------------------------------------------------------------------------------------->
  private static Long toLong (int inputNum) {
    return Long.valueOf(inputNum);
  }
  private static short toShort (int inputNum) {
    return (short) inputNum;
  }
  
 //------------------------------------------------------------------------------------------------->
  private static List<Plataforma> agregarPlataformasJuego (Videojuego inputJuego, List<String> inputListaPlataforma) {
    List<Plataforma> listaPlataformas = new ArrayList<>();
    
    for (String aux : inputListaPlataforma) {
      Plataforma platAux = platController.findPlataforma(aux);
      if (platAux != null) {
        listaPlataformas.add(platAux);
      }
    }
    
    return listaPlataformas;
  }
  
  private static List<ModoJuego> agregarModosJuegoJuego (Videojuego inputJuego, List<String> inputListaModoJuego) {
    List<ModoJuego> listaModosJuego = new ArrayList<>();
    
    for (String aux : inputListaModoJuego) {
      ModoJuego platAux = modoJuegoController.findModoJuego(aux);
      if (platAux != null) {
        listaModosJuego.add(platAux);
      }
    }
    
    return listaModosJuego;
  }
}
