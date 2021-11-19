package nl.wjglerum.beer;

import java.util.concurrent.CompletionStage;

public interface BeerService {

    CompletionStage<Beer> getFromDraft();
}
