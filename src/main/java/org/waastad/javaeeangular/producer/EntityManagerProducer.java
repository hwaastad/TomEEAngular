/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.javaeeangular.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author Helge Waastad <helge.waastad@datametrix.no>
 */
@ApplicationScoped
public class EntityManagerProducer {
    
//    @PersistenceContext(unitName = "TomEEPU")
//    @Produces
//    private EntityManager em;

    @PersistenceUnit
    private EntityManagerFactory emf;


    @Produces
    @Default
    @RequestScoped
    public EntityManager create() {
        System.out.println("Producing entitymanager.....");
        return this.emf.createEntityManager();
    }

    public void close(@Disposes @Default EntityManager em) {
        System.out.println("Disposing entitymanager.....");
        if (em.isOpen()) {
            em.close();
        }
    }

}
