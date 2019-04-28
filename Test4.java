import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Test4 {
    public static void main(String[] args) {
        ConnexionDatabase connexionDatabase;
        Connection connection;

        Set<Promotion> promotions = new HashSet<>();
        Set<Professeur> professeurs = new HashSet<>();
        try{
            int nombre_eleve = 0;
            int nombre_professeur = 0;
            connexionDatabase = new ConnexionDatabase();
            connection = connexionDatabase.getConnection();
            automaticSQL autoSQL = new automaticSQL(connection);
            nombre_professeur = autoSQL.chargerProfesseur(professeurs);
            autoSQL.chargerPromotion(promotions);
            for(Promotion prom : promotions) {
                nombre_eleve = autoSQL.chargerEtudiantBDD(prom.getEleves(), prom);
                autoSQL.chargerEvaluation(prom.getEleves(), professeurs);
            }
            Application application = new Application(professeurs,promotions);
        }
        catch (SQLException e){ e.printStackTrace(); }
        catch (ClassNotFoundException e1){ e1.printStackTrace(); }

    }
}
