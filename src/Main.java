import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        Quotes quotes = new Quotes();
        quotes.setVisible(true);
        quotes.start();
    }
}
