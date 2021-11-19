package nl.wjglerum.beer;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class BeerServiceImpl implements BeerService {

    @Override
    public CompletionStage<Beer> getFromDraft() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Beer.of("Guinness");
        });
    }
}
