package site.yl.template.config;


import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.ThreadLocalTransactionProvider;
import org.jooq.tools.jdbc.JDBCUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

@Configuration
public class DataAccessBeanConfig {
	private final Logger log=LoggerFactory.getLogger(DataAccessBeanConfig.class);

	@Bean(name="genericDao")
	//this bean will load by Spring Container after the dataSource Bean has bean loaded
	@DependsOn("dataSource")
	public DSLContext dao(DataSource dataSource) {

		Settings settings=new Settings()
				                .withExecuteLogging(false);

		ConnectionProvider connProvider=new ConnectionProvider() {

			@Override
			public void release(Connection connection) throws DataAccessException {
				//connection of here is an instance of the  Connection Class of
				// a connection pool,so close in here is just return it to the pool;
				JDBCUtils.safeClose(connection);
			}

			@Override
			public Connection acquire() throws DataAccessException {
				try {
					return dataSource.getConnection();
				} catch (SQLTimeoutException ex) {
					throw new DataAccessException("get connection from dataSource timout", ex);
				}catch (SQLException ex) {
					throw new DataAccessException("get connection from dataSource fail", ex);
				}
			}
		};

		DSLContext dc= DSL.using(
				new DefaultConfiguration()
				.set(settings)
				.set(new ThreadLocalTransactionProvider(connProvider))
		        .set(SQLDialect.MYSQL_5_7));
		log.info("initial dao Bean finish:{}",dc);
		return dc;
	}
}
