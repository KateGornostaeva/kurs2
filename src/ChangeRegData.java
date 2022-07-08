import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class ChangeRegData extends JDialog {
    private JLabel l1 = new JLabel("Новый логин");
    private JLabel l2 = new JLabel("Новый пароль");
    private JLabel l3 = new JLabel("Группа");
    private JTextField inputLogin = new JTextField("", 10);
    private JTextField inputPassword = new JTextField("", 10);
    private JTextField inputGroup = new JTextField("", 10);
    private JButton buttonSave = new JButton("Сохранить изменения");
    private JButton buttonCancel = new JButton("Отмена");
    private boolean succeeded;

    public ChangeRegData(Frame parent, User user, Connection connection) throws HeadlessException {
        super(parent, "Изменить регистрационные данные", true);
        this.setBounds(150, 150, 350, 300);

        buttonSave.addActionListener(new ActionListener() {//нажимаешь на "ок"
            // и перебрасывает в окно с цитатами
            @Override
            public void actionPerformed(ActionEvent e) {//меняем рег. данные пользователя
                user.setLogin(inputLogin.getText());
                user.setHash_pass(inputPassword.getText());
                user.setGroupe(Integer.valueOf(inputGroup.getText()));
                try {
                    user.save(connection);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                succeeded = true;
                dispose();
            }
        });
        buttonCancel.addActionListener(new ActionListener() {//нажимаешь на "отмена"
            // и перебрасывает в окно с цитатами
            @Override
            public void actionPerformed(ActionEvent e) {
                succeeded = true;
                dispose();
            }
        });

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(7, 1, 2, 2));
        container.add(l1);
        container.add(inputLogin);
        container.add(l2);
        container.add(inputPassword);
        container.add(l3);
        container.add(inputGroup);
        container.add(buttonSave);
        container.add(buttonCancel);
    }
}
