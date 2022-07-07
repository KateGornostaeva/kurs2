import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Quotes extends JFrame {
    private JLabel l1 = new JLabel("Логин:");
    private JLabel l2 = new JLabel("Роль:");
    private JLabel l3 = new JLabel("Количество цитат:");
    private JButton buttonWrite = new JButton("Написать цитату");
    private JButton buttonChange = new JButton("Изменить регистрационные данные");
    private JFrame self;
    private boolean succeeded;

    public Quotes() throws HeadlessException {
        super("Основное");
        self = this; //для проброса this в анонимный класс
        this.setBounds(90, 90, 650, 450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //выключает программу с помощью Х

        buttonChange.addActionListener(new ActionListener() {//нажимаешь на "изменить рег.данные"
            // и перебрасывает в окно с цитатами
            @Override
            public void actionPerformed(ActionEvent e) {
                Settings settings = new Settings(self);
                settings.setVisible(true);
            }
        });
        buttonWrite.addActionListener(new ActionListener() {//нажимаешь на "написать цитату"
            // и перебрасывает в окно с цитатами
            @Override
            public void actionPerformed(ActionEvent e) {
                WriteQuote writeQuote = new WriteQuote(self);
                writeQuote.setVisible(true);
            }
        });

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(5, 2, 2, 2));
        container.add(l1);
        container.add(l2);
        container.add(l3);
        container.add(buttonWrite);
        container.add(buttonChange);

    }

    public void start() throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("");

        Login login = new Login(this);
        login.setVisible(true);
    }

    private Connection getConnection() { //подключение к БД
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2011_kurs",
                    "std_2011_kurs", "std_2011_kurs");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}

