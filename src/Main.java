import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        QuotesDialog quotesDialog = new QuotesDialog();
        quotesDialog.setVisible(true);
        quotesDialog.start();
    }
}
