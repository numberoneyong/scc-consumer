package io.beinspired.SCC_Consumer.controller.fraud;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class FraudController {

    private final RestTemplate restTemplate;
    int port = 8090;

    @PostMapping(value = "/fraudcheck", consumes="application/json", produces="application/json")
    public Response fraudCheck(@RequestBody LoanRequest loanRequest) {
        return this.restTemplate.exchange(
                RequestEntity
                        .put(URI.create("http://localhost:" + this.port + "/fraudcheck"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(loanRequest), Response.class
                ).getBody();
    }
}