package io.beinspired.SCC_Consumer.controller;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FraudReason {
    private String reason;

    public static FraudReason sampleFraud() {
        return new FraudReason("mount too high");
    }
}
