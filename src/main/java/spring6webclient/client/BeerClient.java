package spring6webclient.client;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring6webclient.model.BeerDTO;

import java.util.Map;

public interface BeerClient {

    Flux<String> listBeer();
    Flux<Map> listBeerMap();
    Flux<JsonNode> listBeerJson();
    Flux<BeerDTO> listBeerDTO();
    Mono<BeerDTO> getBeerById(String beerId);
    Flux<BeerDTO> getBeerByBeerStyle(String beerStyle);
    Mono<BeerDTO> createBeer(BeerDTO beerDTO);
    Mono<BeerDTO> updateBeer(BeerDTO beerDTO);
    Mono<BeerDTO> patchBeer(BeerDTO beerDTO);
    Mono<Void> deleteBeer(BeerDTO beerDTO);
}
