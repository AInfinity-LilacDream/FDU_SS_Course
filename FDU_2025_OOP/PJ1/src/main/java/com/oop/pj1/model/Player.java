package com.oop.pj1.model;

import java.io.Serializable;

public class Player implements Serializable {
    private final String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
