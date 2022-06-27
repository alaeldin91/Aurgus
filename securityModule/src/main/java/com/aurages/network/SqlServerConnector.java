package com.Aurages.network;

/**
 * Sql Server connector
 */
public class SqlServerConnector {

    private static SqlServerConnector sInstance;

    public static SqlServerConnector getInstance() {

        if (sInstance == null) {

        }

        return sInstance;
    }

    private SqlServerConnector(String keyIp, String keyDb, String keyUsername, String keyPassword, String keyPort) {

    }
}
