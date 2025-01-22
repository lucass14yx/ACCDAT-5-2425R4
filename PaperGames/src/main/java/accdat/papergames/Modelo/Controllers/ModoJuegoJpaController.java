/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accdat.papergames.Modelo.Controllers;

import accdat.papergames.Modelo.Controllers.exceptions.NonexistentEntityException;
import accdat.papergames.Modelo.Controllers.exceptions.PreexistingEntityException;
import accdat.papergames.Modelo.Persistencia.ModoJuego;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import accdat.papergames.Modelo.Persistencia.Videojuego;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.ArrayList;

import java.util.List;

/**
 *
 * @author rezzt
 */
public class ModoJuegoJpaController implements Serializable {

  public ModoJuegoJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(ModoJuego modoJuego) throws PreexistingEntityException, Exception {
    if (modoJuego.getVideojuegoCollection() == null) {
      modoJuego.setVideojuegoCollection(new ArrayList<Videojuego>());
    }
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      List<Videojuego> attachedVideojuegoCollection = new ArrayList<Videojuego>();
      for (Videojuego videojuegoCollectionVideojuegoToAttach : modoJuego.getVideojuegoCollection()) {
        videojuegoCollectionVideojuegoToAttach = em.getReference(videojuegoCollectionVideojuegoToAttach.getClass(), videojuegoCollectionVideojuegoToAttach.getIdVideojuego());
        attachedVideojuegoCollection.add(videojuegoCollectionVideojuegoToAttach);
      }
      modoJuego.setVideojuegoCollection(attachedVideojuegoCollection);
      em.persist(modoJuego);
      for (Videojuego videojuegoCollectionVideojuego : modoJuego.getVideojuegoCollection()) {
        videojuegoCollectionVideojuego.getModoJuegoCollection().add(modoJuego);
        videojuegoCollectionVideojuego = em.merge(videojuegoCollectionVideojuego);
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      if (findModoJuego(modoJuego.getNombreModoJuego()) != null) {
        throw new PreexistingEntityException("ModoJuego " + modoJuego + " already exists.", ex);
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(ModoJuego modoJuego) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      ModoJuego persistentModoJuego = em.find(ModoJuego.class, modoJuego.getNombreModoJuego());
      List<Videojuego> videojuegoCollectionOld = persistentModoJuego.getVideojuegoCollection();
      List<Videojuego> videojuegoCollectionNew = modoJuego.getVideojuegoCollection();
      List<Videojuego> attachedVideojuegoCollectionNew = new ArrayList<Videojuego>();
      for (Videojuego videojuegoCollectionNewVideojuegoToAttach : videojuegoCollectionNew) {
        videojuegoCollectionNewVideojuegoToAttach = em.getReference(videojuegoCollectionNewVideojuegoToAttach.getClass(), videojuegoCollectionNewVideojuegoToAttach.getIdVideojuego());
        attachedVideojuegoCollectionNew.add(videojuegoCollectionNewVideojuegoToAttach);
      }
      videojuegoCollectionNew = attachedVideojuegoCollectionNew;
      modoJuego.setVideojuegoCollection(videojuegoCollectionNew);
      modoJuego = em.merge(modoJuego);
      for (Videojuego videojuegoCollectionOldVideojuego : videojuegoCollectionOld) {
        if (!videojuegoCollectionNew.contains(videojuegoCollectionOldVideojuego)) {
          videojuegoCollectionOldVideojuego.getModoJuegoCollection().remove(modoJuego);
          videojuegoCollectionOldVideojuego = em.merge(videojuegoCollectionOldVideojuego);
        }
      }
      for (Videojuego videojuegoCollectionNewVideojuego : videojuegoCollectionNew) {
        if (!videojuegoCollectionOld.contains(videojuegoCollectionNewVideojuego)) {
          videojuegoCollectionNewVideojuego.getModoJuegoCollection().add(modoJuego);
          videojuegoCollectionNewVideojuego = em.merge(videojuegoCollectionNewVideojuego);
        }
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        String id = modoJuego.getNombreModoJuego();
        if (findModoJuego(id) == null) {
          throw new NonexistentEntityException("The modoJuego with id " + id + " no longer exists.");
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
      ModoJuego modoJuego;
      try {
        modoJuego = em.getReference(ModoJuego.class, id);
        modoJuego.getNombreModoJuego();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The modoJuego with id " + id + " no longer exists.", enfe);
      }
      List<Videojuego> videojuegoCollection = modoJuego.getVideojuegoCollection();
      for (Videojuego videojuegoCollectionVideojuego : videojuegoCollection) {
        videojuegoCollectionVideojuego.getModoJuegoCollection().remove(modoJuego);
        videojuegoCollectionVideojuego = em.merge(videojuegoCollectionVideojuego);
      }
      em.remove(modoJuego);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<ModoJuego> findModoJuegoEntities() {
      EntityManager em = getEntityManager();
      em.getTransaction().begin();
      try {
          List<ModoJuego> result = findModoJuegoEntities(true, -1, -1);
          em.getTransaction().commit();
          return result;
      } finally {
          em.close();
      }
  }

  public List<ModoJuego> findModoJuegoEntities(int maxResults, int firstResult) {
      EntityManager em = getEntityManager();
      em.getTransaction().begin();
      try {
          List<ModoJuego> result = findModoJuegoEntities(false, maxResults, firstResult);
          em.getTransaction().commit();
          return result;
      } finally {
          em.close();
      }
  }

  private List<ModoJuego> findModoJuegoEntities(boolean all, int maxResults, int firstResult) {
      EntityManager em = getEntityManager();
      try {
          CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
          cq.select(cq.from(ModoJuego.class));
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

  public ModoJuego findModoJuego(String id) {
      EntityManager em = getEntityManager();
      em.getTransaction().begin();
      try {
          ModoJuego result = em.find(ModoJuego.class, id);
          em.getTransaction().commit();
          return result;
      } finally {
          em.close();
      }
  }

  public int getModoJuegoCount() {
      EntityManager em = getEntityManager();
      em.getTransaction().begin();
      try {
          CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
          Root<ModoJuego> rt = cq.from(ModoJuego.class);
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
