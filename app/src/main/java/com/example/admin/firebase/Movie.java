package com.example.admin.firebase;

/**
 * Created by Admin on 10/17/2017.
 */

public class Movie {

    String name, director, producer;

    public Movie(){}

    public Movie(String name, String director, String producer) {
        this.name = name;
        this.director = director;
        this.producer = producer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", director='" + director + '\'' +
                ", producer='" + producer + '\'' +
                '}';
    }
}
