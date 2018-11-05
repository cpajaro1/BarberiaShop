/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.IllegalOrphanException;
import Control.exceptions.NonexistentEntityException;
import Modelo.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Turno;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author rcarrillo
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getTurnoList() == null) {
            cliente.setTurnoList(new ArrayList<Turno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Turno> attachedTurnoList = new ArrayList<Turno>();
            for (Turno turnoListTurnoToAttach : cliente.getTurnoList()) {
                turnoListTurnoToAttach = em.getReference(turnoListTurnoToAttach.getClass(), turnoListTurnoToAttach.getTurnoPK());
                attachedTurnoList.add(turnoListTurnoToAttach);
            }
            cliente.setTurnoList(attachedTurnoList);
            em.persist(cliente);
            for (Turno turnoListTurno : cliente.getTurnoList()) {
                Cliente oldClienteOfTurnoListTurno = turnoListTurno.getCliente();
                turnoListTurno.setCliente(cliente);
                turnoListTurno = em.merge(turnoListTurno);
                if (oldClienteOfTurnoListTurno != null) {
                    oldClienteOfTurnoListTurno.getTurnoList().remove(turnoListTurno);
                    oldClienteOfTurnoListTurno = em.merge(oldClienteOfTurnoListTurno);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            List<Turno> turnoListOld = persistentCliente.getTurnoList();
            List<Turno> turnoListNew = cliente.getTurnoList();
            List<String> illegalOrphanMessages = null;
            for (Turno turnoListOldTurno : turnoListOld) {
                if (!turnoListNew.contains(turnoListOldTurno)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Turno " + turnoListOldTurno + " since its cliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Turno> attachedTurnoListNew = new ArrayList<Turno>();
            for (Turno turnoListNewTurnoToAttach : turnoListNew) {
                turnoListNewTurnoToAttach = em.getReference(turnoListNewTurnoToAttach.getClass(), turnoListNewTurnoToAttach.getTurnoPK());
                attachedTurnoListNew.add(turnoListNewTurnoToAttach);
            }
            turnoListNew = attachedTurnoListNew;
            cliente.setTurnoList(turnoListNew);
            cliente = em.merge(cliente);
            for (Turno turnoListNewTurno : turnoListNew) {
                if (!turnoListOld.contains(turnoListNewTurno)) {
                    Cliente oldClienteOfTurnoListNewTurno = turnoListNewTurno.getCliente();
                    turnoListNewTurno.setCliente(cliente);
                    turnoListNewTurno = em.merge(turnoListNewTurno);
                    if (oldClienteOfTurnoListNewTurno != null && !oldClienteOfTurnoListNewTurno.equals(cliente)) {
                        oldClienteOfTurnoListNewTurno.getTurnoList().remove(turnoListNewTurno);
                        oldClienteOfTurnoListNewTurno = em.merge(oldClienteOfTurnoListNewTurno);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Turno> turnoListOrphanCheck = cliente.getTurnoList();
            for (Turno turnoListOrphanCheckTurno : turnoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Turno " + turnoListOrphanCheckTurno + " in its turnoList field has a non-nullable cliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
