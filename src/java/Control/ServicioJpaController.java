/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.IllegalOrphanException;
import Control.exceptions.NonexistentEntityException;
import Modelo.Servicio;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.ServicioOfrecidos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author rcarrillo
 */
public class ServicioJpaController implements Serializable {

    public ServicioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Servicio servicio) {
        if (servicio.getServicioOfrecidosList() == null) {
            servicio.setServicioOfrecidosList(new ArrayList<ServicioOfrecidos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ServicioOfrecidos> attachedServicioOfrecidosList = new ArrayList<ServicioOfrecidos>();
            for (ServicioOfrecidos servicioOfrecidosListServicioOfrecidosToAttach : servicio.getServicioOfrecidosList()) {
                servicioOfrecidosListServicioOfrecidosToAttach = em.getReference(servicioOfrecidosListServicioOfrecidosToAttach.getClass(), servicioOfrecidosListServicioOfrecidosToAttach.getServicioOfrecidosPK());
                attachedServicioOfrecidosList.add(servicioOfrecidosListServicioOfrecidosToAttach);
            }
            servicio.setServicioOfrecidosList(attachedServicioOfrecidosList);
            em.persist(servicio);
            for (ServicioOfrecidos servicioOfrecidosListServicioOfrecidos : servicio.getServicioOfrecidosList()) {
                Servicio oldServicioOfServicioOfrecidosListServicioOfrecidos = servicioOfrecidosListServicioOfrecidos.getServicio();
                servicioOfrecidosListServicioOfrecidos.setServicio(servicio);
                servicioOfrecidosListServicioOfrecidos = em.merge(servicioOfrecidosListServicioOfrecidos);
                if (oldServicioOfServicioOfrecidosListServicioOfrecidos != null) {
                    oldServicioOfServicioOfrecidosListServicioOfrecidos.getServicioOfrecidosList().remove(servicioOfrecidosListServicioOfrecidos);
                    oldServicioOfServicioOfrecidosListServicioOfrecidos = em.merge(oldServicioOfServicioOfrecidosListServicioOfrecidos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Servicio servicio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servicio persistentServicio = em.find(Servicio.class, servicio.getId());
            List<ServicioOfrecidos> servicioOfrecidosListOld = persistentServicio.getServicioOfrecidosList();
            List<ServicioOfrecidos> servicioOfrecidosListNew = servicio.getServicioOfrecidosList();
            List<String> illegalOrphanMessages = null;
            for (ServicioOfrecidos servicioOfrecidosListOldServicioOfrecidos : servicioOfrecidosListOld) {
                if (!servicioOfrecidosListNew.contains(servicioOfrecidosListOldServicioOfrecidos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ServicioOfrecidos " + servicioOfrecidosListOldServicioOfrecidos + " since its servicio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ServicioOfrecidos> attachedServicioOfrecidosListNew = new ArrayList<ServicioOfrecidos>();
            for (ServicioOfrecidos servicioOfrecidosListNewServicioOfrecidosToAttach : servicioOfrecidosListNew) {
                servicioOfrecidosListNewServicioOfrecidosToAttach = em.getReference(servicioOfrecidosListNewServicioOfrecidosToAttach.getClass(), servicioOfrecidosListNewServicioOfrecidosToAttach.getServicioOfrecidosPK());
                attachedServicioOfrecidosListNew.add(servicioOfrecidosListNewServicioOfrecidosToAttach);
            }
            servicioOfrecidosListNew = attachedServicioOfrecidosListNew;
            servicio.setServicioOfrecidosList(servicioOfrecidosListNew);
            servicio = em.merge(servicio);
            for (ServicioOfrecidos servicioOfrecidosListNewServicioOfrecidos : servicioOfrecidosListNew) {
                if (!servicioOfrecidosListOld.contains(servicioOfrecidosListNewServicioOfrecidos)) {
                    Servicio oldServicioOfServicioOfrecidosListNewServicioOfrecidos = servicioOfrecidosListNewServicioOfrecidos.getServicio();
                    servicioOfrecidosListNewServicioOfrecidos.setServicio(servicio);
                    servicioOfrecidosListNewServicioOfrecidos = em.merge(servicioOfrecidosListNewServicioOfrecidos);
                    if (oldServicioOfServicioOfrecidosListNewServicioOfrecidos != null && !oldServicioOfServicioOfrecidosListNewServicioOfrecidos.equals(servicio)) {
                        oldServicioOfServicioOfrecidosListNewServicioOfrecidos.getServicioOfrecidosList().remove(servicioOfrecidosListNewServicioOfrecidos);
                        oldServicioOfServicioOfrecidosListNewServicioOfrecidos = em.merge(oldServicioOfServicioOfrecidosListNewServicioOfrecidos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = servicio.getId();
                if (findServicio(id) == null) {
                    throw new NonexistentEntityException("The servicio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servicio servicio;
            try {
                servicio = em.getReference(Servicio.class, id);
                servicio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servicio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ServicioOfrecidos> servicioOfrecidosListOrphanCheck = servicio.getServicioOfrecidosList();
            for (ServicioOfrecidos servicioOfrecidosListOrphanCheckServicioOfrecidos : servicioOfrecidosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Servicio (" + servicio + ") cannot be destroyed since the ServicioOfrecidos " + servicioOfrecidosListOrphanCheckServicioOfrecidos + " in its servicioOfrecidosList field has a non-nullable servicio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(servicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Servicio> findServicioEntities() {
        return findServicioEntities(true, -1, -1);
    }

    public List<Servicio> findServicioEntities(int maxResults, int firstResult) {
        return findServicioEntities(false, maxResults, firstResult);
    }

    private List<Servicio> findServicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Servicio.class));
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

    public Servicio findServicio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Servicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getServicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Servicio> rt = cq.from(Servicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
