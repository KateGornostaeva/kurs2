import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WriteQuote extends JDialog {
    private JLabel l1 = new JLabel("Напишите цитату");
    private JTextArea inputQuote = new JTextArea(4, 30);
    private JButton buttonPost = new JButton("Опубликовать");
    private JButton buttonCancel = new JButton("Отмена");
    private boolean succeeded;

    public WriteQuote(Frame parent) throws HeadlessException {
        super(parent,"Публикация цитаты",true);
        this.setBounds(200, 200, 450, 300);

        buttonPost.addActionListener(new ActionListener() {//нажимаешь на "опубликовать"
            // и перебрасывает в окно с цитатами
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO опубликовать цитату
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

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        gbl.setConstraints(l1,c);
        container.add(l1);

        GridBagConstraints a = new GridBagConstraints();//текст цитаты
        a.gridx = 0;
        a.gridy = 1;
        a.gridwidth = 3;
        a.gridheight = 5;
        gbl.setConstraints(inputQuote,a);
        container.add(inputQuote);

        GridBagConstraints b = new GridBagConstraints(); //кнопка
        b.gridx = 0;
        b.gridy = 6;
        b.gridwidth = 1;
        b.gridheight = 1;
        gbl.setConstraints(buttonPost,b);
        container.add(buttonPost);

        GridBagConstraints d = new GridBagConstraints(); //кнопка
        d.gridx = 2;
        d.gridy = 6;
        d.gridwidth = 1;
        d.gridheight = 1;
        gbl.setConstraints(buttonCancel,d);
        container.add(buttonCancel);
    }
}
