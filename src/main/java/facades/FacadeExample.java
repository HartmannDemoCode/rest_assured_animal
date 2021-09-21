package facades;

import dtos.AnimalDTO;
import entities.Animal;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import errorhandling.AnimalNotFoundException;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class FacadeExample {

    private static FacadeExample instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private FacadeExample() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static FacadeExample getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FacadeExample();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public AnimalDTO create(AnimalDTO rm){
        Animal rme = new Animal(rm.getType(), rm.getSound());
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(rme);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new AnimalDTO(rme);
    }
    public AnimalDTO getById(long id) throws AnimalNotFoundException {
        EntityManager em = emf.createEntityManager();
        Animal a = em.find(Animal.class, id);
        if(a == null)
            throw new AnimalNotFoundException("No animal found with ID: "+id);
        return new AnimalDTO(a);
    }
    
    //TODO Remove/Change this before use
    public long getRenameMeCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long renameMeCount = (long)em.createQuery("SELECT COUNT(a) FROM Animal a").getSingleResult();
            return renameMeCount;
        }finally{  
            em.close();
        }
    }
    
    public List<AnimalDTO> getAll(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a", Animal.class);
        List<Animal> rms = query.getResultList();
        return AnimalDTO.getDtos(rms);
    }

    public AnimalDTO update(AnimalDTO adto){
        Animal a = new Animal(adto.getId(), adto.getType(), adto.getSound());
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            a = em.merge(a);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new AnimalDTO(a);
    }

    public AnimalDTO delete(long id) throws AnimalNotFoundException {
        EntityManager em = getEntityManager();
        Animal a = em.find(Animal.class, id);
        if(a == null)
            throw new AnimalNotFoundException("Animal with id: "+id+" was not found");
        try {
            em.getTransaction().begin();
            em.remove(a);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new AnimalDTO(a);
    }
    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        FacadeExample fe = getFacadeExample(emf);
        fe.getAll().forEach(dto->System.out.println(dto));
    }

}
