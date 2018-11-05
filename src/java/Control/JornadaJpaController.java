/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Barberia;
import Modelo.Jornada;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author rcarrillo
 */
public class JornadaJpaController implements Serializable {

    public JornadaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jornada jornada) {
        if (jornada.getBarberiaList() == null) {
            jornada.setBarberiaList(new ArrayList<Barberia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Barberia> attachedBarberiaList = new ArrayList<Barberia>();
            for (Barberia barberiaListBarberiaToAttach : jornada.getBarberiaList()) {
                barberiaListBarberiaToAttach = em.getReference(barberiaListBarberiaToAttach.getClass(), barberiaListBarberiaToAttach.getId());
                attachedBarberiaList.add(barberiaListBarberiaToAttach);
            }
            jornada.setBarberiaList(attachedBarberiaList);
            em.persist(jornada);
            for (Barberia barberiaListBarberia : jornada.getBarberiaList()) {
                barberiaListBarberia.getJornadaList().add(jornada);
                barberiaListBarberia = em.merge(barberiaListBarberia);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Jornada jornada) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jornada persistentJornada = em.find(Jornada.class, jornada.getId());
            List<Barberia> barberiaListOld = persistentJornada.getBarberiaList();
            List<Barberia> barberiaListNew = jornada.getBarberiaList();
            List<Barberia> attachedBarberiaListNew = new ArrayList<Barberia>();
            for (Barberia barberiaListNewBarberiaToAttach : barberiaListNew) {
                barberiaListNewBarberiaToAttach = em.getReference(barberiaListNewBarberiaToAttach.getClass(), barberiaListNewBarberiaToAttach.getId());
                attachedBarberiaListNew.add(barberiaListNewBarberiaToAttach);
            }
            barberiaListNew = attachedBarberiaListNew;
            jornada.setBarberiaList(barberiaListNew);
            jornada = em.merge(jornada);
            for (Barberia barberiaListOldBarberia : barberiaListOld) {
                if (!barberiaListNew.contains(barberiaListOldBarberia)) {
                    barberiaListOldBarberia.getJornadaList().remove(jornada);
                    barberiaListOldBarberia = em.merge(barberiaListOldBarberia);
                }
            }
            for (Barberia barberiaListNewBarberia : barberiaListNew) {
                if (!barberiaListOld.contains(barberiaListNewBarberia)) {
                    barberiaListNewBarberia.getJornadaList().add(jornada);
                    barberiaListNewBarberia = em.merge(barberiaListNewBarberia);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = jornada.getId();
                if (findJornada(id) == null) {
                    throw new NonexistentEntityException("The jornada with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jornada jornada;
            try {
                jornada = em.getReference(Jornada.class, id);
                jornada.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jornada with id " + id + " no longer exists.", enfe);
            }
            List<Barberia> barberiaList = jornada.getBarberiaList();
            for (Barberia barberiaListBarberia : barberiaList) {
                barberiaListBarberia.getJornadaList().remove(jornada);
                barberiaListBarberia = em.merge(barberiaListBarberia);
            }
            em.remove(jornada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Jornada> findJornadaEntities() {
        return findJornadaEntities(true, -1, -1);
    }

    public List<Jornada> findJornadaEntities(int maxResults, int firstResult) {
        return findJornadaEntities(false, maxResults, firstResult);
    }

    private List<Jornada> findJornadaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Jornada.class));
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

    public Jornada findJornada(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jornada.class, id);
        } finally {
            em.close();
        }
    }

    public int getJornadaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Jornada> rt = cq.from(Jornada.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
