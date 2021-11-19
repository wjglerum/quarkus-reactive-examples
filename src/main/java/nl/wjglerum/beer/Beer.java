package nl.wjglerum.beer;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Cacheable
public class Beer extends PanacheEntity {

    @Column(length = 40, unique = true, nullable = false)
    public String name;

    public static Beer of(String name) {
        Beer beer = new Beer();
        beer.name = name;
        return beer;
    }
}
