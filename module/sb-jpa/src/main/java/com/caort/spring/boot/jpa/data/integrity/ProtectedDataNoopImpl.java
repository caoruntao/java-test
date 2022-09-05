package com.caort.spring.boot.jpa.data.integrity;

import com.caort.spring.boot.jpa.exception.DatabaseProtectionException;

/**
 * 空实现类
 *
 * @author caort
 * @date 2022/9/2 09:22
 */
public class ProtectedDataNoopImpl implements ProtectedDataImpl {
    @Override
    public void setTableName(String table) {

    }

    @Override
    public void protectData(ProtectedData obj) throws DatabaseProtectionException {

    }

    @Override
    public void verifyData(ProtectedData obj) throws DatabaseProtectionException {

    }

    @Override
    public String calculateProtection(ProtectedData obj) throws DatabaseProtectionException {
        return null;
    }

    @Override
    public void onDataVerificationError(DatabaseProtectionException e) throws DatabaseProtectionException {

    }
}
