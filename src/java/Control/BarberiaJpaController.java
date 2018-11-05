/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.IllegalOrphanException;
import Control.exceptions.NonexistentEntityException;
import Modelo.Barberia;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Jornada;
import java.util.ArrayList;
import java.util.List;
import Modelo.ServicioOfrecidos;
import Modelo.Vincunlacion;
import Modelo.ColaAtencion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author rcarrillo
 */
public class BarberiaJpaController implements Serializable {

    public BarberiaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Barberia barberia) {
        if (barberia.getJornadaList() == null) {
            barberia.setJornadaList(new ArrayList<Jornada>());
        }
        if (barberia.getServicioOfrecidosList() == null) {
            barberia.setServicioOfrecidosList(new ArrayList<ServicioOfrecidos>());
        }
        if (barberia.getVincunlacionList() == null) {
            barberia.setVincunlacionList(new ArrayList<Vincunlacion>());
        }
        if (barberia.getColaAtencionList() == null) {
            barberia.setColaAtencionList(new ArrayList<ColaAtencion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Jornada> attachedJornadaList = new ArrayList<Jornada>();
            for (Jornada jornadaListJornadaToAttach : barberia.getJornadaList()) {
                jornadaListJornadaToAttach = em.getReference(jornadaListJornadaToAttach.getClass(), jornadaListJornadaToAttach.getId());
                attachedJornadaList.add(jornadaListJornadaToAttach);
            }
            barberia.setJornadaList(attachedJornadaList);
            List<ServicioOfrecidos> attachedServicioOfrecidosList = new ArrayList<ServicioOfrecidos>();
            for (ServicioOfrecidos servicioOfrecidosListServicioOfrecidosToAttach : barberia.getServicioOfrecidosList()) {
                servicioOfrecidosListServicioOfrecidosToAttach = em.getReference(servicioOfrecidosListServicioOfrecidosToAttach.getClass(), servicioOfrecidosListServicioOfrecidosToAttach.getServicioOfrecidosPK());
                attachedServicioOfrecidosList.add(servicioOfrecidosListServicioOfrecidosToAttach);
            }
            barberia.setServicioOfrecidosList(attachedServicioOfrecidosList);
            List<Vincunlacion> attachedVincunlacionList = new ArrayList<Vincunlacion>();
            for (Vincunlacion vincunlacionListVincunlacionToAttach : barberia.getVincunlacionList()) {
                vincunlacionListVincunlacionToAttach = em.getReference(vincunlacionListVincunlacionToAttach.getClass(), vincunlacionListVincunlacionToAttach.getVincunlacionPK());
                attachedVincunlacionList.add(vincunlacionListVincunlacionToAttach);
            }
            barberia.setVincunlacionList(attachedVincunlacionList);
            List<ColaAtencion> attachedColaAtencionList = new ArrayList<ColaAtencion>();
            for (ColaAtencion colaAtencionListColaAtencionToAttach : barberia.getColaAtencionList()) {
                colaAtencionListColaAtencionToAttach = em.getReference(colaAtencionListColaAtencionToAttach.getClass(), colaAtencionListColaAtencionToAttach.getColaAtencionPK());
                attachedColaAtencionList.add(colaAtencionListColaAtencionToAttach);
            }
            barberia.setColaAtencionList(attachedColaAtencionList);
            em.persist(barberia);
            for (Jornada jornadaListJornada : barberia.getJornadaList()) {
                jornadaListJornada.getBarberiaList().add(barberia);
                jornadaListJornada = em.merge(jornadaListJornada);
            }
            for (ServicioOfrecidos servicioOfrecidosListServicioOfrecidos : barberia.getServicioOfrecidosList()) {
                Barberia oldBarberiaOfServicioOfrecidosListServicioOfrecidos = servicioOfrecidosListServicioOfrecidos.getBarberia();
                servicioOfrecidosListServicioOfrecidos.setBarberia(barberia);
                servicioOfrecidosListServicioOfrecidos = em.merge(servicioOfrecidosListServicioOfrecidos);
                if (oldBarberiaOfServicioOfrecidosListServicioOfrecidos != null) {
                    oldBarberiaOfServicioOfrecidosListServicioOfrecidos.getServicioOfrecidosList().remove(servicioOfrecidosListServicioOfrecidos);
                    oldBarberiaOfServicioOfrecidosListServicioOfrecidos = em.merge(oldBarberiaOfServicioOfrecidosListServicioOfrecidos);
                }
            }
            for (Vincunlacion vincunlacionListVincunlacion : barberia.getVincunlacionList()) {
                Barberia oldBarberiaOfVincunlacionListVincunlacion = vincunlacionListVincunlacion.getBarberia();
                vincunlacionListVincunlacion.setBarberia(barberia);
                vincunlacionListVincunlacion = em.merge(vincunlacionListVincunlacion);
                if (oldBarberiaOfVincunlacionListVincunlacion != null) {
                    oldBarberiaOfVincunlacionListVincunlacion.getVincunlacionList().remove(vincunlacionListVincunlacion);
                    oldBarberiaOfVincunlacionListVincunlacion = em.merge(oldBarberiaOfVincunlacionListVincunlacion);
                }
            }
            for (ColaAtencion colaAtencionListColaAtencion : barberia.getColaAtencionList()) {
                Barberia oldBarberiaOfColaAtencionListColaAtencion = colaAtencionListColaAtencion.getBarberia();
                colaAtencionListColaAtencion.setBarberia(barberia);
                colaAtencionListColaAtencion = em.merge(colaAtencionListColaAtencion);
                if (oldBarberiaOfColaAtencionListColaAtencion != null) {
                    oldBarberiaOfColaAtencionListColaAtencion.getColaAtencionList().remove(colaAtencionListColaAtencion);
                    oldBarberiaOfColaAtencionListColaAtencion = em.merge(oldBarberiaOfColaAtencionListColaAtencion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Barberia barberia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barberia persistentBarberia = em.find(Barberia.class, barberia.getId());
            List<Jornada> jornadaListOld = persistentBarberia.getJornadaList();
            List<Jornada> jornadaListNew = barberia.getJornadaList();
            List<ServicioOfrecidos> servicioOfrecidosListOld = persistentBarberia.getServicioOfrecidosList();
            List<ServicioOfrecidos> servicioOfrecidosListNew = barberia.getServicioOfrecidosList();
            List<Vincunlacion> vincunlacionListOld = persistentBarberia.getVincunlacionList();
            List<Vincunlacion> vincunlacionListNew = barberia.getVincunlacionList();
            List<ColaAtencion> colaAtencionListOld = persistentBarberia.getColaAtencionList();
            List<ColaAtencion> colaAtencionListNew = barberia.getColaAtencionList();
            List<String> illegalOrphanMessages = null;
            for (ServicioOfrecidos servicioOfrecidosListOldServicioOfrecidos : servicioOfrecidosListOld) {
                if (!servicioOfrecidosListNew.contains(servicioOfrecidosListOldServicioOfrecidos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ServicioOfrecidos " + servicioOfrecidosListOldServicioOfrecidos + " since its barberia field is not nullable.");
                }
            }
            for (Vincunlacion vincunlacionListOldVincunlacion : vincunlacionListOld) {
                if (!vincunlacionListNew.contains(vincunlacionListOldVincunlacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Vincunlacion " + vincunlacionListOldVincunlacion + " since its barberia field is not nullable.");
                }
            }
            for (ColaAtencion colaAtencionListOldColaAtencion : colaAtencionListOld) {
                if (!colaAtencionListNew.contains(colaAtencionListOldColaAtencion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ColaAtencion " + colaAtencionListOldColaAtencion + " since its barberia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Jornada> attachedJornadaListNew = new ArrayList<Jornada>();
            for (Jornada jornadaListNewJornadaToAttach : jornadaListNew) {
                jornadaListNewJornadaToAttach = em.getReference(jornadaListNewJornadaToAttach.getClass(), jornadaListNewJornadaToAttach.getId());
                attachedJornadaListNew.add(jornadaListNewJornadaToAttach);
            }
            jornadaListNew = attachedJornadaListNew;
            barberia.setJornadaList(jornadaListNew);
            List<ServicioOfrecidos> attachedServicioOfrecidosListNew = new ArrayList<ServicioOfrecidos>();
            for (ServicioOfrecidos servicioOfrecidosListNewServicioOfrecidosToAttach : servicioOfrecidosListNew) {
                servicioOfrecidosListNewServicioOfrecidosToAttach = em.getReference(servicioOfrecidosListNewServicioOfrecidosToAttach.getClass(), servicioOfrecidosListNewServicioOfrecidosToAttach.getServicioOfrecidosPK());
                attachedServicioOfrecidosListNew.add(servicioOfrecidosListNewServicioOfrecidosToAttach);
            }
            servicioOfrecidosListNew = attachedServicioOfrecidosListNew;
            barberia.setServicioOfrecidosList(servicioOfrecidosListNew);
            List<Vincunlacion> attachedVincunlacionListNew = new ArrayList<Vincunlacion>();
            for (Vincunlacion vincunlacionListNewVincunlacionToAttach : vincunlacionListNew) {
                vincunlacionListNewVincunlacionToAttach = em.getReference(vincunlacionListNewVincunlacionToAttach.getClass(), vincunlacionListNewVincunlacionToAttach.getVincunlacionPK());
                attachedVincunlacionListNew.add(vincunlacionListNewVincunlacionToAttach);
            }
            vincunlacionListNew = attachedVincunlacionListNew;
            barberia.setVincunlacionList(vincunlacionListNew);
            List<ColaAtencion> attachedColaAtencionListNew = new ArrayList<ColaAtencion>();
            for (ColaAtencion colaAtencionListNewColaAtencionToAttach : colaAtencionListNew) {
                colaAtencionListNewColaAtencionToAttach = em.getReference(colaAtencionListNewColaAtencionToAttach.getClass(), colaAtencionListNewColaAtencionToAttach.getColaAtencionPK());
                attachedColaAtencionListNew.add(colaAtencionListNewColaAtencionToAttach);
            }
            colaAtencionListNew = attachedColaAtencionListNew;
            barberia.setColaAtencionList(colaAtencionListNew);
            barberia = em.merge(barberia);
            for (Jornada jornadaListOldJornada : jornadaListOld) {
                if (!jornadaListNew.contains(jornadaListOldJornada)) {
                    jornadaListOldJornada.getBarberiaList().remove(barberia);
                    jornadaListOldJornada = em.merge(jornadaListOldJornada);
                }
            }
            for (Jornada jornadaListNewJornada : jornadaListNew) {
                if (!jornadaListOld.contains(jornadaListNewJornada)) {
                    jornadaListNewJornada.getBarberiaList().add(barberia);
                    jornadaListNewJornada = em.merge(jornadaListNewJornada);
                }
            }
            for (ServicioOfrecidos servicioOfrecidosListNewServicioOfrecidos : servicioOfrecidosListNew) {
                if (!servicioOfrecidosListOld.contains(servicioOfrecidosListNewServicioOfrecidos)) {
                    Barberia oldBarberiaOfServicioOfrecidosListNewServicioOfrecidos = servicioOfrecidosListNewServicioOfrecidos.getBarberia();
                    servicioOfrecidosListNewServicioOfrecidos.setBarberia(barberia);
                    servicioOfrecidosListNewServicioOfrecidos = em.merge(servicioOfrecidosListNewServicioOfrecidos);
                    if (oldBarberiaOfServicioOfrecidosListNewServicioOfrecidos != null && !oldBarberiaOfServicioOfrecidosListNewServicioOfrecidos.equals(barberia)) {
                        oldBarberiaOfServicioOfrecidosListNewServicioOfrecidos.getServicioOfrecidosList().remove(servicioOfrecidosListNewServicioOfrecidos);
                        oldBarberiaOfServicioOfrecidosListNewServicioOfrecidos = em.merge(oldBarberiaOfServicioOfrecidosListNewServicioOfrecidos);
                    }
                }
            }
            for (Vincunlacion vincunlacionListNewVincunlacion : vincunlacionListNew) {
                if (!vincunlacionListOld.contains(vincunlacionListNewVincunlacion)) {
                    Barberia oldBarberiaOfVincunlacionListNewVincunlacion = vincunlacionListNewVincunlacion.getBarberia();
                    vincunlacionListNewVincunlacion.setBarberia(barberia);
                    vincunlacionListNewVincunlacion = em.merge(vincunlacionListNewVincunlacion);
                    if (oldBarberiaOfVincunlacionListNewVincunlacion != null && !oldBarberiaOfVincunlacionListNewVincunlacion.equals(barberia)) {
                        oldBarberiaOfVincunlacionListNewVincunlacion.getVincunlacionList().remove(vincunlacionListNewVincunlacion);
                        oldBarberiaOfVincunlacionListNewVincunlacion = em.merge(oldBarberiaOfVincunlacionListNewVincunlacion);
                    }
                }
            }
            for (ColaAtencion colaAtencionListNewColaAtencion : colaAtencionListNew) {
                if (!colaAtencionListOld.contains(colaAtencionListNewColaAtencion)) {
                    Barberia oldBarberiaOfColaAtencionListNewColaAtencion = colaAtencionListNewColaAtencion.getBarberia();
                    colaAtencionListNewColaAtencion.setBarberia(barberia);
                    colaAtencionListNewColaAtencion = em.merge(colaAtencionListNewColaAtencion);
                    if (oldBarberiaOfColaAtencionListNewColaAtencion != null && !oldBarberiaOfColaAtencionListNewColaAtencion.equals(barberia)) {
                        oldBarberiaOfColaAtencionListNewColaAtencion.getColaAtencionList().remove(colaAtencionListNewColaAtencion);
                        oldBarberiaOfColaAtencionListNewColaAtencion = em.merge(oldBarberiaOfColaAtencionListNewColaAtencion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = barberia.getId();
                if (findBarberia(id) == null) {
                    throw new NonexistentEntityException("The barberia with id " + id + " no longer exists.");
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
            Barberia barberia;
            try {
                barberia = em.getReference(Barberia.class, id);
                barberia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The barberia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ServicioOfrecidos> servicioOfrecidosListOrphanCheck = barberia.getServicioOfrecidosList();
            for (ServicioOfrecidos servicioOfrecidosListOrphanCheckServicioOfrecidos : servicioOfrecidosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Barberia (" + barberia + ") cannot be destroyed since the ServicioOfrecidos " + servicioOfrecidosListOrphanCheckServicioOfrecidos + " in its servicioOfrecidosList field has a non-nullable barberia field.");
            }
            List<Vincunlacion> vincunlacionListOrphanCheck = barberia.getVincunlacionList();
            for (Vincunlacion vincunlacionListOrphanCheckVincunlacion : vincunlacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Barberia (" + barberia + ") cannot be destroyed since the Vincunlacion " + vincunlacionListOrphanCheckVincunlacion + " in its vincunlacionList field has a non-nullable barberia field.");
            }
            List<ColaAtencion> colaAtencionListOrphanCheck = barberia.getColaAtencionList();
            for (ColaAtencion colaAtencionListOrphanCheckColaAtencion : colaAtencionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Barberia (" + barberia + ") cannot be destroyed since the ColaAtencion " + colaAtencionListOrphanCheckColaAtencion + " in its colaAtencionList field has a non-nullable barberia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Jornada> jornadaList = barberia.getJornadaList();
            for (Jornada jornadaListJornada : jornadaList) {
                jornadaListJornada.getBarberiaList().remove(barberia);
                jornadaListJornada = em.merge(jornadaListJornada);
            }
            em.remove(barberia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Barberia> findBarberiaEntities() {
        return findBarberiaEntities(true, -1, -1);
    }

    public List<Barberia> findBarberiaEntities(int maxResults, int firstResult) {
        return findBarberiaEntities(false, maxResults, firstResult);
    }

    private List<Barberia> findBarberiaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Barberia.class));
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

    public Barberia findBarberia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Barberia.class, id);
        } finally {
            em.close();
        }
    }

    public int getBarberiaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Barberia> rt = cq.from(Barberia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
