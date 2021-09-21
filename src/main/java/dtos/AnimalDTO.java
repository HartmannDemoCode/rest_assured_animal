/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Animal;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tha
 */
public class AnimalDTO {
    private long id;
    private String type;
    private String sound;

    public AnimalDTO(String dummyStr1, String dummyStr2) {
        this.type = dummyStr1;
        this.sound = dummyStr2;
    }
    
    public static List<AnimalDTO> getDtos(List<Animal> rms){
        List<AnimalDTO> rmdtos = new ArrayList();
        rms.forEach(rm->rmdtos.add(new AnimalDTO(rm)));
        return rmdtos;
    }


    public AnimalDTO(Animal rm) {
        if(rm.getId() != null)
            this.id = rm.getId();
        this.type = rm.getType();
        this.sound = rm.getSound();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    @Override
    public String toString() {
        return "AnimalDTO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", sound='" + sound + '\'' +
                '}';
    }
}
