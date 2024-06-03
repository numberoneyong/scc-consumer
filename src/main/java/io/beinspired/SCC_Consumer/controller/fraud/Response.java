package io.beinspired.SCC_Consumer.controller.fraud;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response {
    private FraudCheckStatus fraudCheckStatus;
    private String reason;

    public static Response sampleFraud() {
        return new Response(FraudCheckStatus.FRAUD, "Amount too high");
    }
}
