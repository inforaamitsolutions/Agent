package com.codeclinic.agent.model;

public class LoadingResult {
    String message;
    boolean isLoading;

    public LoadingResult(String message, boolean isLoading) {
        this.message = message;
        this.isLoading = isLoading;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
