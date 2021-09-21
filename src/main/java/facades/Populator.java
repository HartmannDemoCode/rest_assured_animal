/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.AnimalDTO;
import entities.Animal;

import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        FacadeExample fe = FacadeExample.getFacadeExample(emf);
        fe.create(new AnimalDTO(new Animal("cat", "miav")));
        fe.create(new AnimalDTO(new Animal("dog", "wuff")));
        fe.create(new AnimalDTO(new Animal("duck", "quack")));
    }
    
    public static void main(String[] args) {
        populate();
    }
}