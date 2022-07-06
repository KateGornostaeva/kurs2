import javax.swing.*;
import java.awt.*;

public class Quotes extends JFrame {
    private JLabel l1 = new JLabel("Логин:");
    private JLabel l2 = new JLabel("Роль:");
    private JLabel l3 = new JLabel("Количество цитат:");
    private JButton button1 = new JButton("Написать цитату");
    private JButton button2 = new JButton("Изменить цитату");
    private JButton button3 = new JButton("Настройки");

    public Quotes() throws HeadlessException {
        super("Основное");
        this.setBounds(100, 100, 350, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(6, 8, 2, 2));
        container.add(l1);
        container.add(l2);
        container.add(l3);
        container.add(button1);
        container.add(button2);
        container.add(button3);
        Login login = new Login(this);
        login.setVisible(true);
    }

}

