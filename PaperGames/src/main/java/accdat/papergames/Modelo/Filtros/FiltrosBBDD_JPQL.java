/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accdat.papergames.Modelo.Filtros;

import accdat.papergames.Modelo.Persistencia.Dlc;
import accdat.papergames.Modelo.Persistencia.Plataforma;
import accdat.papergames.Modelo.Persistencia.Videojuego;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author rezzt
 */
public class FiltrosBBDD_JPQL {
   // constantes & atributos ->
  static EntityManagerFactory emFactory;
  static EntityManager entityManager;
  private static FiltrosBBDD_JPQL instace;
  
 //------------------------------------------------------------------------------->
   // constructores ->
  public FiltrosBBDD_JPQL () {}
  public static FiltrosBBDD_JPQL getInstance () {
    if (instace == null) {
      instace = new FiltrosBBDD_JPQL();
    }
    return instace;
  }
  
 //------------------------------------------------------------------------------->
   // metodos privados | abrir & cerrar factory ->
  private void abrirFactory () {
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
    entityManager = emFactory.createEntityManager();
  }
  
  private void cerrarFactory () {
    entityManager.close();
    emFactory.close();
  }
  
 //------------------------------------------------------------------------------->
   // metodos publicos | consultas de videojuegos ->
    // metodo publico | consultaVideojuegoPorNombre =>
  public List<Videojuego> consultaVideojuegoPorNombre(String inputTitulo) {
      String jpql = "SELECT v FROM Videojuego v WHERE LOWER(v.titulo) LIKE LOWER(:titulo)";
      return entityManager.createQuery(jpql, Videojuego.class)
               .setParameter("titulo", "%" + inputTitulo + "%")
               .getResultList();
  }

  
    // metodo publico | consultaVideojuegoPorAio =>
  public List<Videojuego> consultaVideojuegoPorAnio(Integer inputAnioMin, Integer inputAnioMax) {
      String jpql = "SELECT v FROM Videojuego v WHERE v.año BETWEEN :anioMin AND :anioMax";
      return entityManager.createQuery(jpql, Videojuego.class)
               .setParameter("anioMin", inputAnioMin)
               .setParameter("anioMax", inputAnioMax)
               .getResultList();
  }

  
     /**
     * Consulta Videojuegos por Plataformas.
     *
     * @param inputPlataformas Lista de nombres de plataformas a filtrar.
     * @return Lista de Videojuegos que coinciden con al menos una de las plataformas.
     */
    public List<Videojuego> consultaVideojuegoPorPlataforma(List<String> inputPlataformas) {
      if (inputPlataformas == null || inputPlataformas.isEmpty()) {
        return Collections.emptyList();
      }

      String jpql = "SELECT DISTINCT v FROM Videojuego v " +
                    "JOIN v.plataformaCollection p " +
                    "WHERE p.nombrePlataforma IN :plataformas";

      try {
        return entityManager.createQuery(jpql, Videojuego.class)
                            .setParameter("plataformas", inputPlataformas)
                            .getResultList();
      } catch (Exception e) {
        return Collections.emptyList();
      }
    }

    /**
     * Consulta Videojuegos por Modos de Juego.
     *
     * @param inputModosJuego Lista de nombres de modos de juego a filtrar.
     * @return Lista de Videojuegos que coinciden con al menos uno de los modos de juego.
     */
  public List<Videojuego> consultaVideojuegoPorModoJuego(List<String> inputModosJuego) {
      if (inputModosJuego == null || inputModosJuego.isEmpty()) {
        return Collections.emptyList();
      }

      String jpql = "SELECT DISTINCT v FROM Videojuego v " +
                    "JOIN v.modoJuegoCollection m " +
                    "WHERE m.nombreModoJuego IN :modosJuego";

      try {
        return entityManager.createQuery(jpql, Videojuego.class)
                            .setParameter("modosJuego", inputModosJuego)
                            .getResultList();
      } catch (Exception e) {
        return Collections.emptyList();
      }
  }

    // metodo publico | consultaVideojuegoPorPEGI =>
  public List<Videojuego> consultaVideojuegoPorPEGI(List<Integer> inputPEGI) {
      String jpql = "SELECT v FROM Videojuego v WHERE v.pegi IN :pegi";
      return entityManager.createQuery(jpql, Videojuego.class)
               .setParameter("pegi", inputPEGI)
               .getResultList();
  }

  
 //------------------------------------------------------------------------------->
   // metodos publicos | consultas de dlc ->
    // metodo publico | consultaDLCPorNombre =>
  public List<Dlc> consultaDLCPorNombre (String inputNombreDLC) {
    CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Dlc> consulta = cBuilder.createQuery(Dlc.class);
    Root<Dlc> rootQuery = consulta.from(Dlc.class);
    
    consulta.select(rootQuery).where(cBuilder.like(rootQuery.get("titulo"), "%" + inputNombreDLC + "%"));
    List<Dlc> listaDLC = entityManager.createQuery(consulta).setLockMode(LockModeType.PESSIMISTIC_WRITE).getResultList();
    return listaDLC;
  }
  
    // metodo publico | consultaDLCPorPrecio =>
  public List<Dlc> consultaDLCPorPrecio (Integer inputPrecioMin, Integer inputPrecioMax) {
    CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Dlc> consulta = cBuilder.createQuery(Dlc.class);
    Root<Dlc> rootQuery = consulta.from(Dlc.class);
    
    Predicate filtro = cBuilder.conjunction();
    if (inputPrecioMin != null) {
      filtro = cBuilder.and(filtro, cBuilder.greaterThanOrEqualTo(rootQuery.get("precio"), inputPrecioMin));
    }
    
    if (inputPrecioMax != null) {
      filtro = cBuilder.and(filtro, cBuilder.lessThanOrEqualTo(rootQuery.get("precio"), inputPrecioMax));
    }
    
    consulta.select(rootQuery).where(filtro);
    List<Dlc> listaDLC = entityManager.createQuery(consulta).setLockMode(LockModeType.PESSIMISTIC_WRITE).getResultList();
    return listaDLC;
  }
  
 //----------------------------------------------------------------------------------------->
   // metodos complementarios ->
    // metodo complementario | actualizarAñoVideojuegoPorAño =>
  public int actualizarAnioVideojuegoPorAnio (int inputAnioActual, int inputAnioNuevo) {
    abrirFactory();
    entityManager.getTransaction().begin();
    
    int actualizados = entityManager.createQuery(
            "UPDATE Videojuego v SET v.año = :nuevoAÑO WHERE v.año = :actualAÑO"
    ).setParameter("nuevoAÑO", inputAnioNuevo).setParameter("actualAÑO", inputAnioActual).executeUpdate();
    
    entityManager.getTransaction().commit();
    cerrarFactory();
    return actualizados;
  }
  
    // metodo complementario | reasignarVideojuegosDePlataformas =>
  public int reasignarVideojuegosDePlataformas (String inputNombrePlataformaEliminar) {
    abrirFactory();
    entityManager.getTransaction().begin();
    
    int numRegistrosActualizados = 0;
    
    try {
      String plataformaNoAsignada = "No Asignada";
      Long idPlataformaNoAsignada = null;
      
      List<Long> plataformasNoAsignadas = entityManager.createQuery(
              "SELECT p.id FROM Plataforma p WHERE p.nombre = :nombre", Long.class
      ).setParameter("nombre", inputNombrePlataformaEliminar).setLockMode(LockModeType.PESSIMISTIC_WRITE).getResultList();
      
      if (plataformasNoAsignadas.isEmpty()) {
        Plataforma nuevaPlataforma = new Plataforma();
        nuevaPlataforma.setNombrePlataforma(plataformaNoAsignada);
        entityManager.persist(nuevaPlataforma);
        entityManager.flush();
        idPlataformaNoAsignada = plataformasNoAsignadas.get(0);
      } else {
        idPlataformaNoAsignada = plataformasNoAsignadas.get(0);
      }
      
      List<Videojuego> videojuegos = entityManager.createQuery(
              "SELECT v FROM Videojuego v WHERE v.plataforma.nombre = :plataformaEliminar", Videojuego.class
      ).setParameter("plataformaEliminar", inputNombrePlataformaEliminar).setLockMode(LockModeType.PESSIMISTIC_WRITE).getResultList();
    
      int actualizados = entityManager.createQuery(
              "UPDATE Videojuego v SET v.plataforma.id = :nuevaPlataformaId WHERE v.plataforma.nombre = :plataformaEliminar"
      )
      .setParameter("nuevaPlataformaId", idPlataformaNoAsignada)
        .setParameter("plataformaEliminar", inputNombrePlataformaEliminar)
        .executeUpdate();
      
      entityManager.createQuery(
              "DELETE FROM Plataforma p WHERE p.nombre = :plataformaEliminar"
      ).setParameter("plataformaEliminar", inputNombrePlataformaEliminar).executeUpdate();
      
      entityManager.getTransaction().commit();
      numRegistrosActualizados = actualizados;
    } catch (Exception ex) {
      entityManager.getTransaction().rollback();
      ex.printStackTrace();
    } finally {
      cerrarFactory();
    }
    
    return numRegistrosActualizados;
  }
  
public List<Videojuego> consultaVideojuegoPorGenero(List<String> nombreGeneros) {
    abrirFactory(); 
    entityManager.getTransaction().begin();
    
    List<Videojuego> listaVideojuegos = entityManager.createQuery(
        "SELECT v FROM Videojuego v WHERE v.nombreGenero.nombreGenero IN :generos", Videojuego.class)
        .setParameter("generos", nombreGeneros)
        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
        .getResultList();
    
    entityManager.getTransaction().commit();
    
    return listaVideojuegos;
}
}
