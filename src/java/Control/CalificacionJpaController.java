/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.NonexistentEntityException;
import Control.exceptions.PreexistingEntityException;
import Modelo.Calificacion;
import Modelo.CalificacionPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.ColaAtencion;
import Modelo.Turno;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author rcarrillo
 */
public class CalificacionJpaController implements Serializable {

    public CalificacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Calificacion calificacion) throws PreexistingEntityException, Exception {
        if (calificacion.getCalificacionPK() == null) {
            calificacion.setCalificacionPK(new CalificacionPK());
        }
        calificacion.getCalificacionPK().setColaAtencionBarberiaid(calificacion.getColaAtencion().getColaAtencionPK().getBarberiaid());
        calificacion.getCalificacionPK().setColaAtencionfecha(calificacion.getColaAtencion().getColaAtencionPK().getFecha());
        calificacion.getCalificacionPK().setTurnoClienteid(calificacion.getTurno().getTurnoPK().getClienteid());
        calificacion.getCalificacionPK().setColaAtencionid(calificacion.getColaAtencion().getColaAtencionPK().getId());
        calificacion.getCalificacionPK().setTurnoBarberoid(calificacion.getTurno().getTurnoPK().getBarberoid());
        calificacion.getCalificacionPK().setTurnoid(calificacion.getTurno().getTurnoPK().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ColaAtencion colaAtencion = calificacion.getColaAtencion();
            if (colaAtencion != null) {
                colaAtencion = em.getReference(colaAtencion.getClass(), colaAtencion.getColaAtencionPK());
                calificacion.setColaAtencion(colaAtencion);
            }
            Turno turno = calificacion.getTurno();
            if (turno != null) {
                turno = em.getReference(turno.getClass(), turno.getTurnoPK());
                calificacion.setTurno(turno);
            }
            em.persist(calificacion);
            if (colaAtencion != null) {
                colaAtencion.getCalificacionList().add(calificacion);
                colaAtencion = em.merge(colaAtencion);
            }
            if (turno != null) {
                turno.getCalificacionList().add(calificacion);
                turno = em.merge(turno);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCalificacion(calificacion.getCalificacionPK()) != null) {
                throw new PreexistingEntityException("Calificacion " + calificacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Calificacion calificacion) throws NonexistentEntityException, Exception {
        calificacion.getCalificacionPK().setColaAtencionBarberiaid(calificacion.getColaAtencion().getColaAtencionPK().getBarberiaid());
        calificacion.getCalificacionPK().setColaAtencionfecha(calificacion.getColaAtencion().getColaAtencionPK().getFecha());
        calificacion.getCalificacionPK().setTurnoClienteid(calificacion.getTurno().getTurnoPK().getClienteid());
        calificacion.getCalificacionPK().setColaAtencionid(calificacion.getColaAtencion().getColaAtencionPK().getId());
        calificacion.getCalificacionPK().setTurnoBarberoid(calificacion.getTurno().getTurnoPK().getBarberoid());
        calificacion.getCalificacionPK().setTurnoid(calificacion.getTurno().getTurnoPK().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Calificacion persistentCalificacion = em.find(Calificacion.class, calificacion.getCalificacionPK());
            ColaAtencion colaAtencionOld = persistentCalificacion.getColaAtencion();
            ColaAtencion colaAtencionNew = calificacion.getColaAtencion();
            Turno turnoOld = persistentCalificacion.getTurno();
            Turno turnoNew = calificacion.getTurno();
            if (colaAtencionNew != null) {
                colaAtencionNew = em.getReference(colaAtencionNew.getClass(), colaAtencionNew.getColaAtencionPK());
                calificacion.setColaAtencion(colaAtencionNew);
            }
            if (turnoNew != null) {
                turnoNew = em.getReference(turnoNew.getClass(), turnoNew.getTurnoPK());
                calificacion.setTurno(turnoNew);
            }
            calificacion = em.merge(calificacion);
            if (colaAtencionOld != null && !colaAtencionOld.equals(colaAtencionNew)) {
                colaAtencionOld.getCalificacionList().remove(calificacion);
                colaAtencionOld = em.merge(colaAtencionOld);
            }
            if (colaAtencionNew != null && !colaAtencionNew.equals(colaAtencionOld)) {
                colaAtencionNew.getCalificacionList().add(calificacion);
                colaAtencionNew = em.merge(colaAtencionNew);
            }
            if (turnoOld != null && !turnoOld.equals(turnoNew)) {
                turnoOld.getCalificacionList().remove(calificacion);
                turnoOld = em.merge(turnoOld);
            }
            if (turnoNew != null && !turnoNew.equals(turnoOld)) {
                turnoNew.getCalificacionList().add(calificacion);
                turnoNew = em.merge(turnoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CalificacionPK id = calificacion.getCalificacionPK();
                if (findCalificacion(id) == null) {
                    throw new NonexistentEntityException("The calificacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CalificacionPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Calificacion calificacion;
            try {
                calificacion = em.getReference(Calificacion.class, id);
                calificacion.getCalificacionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The calificacion with id " + id + " no longer exists.", enfe);
            }
            ColaAtencion colaAtencion = calificacion.getColaAtencion();
            if (colaAtencion != null) {
                colaAtencion.getCalificacionList().remove(calificacion);
                colaAtencion = em.merge(colaAtencion);
            }
            Turno turno = calificacion.getTurno();
            if (turno != null) {
                turno.getCalificacionList().remove(calificacion);
                turno = em.merge(turno);
            }
            em.remove(calificacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Calificacion> findCalificacionEntities() {
        return findCalificacionEntities(true, -1, -1);
    }

    public List<Calificacion> findCalificacionEntities(int maxResults, int firstResult) {
        return findCalificacionEntities(false, maxResults, firstResult);
    }

    private List<Calificacion> findCalificacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Calificacion.class));
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

    public Calificacion findCalificacion(CalificacionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Calificacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getCalificacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Calificacion> rt = cq.from(Calificacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
