package com.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
    classes = { PetstoreCommandApplication.class},
    webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@ActiveProfiles("test")
class PetstoreCommandApplicationTests {

    @Test
    void mainMethodRuns() {
        // Covers PetstoreCommandApplication.main()
        assertDoesNotThrow(() -> PetstoreCommandApplication.main(new String[]{}));
    }
}
