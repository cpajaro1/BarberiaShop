/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.NonexistentEntityException;
import Control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Barberia;
import Modelo.Barbero;
import Modelo.Vincunlacion;
import Modelo.VincunlacionPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author rcarrillo
 */
public class VincunlacionJpaController implements Serializable {

    public VincunlacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vincunlacion vincunlacion) throws PreexistingEntityException, Exception {
        if (vincunlacion.getVincunlacionPK() == null) {
            vincunlacion.setVincunlacionPK(new VincunlacionPK());
        }
        vincunlacion.getVincunlacionPK().setBarberoid(vincunlacion.getBarbero().getId());
        vincunlacion.getVincunlacionPK().setBarberiaid(vincunlacion.getBarberia().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barberia barberia = vincunlacion.getBarberia();
            if (barberia != null) {
                barberia = em.getReference(barberia.getClass(), barberia.getId());
                vincunlacion.setBarberia(barberia);
            }
            Barbero barbero = vincunlacion.getBarbero();
            if (barbero != null) {
                barbero = em.getReference(barbero.getClass(), barbero.getId());
                vincunlacion.setBarbero(barbero);
            }
            em.persist(vincunlacion);
            if (barberia != null) {
                barberia.getVincunlacionList().add(vincunlacion);
                barberia = em.merge(barberia);
            }
            if (barbero != null) {
                barbero.getVincunlacionList().add(vincunlacion);
                barbero = em.merge(barbero);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVincunlacion(vincunlacion.getVincunlacionPK()) != null) {
                throw new PreexistingEntityException("Vincunlacion " + vincunlacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vincunlacion vincunlacion) throws NonexistentEntityException, Exception {
        vincunlacion.getVincunlacionPK().setBarberoid(vincunlacion.getBarbero().getId());
        vincunlacion.getVincunlacionPK().setBarberiaid(vincunlacion.getBarberia().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vincunlacion persistentVincunlacion = em.find(Vincunlacion.class, vincunlacion.getVincunlacionPK());
            Barberia barberiaOld = persistentVincunlacion.getBarberia();
            Barberia barberiaNew = vincunlacion.getBarberia();
            Barbero barberoOld = persistentVincunlacion.getBarbero();
            Barbero barberoNew = vincunlacion.getBarbero();
            if (barberiaNew != null) {
                barberiaNew = em.getReference(barberiaNew.getClass(), barberiaNew.getId());
                vincunlacion.setBarberia(barberiaNew);
            }
            if (barberoNew != null) {
                barberoNew = em.getReference(barberoNew.getClass(), barberoNew.getId());
                vincunlacion.setBarbero(barberoNew);
            }
            vincunlacion = em.merge(vincunlacion);
            if (barberiaOld != null && !barberiaOld.equals(barberiaNew)) {
                barberiaOld.getVincunlacionList().remove(vincunlacion);
                barberiaOld = em.merge(barberiaOld);
            }
            if (barberiaNew != null && !barberiaNew.equals(barberiaOld)) {
                barberiaNew.getVincunlacionList().add(vincunlacion);
                barberiaNew = em.merge(barberiaNew);
            }
            if (barberoOld != null && !barberoOld.equals(barberoNew)) {
                barberoOld.getVincunlacionList().remove(vincunlacion);
                barberoOld = em.merge(barberoOld);
            }
            if (barberoNew != null && !barberoNew.equals(barberoOld)) {
                barberoNew.getVincunlacionList().add(vincunlacion);
                barberoNew = em.merge(barberoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                VincunlacionPK id = vincunlacion.getVincunlacionPK();
                if (findVincunlacion(id) == null) {
                    throw new NonexistentEntityException("The vincunlacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(VincunlacionPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vincunlacion vincunlacion;
            try {
                vincunlacion = em.getReference(Vincunlacion.class, id);
                vincunlacion.getVincunlacionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vincunlacion with id " + id + " no longer exists.", enfe);
            }
            Barberia barberia = vincunlacion.getBarberia();
            if (barberia != null) {
                barberia.getVincunlacionList().remove(vincunlacion);
                barberia = em.merge(barberia);
            }
            Barbero barbero = vincunlacion.getBarbero();
            if (barbero != null) {
                barbero.getVincunlacionList().remove(vincunlacion);
                barbero = em.merge(barbero);
            }
            em.remove(vincunlacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vincunlacion> findVincunlacionEntities() {
        return findVincunlacionEntities(true, -1, -1);
    }

    public List<Vincunlacion> findVincunlacionEntities(int maxResults, int firstResult) {
        return findVincunlacionEntities(false, maxResults, firstResult);
    }

    private List<Vincunlacion> findVincunlacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vincunlacion.class));
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

    public Vincunlacion findVincunlacion(VincunlacionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vincunlacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getVincunlacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vincunlacion> rt = cq.from(Vincunlacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
