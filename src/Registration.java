import javax.swing.*;
import java.awt.*;

public class Registration extends JDialog {
    private JLabel l1 = new JLabel("Введите логин");
    private JLabel l2 = new JLabel("Введите пароль");
    private JTextField inputLogin = new JTextField("", 10);
    private JTextField inputPassword = new JTextField("", 10);
    private JButton button1 = new JButton("OK");
    private JButton button2 = new JButton("Отмена");

    public Registration(Frame parent) throws HeadlessException {
        super(parent,"Регистрация",true);
        this.setBounds(100, 100, 350, 150);


        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3, 2, 2, 2));
        container.add(l1);
        container.add(inputLogin);
        container.add(l2);
        container.add(inputPassword);
        container.add(button1);
        container.add(button2);
    }
}
