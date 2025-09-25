package com.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
	    classes = { PetstoreQueryApplication.class},
	    webEnvironment = SpringBootTest.WebEnvironment.NONE
	)
	@ActiveProfiles("test")
class PetstoreQueryApplicationTests {

    @Test
    void mainMethodRuns() {
        // Covers PetstoreQueryApplication.main()
        assertDoesNotThrow(() -> PetstoreQueryApplication.main(new String[]{}));
    }
}
