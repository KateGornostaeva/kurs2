import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JDialog {
    private JLabel l1 = new JLabel("Логин");
    private JLabel l2 = new JLabel("Пароль");
    private JTextField inputLogin = new JTextField("", 10);
    private JTextField inputPassword = new JTextField("", 10);
    private JButton buttonOK = new JButton("OK");
    private JButton buttonReg = new JButton("Регистрация");
    private JButton buttonGuest = new JButton("Войти как гость");
    private boolean succeeded = false;
    private Connection connection;
    private User user;

    public Login(Frame parent, Connection connection) throws HeadlessException {
        super(parent, "Авторизация", true);
        this.setBounds(150, 150, 350, 200);
        this.connection = connection;

        buttonOK.addActionListener(new ActionListener() { //нажимаешь на "ок"
            // и перебрасывает в окно с цитатами
            @Override
            public void actionPerformed(ActionEvent e) {
                try { //проверяет есть ли уже такой пользователь
                    PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE login = ? AND hash_pass = ?");
                    statement.setString(1, inputLogin.getText());
                    statement.setString(2,inputPassword.getText());
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) { //если логин и пароль верны, то перебрасывает в окно с цитатами
                        user = new User(resultSet);
                        succeeded = true;
                        dispose();
                        return;
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                succeeded = false;
                JOptionPane.showMessageDialog(null, "Неверный логин или пароль");
                return;
            }
        });
        buttonGuest.addActionListener(new ActionListener() {//нажимаешь на "войти как гость"
            // и перебрасывает в окно с цитатами
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO проверить авторизацию
                succeeded = true;
                dispose();
            }
        });
        buttonReg.addActionListener(new ActionListener() {//нажимаешь на "регистрацию" и перебрасывает
            // в окно для регистрации
            @Override
            public void actionPerformed(ActionEvent e) {
                Registration reg = new Registration(parent, connection);
                reg.setVisible(true);
            }
        });

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(4, 2, 2, 2));
        container.add(l1);
        container.add(inputLogin);
        container.add(l2);
        container.add(inputPassword);
        container.add(buttonOK);
        container.add(buttonReg);
        container.add(buttonGuest);
    }

    public boolean isSucceeded() { //проверка получилось ли авторизироватся
        return succeeded;
    }

    public User getUser() {
        return user;
    }
}
