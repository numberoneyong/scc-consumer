package io.beinspired.SCC_Consumer.controller.fraud;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "io.beinspired:SCC-Producer:0.0.1-SNAPSHOT")
class FraudControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    FraudController fraudController;
    @StubRunnerPort("SCC-Producer")
    int producerPort;

    @BeforeEach
    public void setupPort() {
        this.fraudController.port = this.producerPort;
    }

    @Test
    public void should_eventually_get_fraud() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/fraudcheck")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(LoanRequest.sampleFraud()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fraudCheckStatus").value(FraudCheckStatus.FRAUD.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reason").value("Amount too high"));
    }

    @Test
    public void should_eventually_get_fraud_through_controller() throws Exception {
        // given
        LoanRequest loanRequest = LoanRequest.sampleFraud();
        // when
        Response response = fraudController.fraudCheck(loanRequest);
        // then
        Assertions.assertThat(response.getFraudCheckStatus()).isEqualTo(FraudCheckStatus.FRAUD);
        Assertions.assertThat(response.getReason()).isEqualTo("Amount too high");
    }
}