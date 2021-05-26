package com.aliucord.plugins.bottom;

/**
 * An exception thrown when decoding fails.
 *
 * The String causing the error is accessible from instances of this exception.
 */
public class TranslationError extends RuntimeException {
    private final String why;
    
    TranslationError(String message, String why) {
        super(message);
        this.why = why;
    }
    
    /**
     * Why did my decoding fail?
     * @return The string that caused the error.
     */
    public String getWhy() {
        return why;
    }
}
