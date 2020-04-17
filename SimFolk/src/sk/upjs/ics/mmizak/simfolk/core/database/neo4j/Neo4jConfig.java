package sk.upjs.ics.mmizak.simfolk.core.database.neo4j;

import org.neo4j.ogm.config.Configuration.Builder;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories("sk.upjs.ics.mmizak.simfolk.core.database.neo4j.repositories")
@EnableTransactionManagement
@ComponentScan(basePackages = "sk.upjs.ics.mmizak.simfolk.core.database.neo4j.services")
public class Neo4jConfig {

    public static final String URI = "bolt://localhost:11003";
    public static final String USER = "neo4j";
    public static final String PASSWORD = "simfolk";
	/*public static final String LOCAL_STORE_DIR = "lokalnedb/1/embeded";
	public static final boolean LOCAL_DB = true;
	*/
/*	@Bean
	public GraphDatabaseService getGraphDb() {
		GraphDatabaseFactory graphDatabaseFactory = new GraphDatabaseFactory();
		return graphDatabaseFactory.newEmbeddedDatabase(new File(LOCAL_STORE_DIR));
	}
	*/

    @Bean
    org.neo4j.ogm.config.Configuration configuration() {
        Builder builder = new Builder();
        return builder.uri(URI).credentials(USER, PASSWORD).build();
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new SessionFactory(configuration(), "sk.upjs.ics.mmizak.simfolk.core.database.neo4j");
    }

    @Bean
    public Neo4jTransactionManager transactionManager() {
	    return new Neo4jTransactionManager(sessionFactory());
    }
}