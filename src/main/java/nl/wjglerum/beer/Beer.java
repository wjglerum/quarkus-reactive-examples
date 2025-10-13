package nl.wjglerum.beer;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

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
