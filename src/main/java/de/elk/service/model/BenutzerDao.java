package de.elk.service.model;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
@Stateless
public class BenutzerDao {

    @Inject
    EntityManager entityManager;

    public List<Benutzer> getBenutzer(){
        Benutzer benutzer=new Benutzer();
        benutzer.setName("taaly");
        entityManager.persist(benutzer);
        return entityManager.createQuery("select x from Benutzer x").getResultList();
    }
}
