package com.caort.spring.boot.jpa.data.integrity;

import com.caort.spring.boot.jpa.exception.DatabaseProtectionException;

/**
 * Interface that is inherited by actual implementations used to provide database integrity protection.
 *
 * @author caort
 * @date 2022/9/2 09:08
 */
public interface ProtectedDataImpl {
    /**
     * Sets the table name if the entity being protected
     */
    void setTableName(final String table);

    /**
     * Creates and sets the actual database integrity protection, or does nothing
     *
     * @throws DatabaseProtectionException if database protection is enabled, and the audit log does not function
     */
    void protectData(ProtectedData obj) throws DatabaseProtectionException;

    /**
     * Reads and verifies the actual database integrity protection, or does nothing
     *
     * @throws DatabaseProtectionException
     */
    void verifyData(ProtectedData obj) throws DatabaseProtectionException;

    /**
     * @param obj
     * @return
     * @throws DatabaseProtectionException if database protection is enabled, and the audit log does not function
     */
    String calculateProtection(final ProtectedData obj) throws DatabaseProtectionException;

    /**
     * Throws DatabaseProtectionException if erroronverifyfail is enabled in databaseprotection.properties
     * and logs a "row protection failed" message on ERROR level.
     *
     * @throws DatabaseProtectionException if database protection and erroronverifyfail is enabled, and the audit log does not function
     * the exception given as parameter if erroronverifyfail is enabled
     */
    void onDataVerificationError(final DatabaseProtectionException e) throws DatabaseProtectionException;
}
