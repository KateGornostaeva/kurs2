import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Registration extends JDialog {
    private JLabel writeLogin = new JLabel("Введите логин");
    private JLabel writePassword = new JLabel("Введите пароль");
    private JLabel writeGroup = new JLabel("Введите группу");
    private JTextField inputLogin = new JTextField("", 10);
    private JTextField inputPassword = new JTextField("", 10);
    private JTextField inputGroup = new JTextField("", 10);
    private JButton buttonOK = new JButton("OK");
    private JButton buttonCancel = new JButton("Отмена");
    private boolean succeeded;
    private Connection connection;

    public Registration(Frame parent, Connection connection) throws HeadlessException {
        super(parent, "Регистрация", true);
        this.setBounds(200, 200, 350, 300);
        this.connection = connection;

        buttonOK.addActionListener(new ActionListener() {//нажимаешь на "ок"
            // и перебрасывает в окно с авторизацией
            @Override
            public void actionPerformed(ActionEvent e) {
            //если нет логина или пароля или группы, то выводит текст
                if (inputPassword.getText().isEmpty() || inputGroup.getText().isEmpty() || inputLogin.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Проверьте данные");
                    return;
                }

                try { //проверяет есть ли уже такой пользователь
                    PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE login = ?");
                    statement.setString(1, inputLogin.getText());
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(null, "Такой пользователь уже существует");
                        return;
                    }//если такого пользователя нет, то сохраняет в БД
                    User user = new User();
                    user.setLogin(inputLogin.getText());
                    user.setHash_pass(inputPassword.getText());
                    user.setRole("USER");
                    user.setGroupe(Integer.valueOf(inputGroup.getText()));
                    user.save(connection);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                succeeded = true;
                dispose();
            }
        });
        buttonCancel.addActionListener(new ActionListener() {//нажимаешь на "отмена"
            // и перебрасывает в окно с авторизацией
            @Override
            public void actionPerformed(ActionEvent e) {
                succeeded = true;
                dispose();
            }
        });
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(7, 1, 2, 2));
        container.add(writeLogin);
        container.add(inputLogin);
        container.add(writePassword);
        container.add(inputPassword);
        container.add(writeGroup);
        container.add(inputGroup);
        container.add(buttonOK);
        container.add(buttonCancel);
    }
}
