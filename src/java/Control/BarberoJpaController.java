/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.IllegalOrphanException;
import Control.exceptions.NonexistentEntityException;
import Modelo.Barbero;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Vincunlacion;
import java.util.ArrayList;
import java.util.List;
import Modelo.Turno;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author rcarrillo
 */
public class BarberoJpaController implements Serializable {

    public BarberoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Barbero barbero) {
        if (barbero.getVincunlacionList() == null) {
            barbero.setVincunlacionList(new ArrayList<Vincunlacion>());
        }
        if (barbero.getTurnoList() == null) {
            barbero.setTurnoList(new ArrayList<Turno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Vincunlacion> attachedVincunlacionList = new ArrayList<Vincunlacion>();
            for (Vincunlacion vincunlacionListVincunlacionToAttach : barbero.getVincunlacionList()) {
                vincunlacionListVincunlacionToAttach = em.getReference(vincunlacionListVincunlacionToAttach.getClass(), vincunlacionListVincunlacionToAttach.getVincunlacionPK());
                attachedVincunlacionList.add(vincunlacionListVincunlacionToAttach);
            }
            barbero.setVincunlacionList(attachedVincunlacionList);
            List<Turno> attachedTurnoList = new ArrayList<Turno>();
            for (Turno turnoListTurnoToAttach : barbero.getTurnoList()) {
                turnoListTurnoToAttach = em.getReference(turnoListTurnoToAttach.getClass(), turnoListTurnoToAttach.getTurnoPK());
                attachedTurnoList.add(turnoListTurnoToAttach);
            }
            barbero.setTurnoList(attachedTurnoList);
            em.persist(barbero);
            for (Vincunlacion vincunlacionListVincunlacion : barbero.getVincunlacionList()) {
                Barbero oldBarberoOfVincunlacionListVincunlacion = vincunlacionListVincunlacion.getBarbero();
                vincunlacionListVincunlacion.setBarbero(barbero);
                vincunlacionListVincunlacion = em.merge(vincunlacionListVincunlacion);
                if (oldBarberoOfVincunlacionListVincunlacion != null) {
                    oldBarberoOfVincunlacionListVincunlacion.getVincunlacionList().remove(vincunlacionListVincunlacion);
                    oldBarberoOfVincunlacionListVincunlacion = em.merge(oldBarberoOfVincunlacionListVincunlacion);
                }
            }
            for (Turno turnoListTurno : barbero.getTurnoList()) {
                Barbero oldBarberoOfTurnoListTurno = turnoListTurno.getBarbero();
                turnoListTurno.setBarbero(barbero);
                turnoListTurno = em.merge(turnoListTurno);
                if (oldBarberoOfTurnoListTurno != null) {
                    oldBarberoOfTurnoListTurno.getTurnoList().remove(turnoListTurno);
                    oldBarberoOfTurnoListTurno = em.merge(oldBarberoOfTurnoListTurno);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Barbero barbero) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barbero persistentBarbero = em.find(Barbero.class, barbero.getId());
            List<Vincunlacion> vincunlacionListOld = persistentBarbero.getVincunlacionList();
            List<Vincunlacion> vincunlacionListNew = barbero.getVincunlacionList();
            List<Turno> turnoListOld = persistentBarbero.getTurnoList();
            List<Turno> turnoListNew = barbero.getTurnoList();
            List<String> illegalOrphanMessages = null;
            for (Vincunlacion vincunlacionListOldVincunlacion : vincunlacionListOld) {
                if (!vincunlacionListNew.contains(vincunlacionListOldVincunlacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Vincunlacion " + vincunlacionListOldVincunlacion + " since its barbero field is not nullable.");
                }
            }
            for (Turno turnoListOldTurno : turnoListOld) {
                if (!turnoListNew.contains(turnoListOldTurno)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Turno " + turnoListOldTurno + " since its barbero field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Vincunlacion> attachedVincunlacionListNew = new ArrayList<Vincunlacion>();
            for (Vincunlacion vincunlacionListNewVincunlacionToAttach : vincunlacionListNew) {
                vincunlacionListNewVincunlacionToAttach = em.getReference(vincunlacionListNewVincunlacionToAttach.getClass(), vincunlacionListNewVincunlacionToAttach.getVincunlacionPK());
                attachedVincunlacionListNew.add(vincunlacionListNewVincunlacionToAttach);
            }
            vincunlacionListNew = attachedVincunlacionListNew;
            barbero.setVincunlacionList(vincunlacionListNew);
            List<Turno> attachedTurnoListNew = new ArrayList<Turno>();
            for (Turno turnoListNewTurnoToAttach : turnoListNew) {
                turnoListNewTurnoToAttach = em.getReference(turnoListNewTurnoToAttach.getClass(), turnoListNewTurnoToAttach.getTurnoPK());
                attachedTurnoListNew.add(turnoListNewTurnoToAttach);
            }
            turnoListNew = attachedTurnoListNew;
            barbero.setTurnoList(turnoListNew);
            barbero = em.merge(barbero);
            for (Vincunlacion vincunlacionListNewVincunlacion : vincunlacionListNew) {
                if (!vincunlacionListOld.contains(vincunlacionListNewVincunlacion)) {
                    Barbero oldBarberoOfVincunlacionListNewVincunlacion = vincunlacionListNewVincunlacion.getBarbero();
                    vincunlacionListNewVincunlacion.setBarbero(barbero);
                    vincunlacionListNewVincunlacion = em.merge(vincunlacionListNewVincunlacion);
                    if (oldBarberoOfVincunlacionListNewVincunlacion != null && !oldBarberoOfVincunlacionListNewVincunlacion.equals(barbero)) {
                        oldBarberoOfVincunlacionListNewVincunlacion.getVincunlacionList().remove(vincunlacionListNewVincunlacion);
                        oldBarberoOfVincunlacionListNewVincunlacion = em.merge(oldBarberoOfVincunlacionListNewVincunlacion);
                    }
                }
            }
            for (Turno turnoListNewTurno : turnoListNew) {
                if (!turnoListOld.contains(turnoListNewTurno)) {
                    Barbero oldBarberoOfTurnoListNewTurno = turnoListNewTurno.getBarbero();
                    turnoListNewTurno.setBarbero(barbero);
                    turnoListNewTurno = em.merge(turnoListNewTurno);
                    if (oldBarberoOfTurnoListNewTurno != null && !oldBarberoOfTurnoListNewTurno.equals(barbero)) {
                        oldBarberoOfTurnoListNewTurno.getTurnoList().remove(turnoListNewTurno);
                        oldBarberoOfTurnoListNewTurno = em.merge(oldBarberoOfTurnoListNewTurno);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = barbero.getId();
                if (findBarbero(id) == null) {
                    throw new NonexistentEntityException("The barbero with id " + id + " no longer exists.");
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
            Barbero barbero;
            try {
                barbero = em.getReference(Barbero.class, id);
                barbero.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The barbero with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Vincunlacion> vincunlacionListOrphanCheck = barbero.getVincunlacionList();
            for (Vincunlacion vincunlacionListOrphanCheckVincunlacion : vincunlacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Barbero (" + barbero + ") cannot be destroyed since the Vincunlacion " + vincunlacionListOrphanCheckVincunlacion + " in its vincunlacionList field has a non-nullable barbero field.");
            }
            List<Turno> turnoListOrphanCheck = barbero.getTurnoList();
            for (Turno turnoListOrphanCheckTurno : turnoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Barbero (" + barbero + ") cannot be destroyed since the Turno " + turnoListOrphanCheckTurno + " in its turnoList field has a non-nullable barbero field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(barbero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Barbero> findBarberoEntities() {
        return findBarberoEntities(true, -1, -1);
    }

    public List<Barbero> findBarberoEntities(int maxResults, int firstResult) {
        return findBarberoEntities(false, maxResults, firstResult);
    }

    private List<Barbero> findBarberoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Barbero.class));
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

    public Barbero findBarbero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Barbero.class, id);
        } finally {
            em.close();
        }
    }

    public int getBarberoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Barbero> rt = cq.from(Barbero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
