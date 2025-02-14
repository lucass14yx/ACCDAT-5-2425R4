/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accdat.papergames.Modelo.Controllers;

import accdat.papergames.Modelo.Controllers.exceptions.NonexistentEntityException;
import accdat.papergames.Modelo.Persistencia.Dlc;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import accdat.papergames.Modelo.Persistencia.Videojuego;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 *
 * @author rezzt
 */
public class DlcJpaController implements Serializable {

  public DlcJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Dlc dlc) {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Videojuego idVideojuego = dlc.getIdVideojuego();
      if (idVideojuego != null) {
        idVideojuego = em.getReference(idVideojuego.getClass(), idVideojuego.getIdVideojuego());
        dlc.setIdVideojuego(idVideojuego);
      }
      em.persist(dlc);
      if (idVideojuego != null) {
        idVideojuego.getDlcCollection().add(dlc);
        idVideojuego = em.merge(idVideojuego);
      }
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Dlc dlc) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Dlc persistentDlc = em.find(Dlc.class, dlc.getIdDlc());
      Videojuego idVideojuegoOld = persistentDlc.getIdVideojuego();
      Videojuego idVideojuegoNew = dlc.getIdVideojuego();
      if (idVideojuegoNew != null) {
        idVideojuegoNew = em.getReference(idVideojuegoNew.getClass(), idVideojuegoNew.getIdVideojuego());
        dlc.setIdVideojuego(idVideojuegoNew);
      }
      dlc = em.merge(dlc);
      if (idVideojuegoOld != null && !idVideojuegoOld.equals(idVideojuegoNew)) {
        idVideojuegoOld.getDlcCollection().remove(dlc);
        idVideojuegoOld = em.merge(idVideojuegoOld);
      }
      if (idVideojuegoNew != null && !idVideojuegoNew.equals(idVideojuegoOld)) {
        idVideojuegoNew.getDlcCollection().add(dlc);
        idVideojuegoNew = em.merge(idVideojuegoNew);
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        Long id = dlc.getIdDlc();
        if (findDlc(id) == null) {
          throw new NonexistentEntityException("The dlc with id " + id + " no longer exists.");
        }
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void destroy(Long id) throws NonexistentEntityException {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Dlc dlc;
      try {
        dlc = em.getReference(Dlc.class, id);
        dlc.getIdDlc();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The dlc with id " + id + " no longer exists.", enfe);
      }
      Videojuego idVideojuego = dlc.getIdVideojuego();
      if (idVideojuego != null) {
        idVideojuego.getDlcCollection().remove(dlc);
        idVideojuego = em.merge(idVideojuego);
      }
      em.remove(dlc);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Dlc> findDlcEntities() {
      EntityManager em = getEntityManager();
      em.getTransaction().begin();
      try {
          List<Dlc> result = findDlcEntities(true, -1, -1);
          em.getTransaction().commit();
          return result;
      } finally {
          em.close();
      }
  }

  public List<Dlc> findDlcEntities(int maxResults, int firstResult) {
      EntityManager em = getEntityManager();
      em.getTransaction().begin();
      try {
          List<Dlc> result = findDlcEntities(false, maxResults, firstResult);
          em.getTransaction().commit();
          return result;
      } finally {
          em.close();
      }
  }

  private List<Dlc> findDlcEntities(boolean all, int maxResults, int firstResult) {
      EntityManager em = getEntityManager();
      try {
          CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
          cq.select(cq.from(Dlc.class));
          Query q = em.createQuery(cq);
          if (!all) {
              q.setMaxResults(maxResults);
              q.setFirstResult(firstResult);
          }
          return q.getResultList();
      } finally {
          em.close();
      }
  }

  public Dlc findDlc(Long id) {
    EntityManager em = getEntityManager();
    em.getTransaction().begin();
    try {
      Dlc dlc = em.find(Dlc.class, id);
      em.getTransaction().commit();
      return dlc;
      
    } finally {
      em.close();
    }
  }

  public int getDlcCount() {
      EntityManager em = getEntityManager();
      em.getTransaction().begin();
      try {
          CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
          Root<Dlc> rt = cq.from(Dlc.class);
          cq.select(em.getCriteriaBuilder().count(rt));
          Query q = em.createQuery(cq);
          int count = ((Long) q.getSingleResult()).intValue();
          em.getTransaction().commit();
          return count;
      } finally {
          em.close();
      }
  }

  public List<Dlc> findDLCByNombre(String nombre) {
      EntityManager em = getEntityManager();
      em.getTransaction().begin();
      try {
          CriteriaBuilder cBuilder = em.getCriteriaBuilder();
          CriteriaQuery<Dlc> consulta = cBuilder.createQuery(Dlc.class);
          Root<Dlc> rootQuery = consulta.from(Dlc.class);

          consulta.select(rootQuery).where(cBuilder.like(rootQuery.get("nombre"), "%" + nombre + "%"));

          List<Dlc> result = em.createQuery(consulta).getResultList();
          em.getTransaction().commit();
          return result;
      } finally {
          em.close();
      }
  }

  public List<Dlc> findDLCByPrecioRange(double precioMin, double precioMax) {
      EntityManager em = getEntityManager();
      em.getTransaction().begin();
      try {
          CriteriaBuilder cBuilder = em.getCriteriaBuilder();
          CriteriaQuery<Dlc> consulta = cBuilder.createQuery(Dlc.class);
          Root<Dlc> rootQuery = consulta.from(Dlc.class);

          consulta.select(rootQuery).where(cBuilder.between(rootQuery.get("precio"), precioMin, precioMax));

          List<Dlc> result = em.createQuery(consulta).getResultList();
          em.getTransaction().commit();
          return result;
      } finally {
          em.close();
      }
  }
}
