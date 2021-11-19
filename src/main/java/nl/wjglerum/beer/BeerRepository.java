package nl.wjglerum.beer;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BeerRepository implements PanacheRepository<Beer> {

    public Uni<Beer> findByName(String name) {
        return find("name", name).firstResult();
    }
}
