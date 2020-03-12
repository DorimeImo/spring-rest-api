package springjacksondemo.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@PropertySource("classpath:persistence-mysql.properties")
@ComponentScan(basePackages = "springjacksondemo")
public class SpringConfigClass {

    @Autowired
    public Environment holdsDateFromPropertyFile;

    @Bean
    public LocalSessionFactoryBean getSessionFactoryBean(){
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();

        localSessionFactoryBean.setDataSource(myDataSourceSettings());
        localSessionFactoryBean.setPackagesToScan(holdsDateFromPropertyFile.getProperty("hibernate.packagesToScan"));
        localSessionFactoryBean.setHibernateProperties(getHibernateProperties());

        return localSessionFactoryBean;
    }

    @Bean
    public DataSource myDataSourceSettings(){
        ComboPooledDataSource myDataSource = new ComboPooledDataSource();
        try{
            myDataSource.setDriverClass(holdsDateFromPropertyFile.getProperty("jdbc.driver"));
        }
        catch (PropertyVetoException e){
            throw new RuntimeException(e);
        }
        myDataSource.setJdbcUrl(holdsDateFromPropertyFile.getProperty("jdbc.url"));
        myDataSource.setUser(holdsDateFromPropertyFile.getProperty("jdbc.user"));
        myDataSource.setPassword(holdsDateFromPropertyFile.getProperty("jdbc.password"));

        myDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
        myDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
        myDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
        myDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

        return myDataSource;
    }

    private int getIntProperty(String s){
        return Integer.parseInt(Objects.requireNonNull(holdsDateFromPropertyFile.getProperty(s)));
    }

    private Properties getHibernateProperties(){
        Properties properties = new Properties();

        properties.setProperty("hibernate.dialect", holdsDateFromPropertyFile.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", holdsDateFromPropertyFile.getProperty("hibernate.show_sql"));

        return properties;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory){
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }

    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
