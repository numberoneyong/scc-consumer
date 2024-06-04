package io.beinspired.SCC_Consumer.controller.metdo;

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

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "io.beinspired:SCC-Producer:0.0.2-SNAPSHOT")
public class IllustrationControllerTest {
    private static final BigDecimal SURRENDER_RATE = new BigDecimal("0.9");

    @Autowired
    private IllustrationController illustrationController;
    @StubRunnerPort("SCC-Producer")
    int producerPort;

    @BeforeEach
    public void setupPort() {
        this.illustrationController.port = this.producerPort;
    }

    @Test
    public void should_get_surrenders_in_the_amount_of_90_percent_of_face_amount() throws Exception {
        // given
        Product product = Product.sample();
        BigDecimal faceAmount = product.getFaceAmount();
        BigDecimal expectedSurrenders = faceAmount.multiply(SURRENDER_RATE);
        // when
        Illustration illustration = illustrationController.retrieveIllustration(product);
        // then
        Assertions.assertThat(illustration).isNotNull();
        Assertions.assertThat(illustration.getSurrenders().compareTo(expectedSurrenders)).isEqualTo(0);
    }
}
