import entity.Quote;
import entity.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuotesDialog extends JFrame {
    private JLabel login = new JLabel("Логин:");
    private JLabel role = new JLabel("Роль:");
    private JTextArea inputQuote = new JTextArea(6, 30);
    private JLabel numbQuotes = new JLabel("Количество цитат:");
    private JButton buttonWrite = new JButton("Написать цитату");
    private JButton buttonChange = new JButton("Изменить регистрационные данные");
    private JFrame self;
    private boolean succeeded;
    private User user;
    private Connection connection;
    private Container container;
    private GridBagLayout gbl;
    private JTable table;

    public QuotesDialog() throws HeadlessException, SQLException {
        super("Основное");
        self = this; //для проброса this в анонимный класс
        this.setBounds(90, 90, 750, 550);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //выключает программу с помощью кнопки "Х"

        buttonChange.addActionListener(new ActionListener() {//нажимаешь на "изменить рег.данные"
            // и перебрасывает в окно с цитатами
            @Override
            public void actionPerformed(ActionEvent e) {
                if (user.getFunction().contains("3")) {
                    ChangeRegData changeRegData = new ChangeRegData(self, user, connection);
                    changeRegData.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Вы не зарегистрированы в системе");
                }
            }
        });
        buttonWrite.addActionListener(new ActionListener() {//нажимаешь на "написать цитату"
            // и перебрасывает в окно с цитатами
            @Override
            public void actionPerformed(ActionEvent e) {
                if (user.getFunction().contains("1")) {
                    WriteQuote writeQuote = new WriteQuote(self, user, connection);
                    writeQuote.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "У вас нет прав на создание цитат");
                }
            }
        });

        container = this.getContentPane();
        gbl = new GridBagLayout();
        container.setLayout(gbl);

        GridBagConstraints c = new GridBagConstraints();//логин
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        gbl.setConstraints(login, c);
        container.add(login);

        GridBagConstraints a = new GridBagConstraints();//роль
        a.anchor = GridBagConstraints.WEST;
        a.gridx = 0;
        a.gridy = 1;
        a.gridwidth = 1;
        a.gridheight = 1;
        gbl.setConstraints(role, a);
        container.add(role);

        GridBagConstraints b = new GridBagConstraints(); //кол-во цитат
        b.anchor = GridBagConstraints.WEST;
        b.gridx = 0;
        b.gridy = 2;
        b.gridwidth = 1;
        b.gridheight = 1;
        gbl.setConstraints(numbQuotes, b);
        container.add(numbQuotes);

        GridBagConstraints d = new GridBagConstraints(); //кнопка написать
        d.gridx = 0;
        d.gridy = 3;
        d.gridwidth = 1;
        d.gridheight = 1;
        gbl.setConstraints(buttonWrite, d);
        container.add(buttonWrite);

        GridBagConstraints e = new GridBagConstraints(); //кнопка изменить
        e.anchor = GridBagConstraints.SOUTH;
        e.gridx = 0;
        e.gridy = 5;
        e.gridwidth = 1;
        e.gridheight = 1;
        gbl.setConstraints(buttonChange, e);
        container.add(buttonChange);

        connection = getConnection();
        List<Quote> quoteList = getQuotes();
        GridBagConstraints f = new GridBagConstraints();//цитаты
        f.gridx = 1;
        f.gridy = 0;
        f.gridwidth = 3;
        f.gridheight = 5;
        TableModel tableModel = new MyTableModel(quoteList);
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {// если нажали на строчку в таблице то
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = table.getSelectedRow();
                Integer u_id = (Integer) table.getValueAt(row, 5);
                if ((u_id == user.getId() && user.getFunction().contains("3")) || user.getRole().equals("SUPER")) {
                    try {
                        PreparedStatement pStatement = connection.prepareStatement("SELECT * FROM quote_teacher WHERE id = ?");
                        pStatement.setInt(1, (Integer) table.getValueAt(row, 0));
                        ResultSet rSet = pStatement.executeQuery();
                        rSet.next();
                        Quote quote = new Quote(rSet);
                        WriteQuote writeQuote = new WriteQuote(self, user, connection, quote);
                        writeQuote.setVisible(true);
                        List<Quote> quoteList = getQuotes();
                        TableModel tableModel = new MyTableModel(quoteList);
                        table = new JTable(tableModel);
                        table.validate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "У вас нет прав на изменение цитат");
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        gbl.setConstraints(scrollPane, f);
        container.add(scrollPane);
    }

    private List<Quote> getQuotes() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM quote_teacher ");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Quote> quoteList = new ArrayList<>();
        while (resultSet.next()) { //прочитали все цитаты и засунули их в лист
            Quote quote = new Quote(resultSet);
            quoteList.add(quote);
        }
        preparedStatement.close();
        return quoteList;
    }

    public void start() throws SQLException {
        Authorization authorization = new Authorization(this, connection);
        authorization.setVisible(true);
        if (authorization.isSucceeded()) {
            user = authorization.getUser();
        } else {
            user = new User();
            user.setRole("GUEST");
        }
        login.setText("Логин: " + user.getLogin());
        role.setText("Роль: " + user.getRole());
        numbQuotes.setText("Количество цитат: " + getCountQuote());

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

    public void deleteRow(Quote quote) {
        MyTableModel model = (MyTableModel) table.getModel();
        model.removeRow(quote);
        table.repaint();
    }

    private int getCountQuote() throws SQLException { //считает количество цитат у текущего пользователя
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM `quote_teacher` WHERE id_user =?");
        preparedStatement.setInt(1, user.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int result = resultSet.getInt(1);
        preparedStatement.close();
        return result;
    }

    private int getCountQuote2() throws SQLException { //второй способ подсчета цитат
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `quote_teacher` WHERE id_user =?");
        preparedStatement.setInt(1, user.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        int count = 0;
        while (resultSet.next()) {
            count++;
        }
        preparedStatement.close();
        return count;
    }
}

