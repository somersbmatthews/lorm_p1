package com.somersbmatthews;

import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

public class Pirate {

    @Id
    public int id;
    private String name;
    private boolean isatsea;

    public Pirate() {
    }

    public Pirate(int id, String name, boolean isatsea) {
        this.id = id;
        this.name = name;
        this.isatsea = isatsea;
    }

    public String getName() {
        return this.name;
    }

    public boolean isIsatsea() {
        return this.isatsea;
    }

    @Override
    public String toString() {
        return "{" +

                ", name='" + getName() + "'" +
                ", isatsea='" + isIsatsea() + "'" +
                "}";
    }

}
