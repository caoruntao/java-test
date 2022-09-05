package com.caort.spring.boot.jpa.data.integrity;

import com.caort.spring.boot.jpa.exception.DatabaseProtectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author caort
 * @date 2022/9/2 09:00
 */
public abstract class ProtectedData {

    /**
     * 数据完整性保护的实现类
     */
    protected ProtectedDataImpl impl;

    /**
     * JPA需要的默认构造函数。
     * 此构造函数初始化可用的数据库完整性保护模块（如果有）
     */
    public ProtectedData() {
        impl = new ProtectedDataIntegrityImpl();
        impl.setTableName(getTableName());
    }

    /**
     * 实体类返回需要保护的数据
     * 更改实体类的现有保护字符串必须确保向后和向前兼容性（版本控制），以免破坏现有安装
     *
     * @param protectVersion 受保护的字符串版本，在验证数据时用作输入。
     * @return 要完整性保护的字符串
     */
    protected abstract String getProtectString(int protectVersion);

    /**
     * 子类必须有一个可以读写的数据库列
     */
    abstract public void setRowProtection(final String rowProtection);

    abstract public String getRowProtection();

    /**
     * @return 数据库表名. 这里使用实体类名，需要和ProtectedDataConfiguration#tableConfigurationMap的key保持一致
     * @see ProtectedDataConfiguration
     */
    protected String getTableName() {
        return this.getClass().getSimpleName();
    }

    /**
     * Overridden by extending class to be able to use @PrePersist, overriding class calls super.protectData().
     * This method creates integrity protection for the specific entity in the database.
     *
     * @throws DatabaseProtectionException if database protection is enabled, and the audit log does not function
     */
    protected void protectData() throws DatabaseProtectionException {
        if (impl != null) {
            impl.protectData(this);
        }
    }

    /**
     * Overridden by extending class to be able to use @PostLoad, overriding class calls super.verifyData().
     * This method verifies integrity protection for the specific entity in the database. If the data verification
     * failed, it invokes {@link #onDataVerificationError(DatabaseProtectionException)}.
     *
     * @throws DatabaseProtectionException if database protection is enabled, and the audit log does not function
     */
    protected void verifyData() throws DatabaseProtectionException {
        try {
            impl.verifyData(this);
        } catch (final DatabaseProtectionException e) {
            onDataVerificationError(e);
        }
    }

    /**
     * Method that calculates integrity protection of an entity, but does not store it anywhere. Used primarily to make test protection
     * in order to exercise the CryptoToken.
     *
     * @return the calculated protection string
     * @throws DatabaseProtectionException if database protection is enabled, and the audit log does not function
     */
    public String calculateProtection() throws DatabaseProtectionException {
        return impl.calculateProtection(this);
    }

    /**
     * Throws DatabaseProtectionException if errorOnVerifyFail is enabled in ProtectedDataConfiguration#TableConfiguration
     * and logs a "row protection failed" message on ERROR level.
     *
     * @throws DatabaseProtectionException if database protection is enabled, and the audit log does not function
     * the exception given as parameter if errorOnVerifyFail is enabled
     */
    protected void onDataVerificationError(final DatabaseProtectionException e) throws DatabaseProtectionException {
        impl.onDataVerificationError(e);
    }
}
