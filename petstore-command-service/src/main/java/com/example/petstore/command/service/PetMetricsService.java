package com.example.petstore.command.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.Map;

@Service
public class PetMetricsService {
    private final LongAdder totalInserts = new LongAdder();
    private final AtomicLong minTime = new AtomicLong(Long.MAX_VALUE);
    private final AtomicLong maxTime = new AtomicLong(0);
    private final AtomicLong totalDuration = new AtomicLong(0);
    private final long startTime = System.currentTimeMillis();

    public void recordInsert(long durationMs) {
        totalInserts.increment();
        totalDuration.addAndGet(durationMs);
        
        minTime.updateAndGet(current -> Math.min(current, durationMs));
        maxTime.updateAndGet(current -> Math.max(current, durationMs));
    }

    public Map<String, Object> getMetrics() {
        long count = totalInserts.sum();
        long now = System.currentTimeMillis();
        
        // Calculate time intervals
        double uptimeMillis = Math.max(1, now - startTime); // Prevent 0 division
        double uptimeSeconds = uptimeMillis / 1000.0;
        double uptimeMinutes = uptimeMillis / 60000.0;

        // Calculate rates
        double avg = count == 0 ? 0 : (double) totalDuration.get() / count;
        double insertsPerSecond = count / uptimeSeconds;
        double insertsPerMinute = count / uptimeMinutes;

        return Map.of(
            "totalInserts", count,
            "insertsPerSecond", Math.round(insertsPerSecond * 100.0) / 100.0, // Added this
            "insertsPerMinute", Math.round(insertsPerMinute * 100.0) / 100.0,
            "minInsertTimeMs", count == 0 ? 0 : minTime.get(),
            "maxInsertTimeMs", maxTime.get(),
            "averageInsertTimeMs", Math.round(avg * 100.0) / 100.0
        );
    }
}