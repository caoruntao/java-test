package com.caort.spring.boot.jpa.exception;

import com.caort.spring.boot.jpa.data.integrity.ProtectedData;

/**
 * @author caort
 * @date 2022/9/2 09:16
 */
public class DatabaseProtectionException extends RuntimeException {
    private static final long serialVersionUID = -1L;

    /* We don't want to send raw database entries outside the JVM. */
    private transient final ProtectedData entity;

    /**
     * Constructs an instance of exception with a simple details message
     * and the read entity causing the error.
     */
    public DatabaseProtectionException(final String msg, final ProtectedData entity) {
        super(msg);
        this.entity = entity;
    }


    /**
     * Constructs an instance of exception with a simple details message
     * and the underlying exception
     */
    public DatabaseProtectionException(final String msg, final Exception e) {
        super(msg, e);
        this.entity = null;
    }

    /**
     * Constructs an instance of exception with a simple details message
     */
    public DatabaseProtectionException(final String msg) {
        super(msg);
        this.entity = null;
    }

    /**
     * Constructs an instance of exception wrapping the causing error
     */
    public DatabaseProtectionException(final Exception e) {
        super(e);
        this.entity = null;
    }

    /**
     * @return the entity that we tried to read that failed the verification.
     */
    public ProtectedData getEntity() {
        return entity;
    }
}
