package io.beinspired.SCC_Consumer.controller.metdo;

import io.beinspired.SCC_Consumer.common.utils.Server;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Getter
@RequiredArgsConstructor
@RestController
public class IllustrationController {

    private final RestTemplate restTemplate;
    private static final String API_ADDRESS = "/surrenders";
    int port = 8090;

    @PostMapping(value = API_ADDRESS, consumes="application/json", produces="application/json")
    public Illustration retrieveIllustration(Product product) {

        // pre processing...
        // flow...
        // send a http request to the server(producer)
        Illustration illustration = this.restTemplate.exchange(
                RequestEntity
                        .post(URI.create(Server.LOCAL_HOST.getAddress() + this.port + API_ADDRESS))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(product), Illustration.class)
                .getBody();

        // post processing...
        return illustration;
    }
}
