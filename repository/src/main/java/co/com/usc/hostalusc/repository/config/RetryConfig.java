package co.com.usc.hostalusc.repository.config;

import feign.RetryableException;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;



@Slf4j
public class RetryConfig implements Retryer {

    private final int maxAttempts;
    private final long backoff;
    int attempt;

    public RetryConfig() {
        this(2000, 3);
    }

    public RetryConfig(long backoff, int maxAttempts) {
        this.backoff = backoff;
        this.maxAttempts = maxAttempts;
        this.attempt = 1;
    }

    public void continueOrPropagate(RetryableException e) {
        log.warn("retrying");
        if (attempt++ >= maxAttempts) {
            throw e;
        }

        try {
            Thread.sleep(backoff);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Retryer clone() {
        return new RetryConfig(backoff, maxAttempts);
    }
}