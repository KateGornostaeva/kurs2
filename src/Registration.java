import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registration extends JDialog {
    private JLabel l1 = new JLabel("Введите логин");
    private JLabel l2 = new JLabel("Введите пароль");
    private JTextField inputLogin = new JTextField("", 10);
    private JTextField inputPassword = new JTextField("", 10);
    private JButton buttonOK = new JButton("OK");
    private JButton buttonCancel = new JButton("Отмена");
    private boolean succeeded;

    public Registration(Frame parent) throws HeadlessException {
        super(parent,"Регистрация",true);
        this.setBounds(200, 200, 350, 300);

        buttonOK.addActionListener(new ActionListener() {//нажимаешь на "ок"
            // и перебрасывает в окно с авторизацией
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO проверить регистрацию
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
        container.add(l1);
        container.add(inputLogin);
        container.add(l2);
        container.add(inputPassword);
        container.add(buttonOK);
        container.add(buttonCancel);
    }
}
