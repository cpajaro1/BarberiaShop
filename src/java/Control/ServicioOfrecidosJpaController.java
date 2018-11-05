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
import Modelo.Servicio;
import Modelo.ServicioOfrecidos;
import Modelo.ServicioOfrecidosPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author rcarrillo
 */
public class ServicioOfrecidosJpaController implements Serializable {

    public ServicioOfrecidosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ServicioOfrecidos servicioOfrecidos) throws PreexistingEntityException, Exception {
        if (servicioOfrecidos.getServicioOfrecidosPK() == null) {
            servicioOfrecidos.setServicioOfrecidosPK(new ServicioOfrecidosPK());
        }
        servicioOfrecidos.getServicioOfrecidosPK().setBarberiaid(servicioOfrecidos.getBarberia().getId());
        servicioOfrecidos.getServicioOfrecidosPK().setServicioid(servicioOfrecidos.getServicio().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barberia barberia = servicioOfrecidos.getBarberia();
            if (barberia != null) {
                barberia = em.getReference(barberia.getClass(), barberia.getId());
                servicioOfrecidos.setBarberia(barberia);
            }
            Servicio servicio = servicioOfrecidos.getServicio();
            if (servicio != null) {
                servicio = em.getReference(servicio.getClass(), servicio.getId());
                servicioOfrecidos.setServicio(servicio);
            }
            em.persist(servicioOfrecidos);
            if (barberia != null) {
                barberia.getServicioOfrecidosList().add(servicioOfrecidos);
                barberia = em.merge(barberia);
            }
            if (servicio != null) {
                servicio.getServicioOfrecidosList().add(servicioOfrecidos);
                servicio = em.merge(servicio);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findServicioOfrecidos(servicioOfrecidos.getServicioOfrecidosPK()) != null) {
                throw new PreexistingEntityException("ServicioOfrecidos " + servicioOfrecidos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ServicioOfrecidos servicioOfrecidos) throws NonexistentEntityException, Exception {
        servicioOfrecidos.getServicioOfrecidosPK().setBarberiaid(servicioOfrecidos.getBarberia().getId());
        servicioOfrecidos.getServicioOfrecidosPK().setServicioid(servicioOfrecidos.getServicio().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ServicioOfrecidos persistentServicioOfrecidos = em.find(ServicioOfrecidos.class, servicioOfrecidos.getServicioOfrecidosPK());
            Barberia barberiaOld = persistentServicioOfrecidos.getBarberia();
            Barberia barberiaNew = servicioOfrecidos.getBarberia();
            Servicio servicioOld = persistentServicioOfrecidos.getServicio();
            Servicio servicioNew = servicioOfrecidos.getServicio();
            if (barberiaNew != null) {
                barberiaNew = em.getReference(barberiaNew.getClass(), barberiaNew.getId());
                servicioOfrecidos.setBarberia(barberiaNew);
            }
            if (servicioNew != null) {
                servicioNew = em.getReference(servicioNew.getClass(), servicioNew.getId());
                servicioOfrecidos.setServicio(servicioNew);
            }
            servicioOfrecidos = em.merge(servicioOfrecidos);
            if (barberiaOld != null && !barberiaOld.equals(barberiaNew)) {
                barberiaOld.getServicioOfrecidosList().remove(servicioOfrecidos);
                barberiaOld = em.merge(barberiaOld);
            }
            if (barberiaNew != null && !barberiaNew.equals(barberiaOld)) {
                barberiaNew.getServicioOfrecidosList().add(servicioOfrecidos);
                barberiaNew = em.merge(barberiaNew);
            }
            if (servicioOld != null && !servicioOld.equals(servicioNew)) {
                servicioOld.getServicioOfrecidosList().remove(servicioOfrecidos);
                servicioOld = em.merge(servicioOld);
            }
            if (servicioNew != null && !servicioNew.equals(servicioOld)) {
                servicioNew.getServicioOfrecidosList().add(servicioOfrecidos);
                servicioNew = em.merge(servicioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ServicioOfrecidosPK id = servicioOfrecidos.getServicioOfrecidosPK();
                if (findServicioOfrecidos(id) == null) {
                    throw new NonexistentEntityException("The servicioOfrecidos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ServicioOfrecidosPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ServicioOfrecidos servicioOfrecidos;
            try {
                servicioOfrecidos = em.getReference(ServicioOfrecidos.class, id);
                servicioOfrecidos.getServicioOfrecidosPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servicioOfrecidos with id " + id + " no longer exists.", enfe);
            }
            Barberia barberia = servicioOfrecidos.getBarberia();
            if (barberia != null) {
                barberia.getServicioOfrecidosList().remove(servicioOfrecidos);
                barberia = em.merge(barberia);
            }
            Servicio servicio = servicioOfrecidos.getServicio();
            if (servicio != null) {
                servicio.getServicioOfrecidosList().remove(servicioOfrecidos);
                servicio = em.merge(servicio);
            }
            em.remove(servicioOfrecidos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ServicioOfrecidos> findServicioOfrecidosEntities() {
        return findServicioOfrecidosEntities(true, -1, -1);
    }

    public List<ServicioOfrecidos> findServicioOfrecidosEntities(int maxResults, int firstResult) {
        return findServicioOfrecidosEntities(false, maxResults, firstResult);
    }

    private List<ServicioOfrecidos> findServicioOfrecidosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ServicioOfrecidos.class));
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

    public ServicioOfrecidos findServicioOfrecidos(ServicioOfrecidosPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ServicioOfrecidos.class, id);
        } finally {
            em.close();
        }
    }

    public int getServicioOfrecidosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ServicioOfrecidos> rt = cq.from(ServicioOfrecidos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
