package wav.tcc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author willian
 */
public class Database
{

    private static Database instance = null;
    private Connection connection = null;

    private Database()
    {
        try
        {
            Properties prop = new Properties();
            
            prop.load( getClass().getResourceAsStream( "db.properties" ) );
            
            String dbUrl = prop.getProperty( "db.url" );
            String dbUser = prop.getProperty( "db.user" );
            String dbPassword = prop.getProperty( "db.password" );
            String driver = prop.getProperty( "db.driver" );

            Class.forName( driver );
            
            connection = DriverManager.getConnection( dbUrl, dbUser, dbPassword );
        }

        catch ( Exception e )
        {
            System.err.println( e );
        }
    }

    /**
     *
     * @return
     */
    public static Database getInstance()
    {
        if ( instance == null )
        {
            instance = new Database();
        }

        return instance;
    }

    /**
     *
     * @return
     */
    public Connection getConnection()
    {
        if ( connection == null )
        {
            throw new RuntimeException( "connection cannot be null" );
        }

        return connection;
    }

    /**
     * 
     */
    public void shutDown()
    {
        try
        {
            connection.close();
            instance = null;
            connection = null;
        }

        catch ( Exception e )
        {
            System.err.println( e );
        }
    }
    
}
