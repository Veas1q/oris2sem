import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ru.itis.dis403.model.Client;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_03");


        Client client = new Client();
        client.setId();
        emf.close();
    }
}
