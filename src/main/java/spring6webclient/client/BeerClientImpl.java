package spring6webclient.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class BeerClientImpl implements BeerClient {

    private static final String SERVER_PATH = "http://localhost:8080";
    private static final String SERVER_PATH_ID = "/{beerId}";
    private static final String PATH_URL = "/api/v3/beer";
    private final WebClient webClient;

    public BeerClientImpl(WebClient.Builder webClientBuilder) {
        this.webClient = WebClient.builder().baseUrl(SERVER_PATH).build();
    }

    @Override
    public Flux<String> listBeer() {
        return webClient.get().uri(PATH_URL, String.class)
                .retrieve().bodyToFlux(String.class);
    }
}
