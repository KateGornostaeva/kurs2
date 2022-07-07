import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JDialog {
    private JLabel l1 = new JLabel("Логин");
    private JLabel l2 = new JLabel("Пароль");
    private JTextField inputLogin = new JTextField("", 10);
    private JTextField inputPassword = new JTextField("", 10);
    private JButton buttonOK = new JButton("OK");
    private JButton buttonReg = new JButton("Регистрация");
    private JButton buttonGuest = new JButton("Войти как гость");
    private boolean succeeded;

    public Login(Frame parent) throws HeadlessException {
        super(parent, "Авторизация", true);
        this.setBounds(150, 150, 350, 200);

        buttonOK.addActionListener(new ActionListener() { //нажимаешь на "ок"
            // и перебрасывает в окно с цитатами
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO проверить авторизацию
                succeeded = true;
                dispose();
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
                Registration reg = new Registration(parent);
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
}
