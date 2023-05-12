package spring6webclient.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring6webclient.model.BeerDTO;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClient beerClient;

    private BeerDTO getSavedBeerDto() {
        BeerDTO dto = beerClient.listBeerDTO().blockFirst();
        return dto;
    }

    @Test
    void listBeer() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        beerClient.listBeer().subscribe(response -> {
            System.out.println(response);
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void listBeerAsMap() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        beerClient.listBeerMap().subscribe(response -> {
            System.out.println(response);
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void listBeerAsJsonNode() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        beerClient.listBeerJson().subscribe(response -> {
            System.out.println(response);
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void listBeerAsPOJO() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeerDTO().subscribe(beerDTO -> {
            System.out.println(beerDTO.getBeerName());
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetBeerById() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        BeerDTO dto = getSavedBeerDto();

        beerClient.getBeerById(dto.getId()).subscribe(beerDTO -> {
            System.out.println(beerDTO.toString());
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetBeerByBeerStyle() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        beerClient.getBeerByBeerStyle("Pale Ale").subscribe(beerDTO -> {
            System.out.println(beerDTO.getBeerName() + " -- " + beerDTO.getBeerStyle());
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void testcreateBeer() {
        BeerDTO beerToSave = BeerDTO.builder()
                .beerName("BeerToSave")
                .beerStyle("IPA")
                .price(BigDecimal.TEN)
                .upc("4546165")
                .quantityOnHand(15)
                .build();

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.createBeer(beerToSave).subscribe(beerDTO -> {
            System.out.println(beerDTO.getBeerName());
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void testUpdateBeer() {
        BeerDTO beerToUpdate = getSavedBeerDto();
        beerToUpdate.setBeerName("Updated BeerName");
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.updateBeer(beerToUpdate).subscribe(beerDTO -> {
            System.out.println(beerDTO.toString());
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testPatchBeer() {
        BeerDTO beerTopatch = getSavedBeerDto();
        beerTopatch.setBeerName("Updated BeerName");
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.patchBeer(beerTopatch).subscribe(beerDTO -> {
            System.out.println(beerDTO.toString());
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testDeleteBeer() {
        BeerDTO beerToDelete = getSavedBeerDto();
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.deleteBeer(beerToDelete)
                .doOnSuccess(mt -> {
                    System.out.println(beerToDelete.getId());
                    atomicBoolean.set(true);
                })
                .subscribe();
        await().untilTrue(atomicBoolean);
    }
}