package com.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
    classes = { PetstoreCommandApplication.class, PetstoreCommandApplicationTests.TestConfig.class },
    webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@ActiveProfiles("test")
class PetstoreCommandApplicationTests {

    // ✅ Exclude everything in controller packages
    @TestConfiguration
    @ComponentScan(
        excludeFilters = {
            @Filter(type = FilterType.REGEX, pattern = ".*\\.controller\\..*")
        }
    )
    static class TestConfig {}

    @Test
    void contextLoads() {
        // Verifies Spring context starts with minimal scan
    }

    @Test
    void mainMethodRuns() {
        // Covers PetstoreCommandApplication.main()
        assertDoesNotThrow(() -> PetstoreCommandApplication.main(new String[]{}));
    }
}
