/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accdat.papergames.Modelo.Controllers;

import accdat.papergames.Modelo.Persistencia.Genero;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import accdat.papergames.Modelo.Persistencia.Videojuego;
import accdat.papergames.exceptions.NonexistentEntityException;
import accdat.papergames.exceptions.PreexistingEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rezzt
 */
public class GeneroJpaController implements Serializable {

  public GeneroJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Genero genero) throws PreexistingEntityException, Exception {
    if (genero.getVideojuegoCollection() == null) {
      genero.setVideojuegoCollection(new ArrayList<Videojuego>());
    }
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      List<Videojuego> attachedVideojuegoCollection = new ArrayList<Videojuego>();
      for (Videojuego videojuegoCollectionVideojuegoToAttach : genero.getVideojuegoCollection()) {
        videojuegoCollectionVideojuegoToAttach = em.getReference(videojuegoCollectionVideojuegoToAttach.getClass(), videojuegoCollectionVideojuegoToAttach.getIdVideojuego());
        attachedVideojuegoCollection.add(videojuegoCollectionVideojuegoToAttach);
      }
      genero.setVideojuegoCollection(attachedVideojuegoCollection);
      em.persist(genero);
      for (Videojuego videojuegoCollectionVideojuego : genero.getVideojuegoCollection()) {
        Genero oldNombreGeneroOfVideojuegoCollectionVideojuego = videojuegoCollectionVideojuego.getNombreGenero();
        videojuegoCollectionVideojuego.setNombreGenero(genero);
        videojuegoCollectionVideojuego = em.merge(videojuegoCollectionVideojuego);
        if (oldNombreGeneroOfVideojuegoCollectionVideojuego != null) {
          oldNombreGeneroOfVideojuegoCollectionVideojuego.getVideojuegoCollection().remove(videojuegoCollectionVideojuego);
          oldNombreGeneroOfVideojuegoCollectionVideojuego = em.merge(oldNombreGeneroOfVideojuegoCollectionVideojuego);
        }
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      if (findGenero(genero.getNombreGenero()) != null) {
        throw new PreexistingEntityException("Genero " + genero + " already exists.", ex);
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Genero genero) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Genero persistentGenero = em.find(Genero.class, genero.getNombreGenero());
      List<Videojuego> videojuegoCollectionOld = persistentGenero.getVideojuegoCollection();
      List<Videojuego> videojuegoCollectionNew = genero.getVideojuegoCollection();
      List<Videojuego> attachedVideojuegoCollectionNew = new ArrayList<Videojuego>();
      for (Videojuego videojuegoCollectionNewVideojuegoToAttach : videojuegoCollectionNew) {
        videojuegoCollectionNewVideojuegoToAttach = em.getReference(videojuegoCollectionNewVideojuegoToAttach.getClass(), videojuegoCollectionNewVideojuegoToAttach.getIdVideojuego());
        attachedVideojuegoCollectionNew.add(videojuegoCollectionNewVideojuegoToAttach);
      }
      videojuegoCollectionNew = attachedVideojuegoCollectionNew;
      genero.setVideojuegoCollection(videojuegoCollectionNew);
      genero = em.merge(genero);
      for (Videojuego videojuegoCollectionOldVideojuego : videojuegoCollectionOld) {
        if (!videojuegoCollectionNew.contains(videojuegoCollectionOldVideojuego)) {
          videojuegoCollectionOldVideojuego.setNombreGenero(null);
          videojuegoCollectionOldVideojuego = em.merge(videojuegoCollectionOldVideojuego);
        }
      }
      for (Videojuego videojuegoCollectionNewVideojuego : videojuegoCollectionNew) {
        if (!videojuegoCollectionOld.contains(videojuegoCollectionNewVideojuego)) {
          Genero oldNombreGeneroOfVideojuegoCollectionNewVideojuego = videojuegoCollectionNewVideojuego.getNombreGenero();
          videojuegoCollectionNewVideojuego.setNombreGenero(genero);
          videojuegoCollectionNewVideojuego = em.merge(videojuegoCollectionNewVideojuego);
          if (oldNombreGeneroOfVideojuegoCollectionNewVideojuego != null && !oldNombreGeneroOfVideojuegoCollectionNewVideojuego.equals(genero)) {
            oldNombreGeneroOfVideojuegoCollectionNewVideojuego.getVideojuegoCollection().remove(videojuegoCollectionNewVideojuego);
            oldNombreGeneroOfVideojuegoCollectionNewVideojuego = em.merge(oldNombreGeneroOfVideojuegoCollectionNewVideojuego);
          }
        }
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        String id = genero.getNombreGenero();
        if (findGenero(id) == null) {
          throw new NonexistentEntityException("The genero with id " + id + " no longer exists.");
        }
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void destroy(String id) throws NonexistentEntityException {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Genero genero;
      try {
        genero = em.getReference(Genero.class, id);
        genero.getNombreGenero();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The genero with id " + id + " no longer exists.", enfe);
      }
      List<Videojuego> videojuegoCollection = genero.getVideojuegoCollection();
      for (Videojuego videojuegoCollectionVideojuego : videojuegoCollection) {
        videojuegoCollectionVideojuego.setNombreGenero(null);
        videojuegoCollectionVideojuego = em.merge(videojuegoCollectionVideojuego);
      }
      em.remove(genero);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Genero> findGeneroEntities() {
    EntityManager em = getEntityManager();
    List<Genero> generos = null;
    try {
        em.getTransaction().begin(); // Iniciar transacción solo si es necesario
        generos = em.createQuery("SELECT g FROM Genero g", Genero.class).getResultList();
        em.getTransaction().commit(); // Asegurar commit
    } catch (Exception e) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); // Evitar transacción bloqueada
        }
        e.printStackTrace();
    } finally {
        if (em != null && em.isOpen()) {
            em.close(); // Cerrar el EntityManager
        }
    }
    return generos;
  }


  public List<Genero> findGeneroEntities(int maxResults, int firstResult) {
      EntityManager em = getEntityManager();
      em.getTransaction().begin();
      try {
          List<Genero> result = findGeneroEntities(false, maxResults, firstResult);
          em.getTransaction().commit();
          return result;
      } finally {
          em.close();
      }
  }

  private List<Genero> findGeneroEntities(boolean all, int maxResults, int firstResult) {
      EntityManager em = getEntityManager();
      try {
          CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
          cq.select(cq.from(Genero.class));
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

  public Genero findGenero(String nombreGenero) {
    EntityManager em = getEntityManager();
    Genero genero = null;
    
    try {
        em.getTransaction().begin();
        genero = em.createQuery("SELECT g FROM Genero g WHERE g.nombreGenero = :nombre", Genero.class)
                   .setParameter("nombre", nombreGenero)
                   .getSingleResult();
        em.getTransaction().commit();
    } catch (Exception e) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        e.printStackTrace();
    } finally {
        em.close();
    }
    
    return genero;
  }


  public int getGeneroCount() {
      EntityManager em = getEntityManager();
      em.getTransaction().begin();
      try {
          CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
          Root<Genero> rt = cq.from(Genero.class);
          cq.select(em.getCriteriaBuilder().count(rt));
          Query q = em.createQuery(cq);
          int count = ((Long) q.getSingleResult()).intValue();
          em.getTransaction().commit();
          return count;
      } finally {
          em.close();
      }
  }

  
}
