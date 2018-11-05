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
import Modelo.Barbero;
import Modelo.Cliente;
import Modelo.Calificacion;
import Modelo.Turno;
import Modelo.TurnoPK;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author rcarrillo
 */
public class TurnoJpaController implements Serializable {

    public TurnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Turno turno) throws PreexistingEntityException, Exception {
        if (turno.getTurnoPK() == null) {
            turno.setTurnoPK(new TurnoPK());
        }
        if (turno.getCalificacionList() == null) {
            turno.setCalificacionList(new ArrayList<Calificacion>());
        }
        turno.getTurnoPK().setClienteid(turno.getCliente().getId());
        turno.getTurnoPK().setBarberoid(turno.getBarbero().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barbero barbero = turno.getBarbero();
            if (barbero != null) {
                barbero = em.getReference(barbero.getClass(), barbero.getId());
                turno.setBarbero(barbero);
            }
            Cliente cliente = turno.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getId());
                turno.setCliente(cliente);
            }
            List<Calificacion> attachedCalificacionList = new ArrayList<Calificacion>();
            for (Calificacion calificacionListCalificacionToAttach : turno.getCalificacionList()) {
                calificacionListCalificacionToAttach = em.getReference(calificacionListCalificacionToAttach.getClass(), calificacionListCalificacionToAttach.getCalificacionPK());
                attachedCalificacionList.add(calificacionListCalificacionToAttach);
            }
            turno.setCalificacionList(attachedCalificacionList);
            em.persist(turno);
            if (barbero != null) {
                barbero.getTurnoList().add(turno);
                barbero = em.merge(barbero);
            }
            if (cliente != null) {
                cliente.getTurnoList().add(turno);
                cliente = em.merge(cliente);
            }
            for (Calificacion calificacionListCalificacion : turno.getCalificacionList()) {
                Turno oldTurnoOfCalificacionListCalificacion = calificacionListCalificacion.getTurno();
                calificacionListCalificacion.setTurno(turno);
                calificacionListCalificacion = em.merge(calificacionListCalificacion);
                if (oldTurnoOfCalificacionListCalificacion != null) {
                    oldTurnoOfCalificacionListCalificacion.getCalificacionList().remove(calificacionListCalificacion);
                    oldTurnoOfCalificacionListCalificacion = em.merge(oldTurnoOfCalificacionListCalificacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTurno(turno.getTurnoPK()) != null) {
                throw new PreexistingEntityException("Turno " + turno + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Turno turno) throws IllegalOrphanException, NonexistentEntityException, Exception {
        turno.getTurnoPK().setClienteid(turno.getCliente().getId());
        turno.getTurnoPK().setBarberoid(turno.getBarbero().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Turno persistentTurno = em.find(Turno.class, turno.getTurnoPK());
            Barbero barberoOld = persistentTurno.getBarbero();
            Barbero barberoNew = turno.getBarbero();
            Cliente clienteOld = persistentTurno.getCliente();
            Cliente clienteNew = turno.getCliente();
            List<Calificacion> calificacionListOld = persistentTurno.getCalificacionList();
            List<Calificacion> calificacionListNew = turno.getCalificacionList();
            List<String> illegalOrphanMessages = null;
            for (Calificacion calificacionListOldCalificacion : calificacionListOld) {
                if (!calificacionListNew.contains(calificacionListOldCalificacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Calificacion " + calificacionListOldCalificacion + " since its turno field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (barberoNew != null) {
                barberoNew = em.getReference(barberoNew.getClass(), barberoNew.getId());
                turno.setBarbero(barberoNew);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getId());
                turno.setCliente(clienteNew);
            }
            List<Calificacion> attachedCalificacionListNew = new ArrayList<Calificacion>();
            for (Calificacion calificacionListNewCalificacionToAttach : calificacionListNew) {
                calificacionListNewCalificacionToAttach = em.getReference(calificacionListNewCalificacionToAttach.getClass(), calificacionListNewCalificacionToAttach.getCalificacionPK());
                attachedCalificacionListNew.add(calificacionListNewCalificacionToAttach);
            }
            calificacionListNew = attachedCalificacionListNew;
            turno.setCalificacionList(calificacionListNew);
            turno = em.merge(turno);
            if (barberoOld != null && !barberoOld.equals(barberoNew)) {
                barberoOld.getTurnoList().remove(turno);
                barberoOld = em.merge(barberoOld);
            }
            if (barberoNew != null && !barberoNew.equals(barberoOld)) {
                barberoNew.getTurnoList().add(turno);
                barberoNew = em.merge(barberoNew);
            }
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getTurnoList().remove(turno);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getTurnoList().add(turno);
                clienteNew = em.merge(clienteNew);
            }
            for (Calificacion calificacionListNewCalificacion : calificacionListNew) {
                if (!calificacionListOld.contains(calificacionListNewCalificacion)) {
                    Turno oldTurnoOfCalificacionListNewCalificacion = calificacionListNewCalificacion.getTurno();
                    calificacionListNewCalificacion.setTurno(turno);
                    calificacionListNewCalificacion = em.merge(calificacionListNewCalificacion);
                    if (oldTurnoOfCalificacionListNewCalificacion != null && !oldTurnoOfCalificacionListNewCalificacion.equals(turno)) {
                        oldTurnoOfCalificacionListNewCalificacion.getCalificacionList().remove(calificacionListNewCalificacion);
                        oldTurnoOfCalificacionListNewCalificacion = em.merge(oldTurnoOfCalificacionListNewCalificacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TurnoPK id = turno.getTurnoPK();
                if (findTurno(id) == null) {
                    throw new NonexistentEntityException("The turno with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TurnoPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Turno turno;
            try {
                turno = em.getReference(Turno.class, id);
                turno.getTurnoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The turno with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Calificacion> calificacionListOrphanCheck = turno.getCalificacionList();
            for (Calificacion calificacionListOrphanCheckCalificacion : calificacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Turno (" + turno + ") cannot be destroyed since the Calificacion " + calificacionListOrphanCheckCalificacion + " in its calificacionList field has a non-nullable turno field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Barbero barbero = turno.getBarbero();
            if (barbero != null) {
                barbero.getTurnoList().remove(turno);
                barbero = em.merge(barbero);
            }
            Cliente cliente = turno.getCliente();
            if (cliente != null) {
                cliente.getTurnoList().remove(turno);
                cliente = em.merge(cliente);
            }
            em.remove(turno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Turno> findTurnoEntities() {
        return findTurnoEntities(true, -1, -1);
    }

    public List<Turno> findTurnoEntities(int maxResults, int firstResult) {
        return findTurnoEntities(false, maxResults, firstResult);
    }

    private List<Turno> findTurnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Turno.class));
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

    public Turno findTurno(TurnoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Turno.class, id);
        } finally {
            em.close();
        }
    }

    public int getTurnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Turno> rt = cq.from(Turno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
