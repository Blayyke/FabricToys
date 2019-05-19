package io.github.blayyke.fabrictoys.config;

public class ConfigUpdateResult {
    private final Result result;
    private String message = null;

    private ConfigUpdateResult(Result result) {
        this.result = result;
    }

    private ConfigUpdateResult(Result result, String message) {
        this.result = result;
        this.message = message;
    }

    public static ConfigUpdateResult success(String message) {
        return new ConfigUpdateResult(Result.SUCCESS, message);
    }

    public static ConfigUpdateResult failure(String message) {
        return new ConfigUpdateResult(Result.FAILURE, message);
    }

    public String getMessage() {
        return message;
    }

    public Result getResult() {
        return result;
    }

    public enum Result {
        SUCCESS, FAILURE
    }
}