import entity.Quote;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuotesDialog extends JFrame {
    private JLabel Login = new JLabel("Логин:");
    private JLabel Role = new JLabel("Роль:");
    private JTextArea inputQuote = new JTextArea(6, 30);
    //TODO заменить поле для текста(то что выше) на цитаты, которые опубликовали
    private JLabel NumbQuotes = new JLabel("Количество цитат:");
    private JButton buttonWrite = new JButton("Написать цитату");
    private JButton buttonChange = new JButton("Изменить регистрационные данные");
    private JFrame self;
    private boolean succeeded;
    private User user;
    private Connection connection;

    public QuotesDialog() throws HeadlessException {
        super("Основное");
        self = this; //для проброса this в анонимный класс
        this.setBounds(90, 90, 650, 450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //выключает программу с помощью кнопки "Х"

        buttonChange.addActionListener(new ActionListener() {//нажимаешь на "изменить рег.данные"
            // и перебрасывает в окно с цитатами
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeRegData changeRegData = new ChangeRegData(self, user, connection);
                changeRegData.setVisible(true);
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
        GridBagLayout gbl = new GridBagLayout();
        container.setLayout(gbl);

        GridBagConstraints c = new GridBagConstraints();//логин
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        gbl.setConstraints(Login, c);
        container.add(Login);

        GridBagConstraints a = new GridBagConstraints();//роль
        a.gridx = 0;
        a.gridy = 1;
        a.gridwidth = 1;
        a.gridheight = 1;
        gbl.setConstraints(Role, a);
        container.add(Role);

        GridBagConstraints b = new GridBagConstraints(); //кол-во цитат
        b.gridx = 0;
        b.gridy = 2;
        b.gridwidth = 1;
        b.gridheight = 1;
        gbl.setConstraints(NumbQuotes, b);
        container.add(NumbQuotes);

        GridBagConstraints d = new GridBagConstraints(); //кнопка написать
        d.gridx = 0;
        d.gridy = 3;
        d.gridwidth = 1;
        d.gridheight = 1;
        gbl.setConstraints(buttonWrite, d);
        container.add(buttonWrite);

        GridBagConstraints e = new GridBagConstraints(); //кнопка написать
        e.gridx = 0;
        e.gridy = 4;
        e.gridwidth = 1;
        e.gridheight = 1;
        gbl.setConstraints(buttonChange, e);
        container.add(buttonChange);

        GridBagConstraints f = new GridBagConstraints();//цитаты
        f.gridx = 1;
        f.gridy = 0;
        f.gridwidth = 3;
        f.gridheight = 5;
        gbl.setConstraints(inputQuote, f);
        container.add(inputQuote);

    }

    public void start() throws SQLException {
        connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("");

        Login login = new Login(this, connection);
        login.setVisible(true);
        if (login.isSucceeded()) {
            user = login.getUser();
        } else {
            user = new User();
            user.setRole("GUEST");
        }
        preparedStatement = connection.prepareStatement("SELECT * FROM quote_teacher ");
        resultSet = preparedStatement.executeQuery();
        List<Quote> quoteList = new ArrayList<>();
        while (resultSet.next()){ //прочитали все цитаты и засунули их в лист
            Quote quote = new Quote(resultSet);
            quoteList.add(quote);
        }
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

