import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JDialog {
    private JLabel l1 = new JLabel("Новый логин");
    private JLabel l2 = new JLabel("Новый пароль");
    private JTextField inputLogin = new JTextField("", 10);
    private JTextField inputPassword = new JTextField("", 10);
    private JButton buttonSave = new JButton("Сохранить изменения");
    private JButton buttonCancel = new JButton("Отмена");
    private boolean succeeded;

    public Settings(Frame parent) throws HeadlessException {
        super(parent,"Изменить регистрационные данные",true);
        this.setBounds(150, 150, 350, 300);

        buttonSave.addActionListener(new ActionListener() {//нажимаешь на "ок"
            // и перебрасывает в окно с цитатами
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO проверить регистрацию
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
        container.add(buttonSave);
        container.add(buttonCancel);
    }
}
