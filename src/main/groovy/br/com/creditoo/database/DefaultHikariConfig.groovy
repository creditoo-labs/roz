package br.com.creditoo.database

import com.zaxxer.hikari.HikariConfig

class DefaultHikariConfig extends HikariConfig {

    private final int POLLING_REFRESH_RATE = 10

    DefaultHikariConfig(String jdbcUrl, String user, String password, int maxPoolSize) {

        setMaximumPoolSize(maxPoolSize)
        setJdbcUrl(jdbcUrl)
        addDataSourceProperty("user", user)
        addDataSourceProperty("password", password)

        addDataSourceProperty("useSSL", false)

        setMinimumIdle(1)
        setUseDnsChecker(true)
        setDnsCheckerDelay(POLLING_REFRESH_RATE)
        setAllowPoolSuspension(true)
        setRegisterMbeans(true)
    }
}
