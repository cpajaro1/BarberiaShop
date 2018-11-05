/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.IllegalOrphanException;
import Control.exceptions.NonexistentEntityException;
import Control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Barberia;
import Modelo.Calificacion;
import Modelo.ColaAtencion;
import Modelo.ColaAtencionPK;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author rcarrillo
 */
public class ColaAtencionJpaController implements Serializable {

    public ColaAtencionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ColaAtencion colaAtencion) throws PreexistingEntityException, Exception {
        if (colaAtencion.getColaAtencionPK() == null) {
            colaAtencion.setColaAtencionPK(new ColaAtencionPK());
        }
        if (colaAtencion.getCalificacionList() == null) {
            colaAtencion.setCalificacionList(new ArrayList<Calificacion>());
        }
        colaAtencion.getColaAtencionPK().setBarberiaid(colaAtencion.getBarberia().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barberia barberia = colaAtencion.getBarberia();
            if (barberia != null) {
                barberia = em.getReference(barberia.getClass(), barberia.getId());
                colaAtencion.setBarberia(barberia);
            }
            List<Calificacion> attachedCalificacionList = new ArrayList<Calificacion>();
            for (Calificacion calificacionListCalificacionToAttach : colaAtencion.getCalificacionList()) {
                calificacionListCalificacionToAttach = em.getReference(calificacionListCalificacionToAttach.getClass(), calificacionListCalificacionToAttach.getCalificacionPK());
                attachedCalificacionList.add(calificacionListCalificacionToAttach);
            }
            colaAtencion.setCalificacionList(attachedCalificacionList);
            em.persist(colaAtencion);
            if (barberia != null) {
                barberia.getColaAtencionList().add(colaAtencion);
                barberia = em.merge(barberia);
            }
            for (Calificacion calificacionListCalificacion : colaAtencion.getCalificacionList()) {
                ColaAtencion oldColaAtencionOfCalificacionListCalificacion = calificacionListCalificacion.getColaAtencion();
                calificacionListCalificacion.setColaAtencion(colaAtencion);
                calificacionListCalificacion = em.merge(calificacionListCalificacion);
                if (oldColaAtencionOfCalificacionListCalificacion != null) {
                    oldColaAtencionOfCalificacionListCalificacion.getCalificacionList().remove(calificacionListCalificacion);
                    oldColaAtencionOfCalificacionListCalificacion = em.merge(oldColaAtencionOfCalificacionListCalificacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findColaAtencion(colaAtencion.getColaAtencionPK()) != null) {
                throw new PreexistingEntityException("ColaAtencion " + colaAtencion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ColaAtencion colaAtencion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        colaAtencion.getColaAtencionPK().setBarberiaid(colaAtencion.getBarberia().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ColaAtencion persistentColaAtencion = em.find(ColaAtencion.class, colaAtencion.getColaAtencionPK());
            Barberia barberiaOld = persistentColaAtencion.getBarberia();
            Barberia barberiaNew = colaAtencion.getBarberia();
            List<Calificacion> calificacionListOld = persistentColaAtencion.getCalificacionList();
            List<Calificacion> calificacionListNew = colaAtencion.getCalificacionList();
            List<String> illegalOrphanMessages = null;
            for (Calificacion calificacionListOldCalificacion : calificacionListOld) {
                if (!calificacionListNew.contains(calificacionListOldCalificacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Calificacion " + calificacionListOldCalificacion + " since its colaAtencion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (barberiaNew != null) {
                barberiaNew = em.getReference(barberiaNew.getClass(), barberiaNew.getId());
                colaAtencion.setBarberia(barberiaNew);
            }
            List<Calificacion> attachedCalificacionListNew = new ArrayList<Calificacion>();
            for (Calificacion calificacionListNewCalificacionToAttach : calificacionListNew) {
                calificacionListNewCalificacionToAttach = em.getReference(calificacionListNewCalificacionToAttach.getClass(), calificacionListNewCalificacionToAttach.getCalificacionPK());
                attachedCalificacionListNew.add(calificacionListNewCalificacionToAttach);
            }
            calificacionListNew = attachedCalificacionListNew;
            colaAtencion.setCalificacionList(calificacionListNew);
            colaAtencion = em.merge(colaAtencion);
            if (barberiaOld != null && !barberiaOld.equals(barberiaNew)) {
                barberiaOld.getColaAtencionList().remove(colaAtencion);
                barberiaOld = em.merge(barberiaOld);
            }
            if (barberiaNew != null && !barberiaNew.equals(barberiaOld)) {
                barberiaNew.getColaAtencionList().add(colaAtencion);
                barberiaNew = em.merge(barberiaNew);
            }
            for (Calificacion calificacionListNewCalificacion : calificacionListNew) {
                if (!calificacionListOld.contains(calificacionListNewCalificacion)) {
                    ColaAtencion oldColaAtencionOfCalificacionListNewCalificacion = calificacionListNewCalificacion.getColaAtencion();
                    calificacionListNewCalificacion.setColaAtencion(colaAtencion);
                    calificacionListNewCalificacion = em.merge(calificacionListNewCalificacion);
                    if (oldColaAtencionOfCalificacionListNewCalificacion != null && !oldColaAtencionOfCalificacionListNewCalificacion.equals(colaAtencion)) {
                        oldColaAtencionOfCalificacionListNewCalificacion.getCalificacionList().remove(calificacionListNewCalificacion);
                        oldColaAtencionOfCalificacionListNewCalificacion = em.merge(oldColaAtencionOfCalificacionListNewCalificacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ColaAtencionPK id = colaAtencion.getColaAtencionPK();
                if (findColaAtencion(id) == null) {
                    throw new NonexistentEntityException("The colaAtencion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ColaAtencionPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ColaAtencion colaAtencion;
            try {
                colaAtencion = em.getReference(ColaAtencion.class, id);
                colaAtencion.getColaAtencionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The colaAtencion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Calificacion> calificacionListOrphanCheck = colaAtencion.getCalificacionList();
            for (Calificacion calificacionListOrphanCheckCalificacion : calificacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ColaAtencion (" + colaAtencion + ") cannot be destroyed since the Calificacion " + calificacionListOrphanCheckCalificacion + " in its calificacionList field has a non-nullable colaAtencion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Barberia barberia = colaAtencion.getBarberia();
            if (barberia != null) {
                barberia.getColaAtencionList().remove(colaAtencion);
                barberia = em.merge(barberia);
            }
            em.remove(colaAtencion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ColaAtencion> findColaAtencionEntities() {
        return findColaAtencionEntities(true, -1, -1);
    }

    public List<ColaAtencion> findColaAtencionEntities(int maxResults, int firstResult) {
        return findColaAtencionEntities(false, maxResults, firstResult);
    }

    private List<ColaAtencion> findColaAtencionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ColaAtencion.class));
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

    public ColaAtencion findColaAtencion(ColaAtencionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ColaAtencion.class, id);
        } finally {
            em.close();
        }
    }

    public int getColaAtencionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ColaAtencion> rt = cq.from(ColaAtencion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
