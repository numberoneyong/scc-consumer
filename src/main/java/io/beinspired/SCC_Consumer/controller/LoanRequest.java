package io.beinspired.SCC_Consumer.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoanRequest {
    private String clientId;
    private Long loanAmount;

    static LoanRequest sampleOk() { return new LoanRequest("1", 10000L); }

    static LoanRequest sampleFraud() {
        return new LoanRequest("1", 99999L);
    }
}

