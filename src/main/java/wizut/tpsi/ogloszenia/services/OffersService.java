/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizut.tpsi.ogloszenia.services;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import wizut.tpsi.ogloszenia.jpa.CarManufacturer;
import wizut.tpsi.ogloszenia.jpa.CarModel;
import wizut.tpsi.ogloszenia.jpa.BodyStyle;
import wizut.tpsi.ogloszenia.jpa.FuelType;
import wizut.tpsi.ogloszenia.jpa.Offer;

/**
 *
 * @author Czaro
 */
@Service
@Transactional
public class OffersService {
    
    @PersistenceContext
    private EntityManager em;
    
    public CarManufacturer getCarManufacturer(int id) {
    return em.find(CarManufacturer.class, id);
    }
    
    public CarModel getModel(int id){
        return em.find(CarModel.class, id);
    }
     
    public List<CarManufacturer> getCarManufacturers() {
        String jpql = "select cm from CarManufacturer cm order by cm.name";
        TypedQuery<CarManufacturer> query = em.createQuery(jpql, CarManufacturer.class);
        List<CarManufacturer> result = query.getResultList();
        return result;
    }
    
     public List<CarModel> getCarModels() {
        String jpql = "select cm from CarModel cm order by cm.name";
        TypedQuery<CarModel> query = em.createQuery(jpql, CarModel.class);
        List<CarModel> result = query.getResultList();
        return result;
    }
    
      public List<BodyStyle> getBodyStyles() {
        String jpql = "select cm from BodyStyle cm order by cm.name";
        TypedQuery<BodyStyle> query = em.createQuery(jpql, BodyStyle.class);
        List<BodyStyle> result = query.getResultList();
        return result;
    }
      
    public List<FuelType> getFuelTypes() {
        String jpql = "select cm from FuelType cm order by cm.name";
        TypedQuery<FuelType> query = em.createQuery(jpql, FuelType.class);
        List<FuelType> result = query.getResultList();
        return result;
    }
    
    public List<CarModel> getCarModels(int manufacturerId) {
        String jpql = "select cm from CarModel cm where cm.manufacturer.id = :id order by cm.name";

        TypedQuery<CarModel> query = em.createQuery(jpql, CarModel.class);
        query.setParameter("id", manufacturerId);

        return query.getResultList();
    }
    
    public List<Offer> getOffers() {
        String jpql = "select cm from Offer cm order by cm.title";
        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
        List<Offer> result = query.getResultList();
        return result;
    }
    
     public List<Offer> getOffersByModel(int manufacturerId) {
        String jpql = "select cm from Offer cm where cm.model.id = :id order by cm.title";

        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
        query.setParameter("id", manufacturerId);

        return query.getResultList();
    }
     
//      public List<CarModel> getOffersByManufacturer(int manufacturerId) {
//        String jpql = "select cm from CarModel cm where cm.manufacturer.id = :id order by cm.name";
//        
//
//        TypedQuery<CarModel> query = em.createQuery(jpql, CarModel.class);
//        query.setParameter("id", manufacturerId);
//        List<CarModel> result = query.getResultList();
//        List<Offer> oferta;
//        for (int i=0;i<result.size();i++){
//            if (result.get(i).getId()==10){
//                
//            }
//        }
//        return result;
//    }
     
     public List<Offer> getOffersByManufacturer(int manufacturerId){
        String jpql = "select obm from Offer obm where obm.model.manufacturer.id = :id order by obm.id";

        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
        query.setParameter("id", manufacturerId);

        return query.getResultList();
    }
     
     
      
    public Offer getOffer(int id) {
    return em.find(Offer.class, id);
    }
   
    public Offer createOffer(Offer offer) {
        em.persist(offer);
        return offer;
    }
    
    public Offer deleteOffer(Integer id) {
        Offer offer = em.find(Offer.class, id);
        em.remove(offer);
        return offer;
    }
    
    public Offer saveOffer(Offer offer) {
        return em.merge(offer);
    }
    
    public List<CarModel> getCarModelsByManufacturer(int manufacturerId) {
        String jpql = "select cm from CarModel cm where cm.manufacturer.id="+manufacturerId+"order by cm.id";
        TypedQuery<CarModel> query = em.createQuery(jpql, CarModel.class);
        List<CarModel> result = query.getResultList();
        return result;         
    }
}
