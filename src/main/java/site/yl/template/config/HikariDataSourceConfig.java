package site.yl.template.config;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix = "data-source")
public class HikariDataSourceConfig {
	private final Logger log=LoggerFactory.getLogger(HikariDataSourceConfig.class);

	final String prefix="${datasource.hikari.";
	final String suffix="}";

	@Value(prefix+"pool-name"+suffix)
	private String poolName;

	@Value(prefix+"jdbc-url"+suffix)
	private String jdbcUrl;

	@Value(prefix+"driver-class-name"+suffix)
	private String driverClassName;

	@Value(prefix+"username"+suffix)
	private String username;

	@Value(prefix+"password"+suffix)
	private String password;

	@Value(prefix+"min-idle"+suffix)
	private int minIdle;

	@Value(prefix+"max-pool-size"+suffix)
	private int maxPoolSize;

	@Value(prefix+"max-lifetime"+suffix)
	private long maxLifetimeMs;

	@Value(prefix+"connection-timeout"+suffix)
	private long connectionTimeoutMs;

	@Value(prefix+"auto-commit"+suffix)
	private boolean isAutoCommit;

	@Value(prefix+"connection-test-query"+suffix)
	private String connectionTestQuery;

	@Value(prefix+"idle-timeout"+suffix)
	private long idleTimeout;
	
	
	@Bean(name="dataSourceFirst")
	public DataSource dataSource() {
		HikariDataSource hds=new HikariDataSource();
		hds.setPoolName(poolName);
		hds.setJdbcUrl(jdbcUrl);
		hds.setDriverClassName(driverClassName);
		hds.setUsername(username);
		hds.setPassword(password);
		hds.setMinimumIdle(minIdle);
		hds.setIdleTimeout(idleTimeout);
		hds.setMaximumPoolSize(maxPoolSize);
		hds.setAutoCommit(isAutoCommit);
		hds.setMaxLifetime(maxLifetimeMs);
		hds.setConnectionTimeout(connectionTimeoutMs);
		hds.setConnectionTestQuery(connectionTestQuery);
		log.info("dataSourceFirst config finish");
		return hds;
	}

}
