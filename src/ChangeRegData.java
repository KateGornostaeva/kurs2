import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class ChangeRegData extends JDialog {
    private JLabel newLogin = new JLabel("Новый логин");
    private JLabel newPassword = new JLabel("Новый пароль");
    private JLabel group = new JLabel("Группа");
    private JTextField inputLogin = new JTextField("", 10);
    private JTextField inputPassword = new JTextField("", 10);
    private JTextField inputGroup = new JTextField("", 10);
    private JButton buttonSave = new JButton("Сохранить изменения");
    private JButton buttonCancel = new JButton("Отмена");
    private boolean succeeded;

    public ChangeRegData(Frame parent, User user, Connection connection) throws HeadlessException {
        super(parent, "Изменить регистрационные данные", true);
        this.setBounds(150, 150, 450, 350);

        buttonSave.addActionListener(new ActionListener() {//нажимаешь на "ок"
            // и перебрасывает в окно с цитатами
            @Override
            public void actionPerformed(ActionEvent e) {//меняем регистрационные данные пользователя
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
        GridBagLayout gbl = new GridBagLayout();
        container.setLayout(gbl);

        GridBagConstraints a = new GridBagConstraints();
        a.gridx = GridBagConstraints.CENTER;
        a.gridy = 0;
        a.gridwidth = 1;
        a.gridheight = 1;
        gbl.setConstraints(newLogin, a);
        container.add(newLogin);

        GridBagConstraints b = new GridBagConstraints();
        b.gridx = GridBagConstraints.CENTER;
        b.gridy = 1;
        b.gridwidth = 1;
        b.gridheight = 1;
        gbl.setConstraints(inputLogin, b);
        container.add(inputLogin);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = GridBagConstraints.CENTER;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        gbl.setConstraints(newPassword, c);
        container.add(newPassword);

        GridBagConstraints d = new GridBagConstraints();
        d.gridx = GridBagConstraints.CENTER;
        d.gridy = 3;
        d.gridwidth = 1;
        d.gridheight = 1;
        gbl.setConstraints(inputPassword, d);
        container.add(inputPassword);

        GridBagConstraints e = new GridBagConstraints();
        e.gridx = GridBagConstraints.CENTER;
        e.gridy = 4;
        e.gridwidth = 1;
        e.gridheight = 1;
        gbl.setConstraints(group, e);
        container.add(group);

        GridBagConstraints f = new GridBagConstraints();
        f.gridx = GridBagConstraints.CENTER;
        f.gridy = 5;
        f.gridwidth = 1;
        f.gridheight = 1;
        gbl.setConstraints(inputGroup, f);
        container.add(inputGroup);

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = GridBagConstraints.CENTER-1;
        g.gridy = 6;
        g.gridwidth = 1;
        g.gridheight = 1;
        gbl.setConstraints(buttonSave, g);
        container.add(buttonSave);

        GridBagConstraints h = new GridBagConstraints();
        h.gridx = GridBagConstraints.CENTER+1;
        h.gridy = 6;
        h.gridwidth = 1;
        h.gridheight = 1;
        gbl.setConstraints(buttonCancel, h);
        container.add(buttonCancel);
    }
}
