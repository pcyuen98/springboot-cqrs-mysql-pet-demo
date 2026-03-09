package com.example.petstore.command.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class PetStoreLoadTester {

    private static final String URL = "http://localhost:8081/api/v1/test/pets";
    private static final int TOTAL_REQUESTS = 6000;
    // Increased concurrency to better test Virtual Threads performance
    private static final int CONCURRENCY_LIMIT = 500; 

    public static void main(String[] args) {
        System.out.println("🚀 Starting load test: " + TOTAL_REQUESTS + " requests...");
        
        // Using a connection pool friendly client with a timeout
        try (var httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
             var executor = Executors.newVirtualThreadPerTaskExecutor()) {

            Semaphore semaphore = new Semaphore(CONCURRENCY_LIMIT);
            AtomicInteger successCount = new AtomicInteger(0);
            AtomicInteger failureCount = new AtomicInteger(0);
            
            long startTime = System.currentTimeMillis();

            // Use a list or array to track all virtual thread tasks
            CompletableFuture<?>[] futures = new CompletableFuture[TOTAL_REQUESTS];

            for (int i = 0; i < TOTAL_REQUESTS; i++) {
                futures[i] = CompletableFuture.runAsync(() -> {
                    try {
                        semaphore.acquire();
                        
                        // Matching the @GetMapping in your controller
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(URL))
                                .timeout(Duration.ofSeconds(5))
                                .GET() 
                                .build();

                        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                        // Your controller returns HttpStatus.CREATED (201)
                        if (response.statusCode() == 201) {
                            successCount.incrementAndGet();
                        } else {
                            System.err.println("Unexpected status: " + response.statusCode());
                            failureCount.incrementAndGet();
                        }
                    } catch (Exception e) {
                        failureCount.incrementAndGet();
                        System.err.println("Request failed: " + e.getMessage());
                    } finally {
                        semaphore.release();
                    }
                }, executor);
            }

            // Block until all 6000 requests are finished
            CompletableFuture.allOf(futures).join();

            long totalTime = System.currentTimeMillis() - startTime;
            printSummary(successCount.get(), failureCount.get(), totalTime);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printSummary(int success, int failure, long time) {
        double seconds = time / 1000.0;
        System.out.println("\n--- 📊 Load Test Results ---");
        System.out.println("Total Requests:  " + (success + failure));
        System.out.println("Successful:      " + success);
        System.out.println("Failed:          " + failure);
        System.out.println("Total Time:      " + time + "ms");
        if (seconds > 0) {
            System.out.printf("Avg Throughput:  %.2f req/sec%n", (success / seconds));
        }
        System.out.println("---------------------------\n");
    }
}