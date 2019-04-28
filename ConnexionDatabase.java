import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnexionDatabase {
    private Connection connection;
    private static ConnexionDatabase connexionDatabase;

    public ConnexionDatabase() throws ClassNotFoundException, SQLException{
        Class.forName("org.mariadb.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/basededonneejava",
                "root", "");
    }

    public static ConnexionDatabase getConnexionDatabase() throws ClassNotFoundException, SQLException{
        if(connexionDatabase == null){
            connexionDatabase = new ConnexionDatabase();
        }
        return connexionDatabase;
    }

    public Connection getConnection(){
        return connection;
    }
}
