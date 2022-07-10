import entity.Quote;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class WriteQuote extends JDialog {
    private JLabel writeQuote = new JLabel("Напишите цитату");
    private JTextArea inputQuote = new JTextArea(4, 30);
    private JLabel writeTeacher = new JLabel("Преподаватель");
    private JTextArea inputTeacher = new JTextArea(4, 30);
    private JLabel writeSubject = new JLabel("Предмет");
    private JTextArea inputSubject = new JTextArea(4, 30);
    private JButton buttonPost = new JButton("Опубликовать");
    private JButton buttonCancel = new JButton("Отмена");
    private JButton buttonDelete = new JButton("Удалить цитату");


    private Quote quote = new Quote();
    private QuotesDialog parentQD;

    public WriteQuote(Frame parent, User user, Connection connection, Quote quote) {
        this(parent, user, connection);
        this.quote = quote;
        this.parentQD = (QuotesDialog) parent;
        inputQuote.setText(quote.getQuote());
        inputSubject.setText(quote.getSubject());
        inputTeacher.setText(quote.getTeacher());

    }

    public WriteQuote(Frame parent, User user, Connection connection) throws HeadlessException {
        super(parent, "Публикация цитаты", true);
        this.setBounds(200, 200, 550, 400);

        buttonPost.addActionListener(new ActionListener() {//публикация и изменение цитат
            //нажимаешь на "опубликовать" и перебрасывает в окно с цитатами
            @Override
            public void actionPerformed(ActionEvent e) {
                quote.setQuote(inputQuote.getText());
                quote.setDate(Date.valueOf(LocalDate.now()));//текущее время ставит
                quote.setSubject(inputSubject.getText());
                quote.setTeacher(inputTeacher.getText());
                quote.setId_user(user.getId());
                try {
                    if (quote.getId() == null) {//id есть только у цитат которые уже были добавлены в БД, тк до этого мы его не ставим,
                        //а только сама БД автоматически это делает. А, если у цитаты есть id, то мы можем ее изменить
                        quote.save(connection);
                    } else {
                        quote.update(connection);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                dispose();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {//нажимаешь на "отмена"
            // и перебрасывает в окно с цитатами
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (quote.getId() != null) {
                        if ((user.getId() == quote.getId_user() && user.getFunction().contains("4")) || user.getRole().equals("SUPER")) {
                            quote.delete(connection);
                            parentQD.deleteRow(quote);
                        } else {
                            JOptionPane.showMessageDialog(null, "У вас недостаточно прав");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                dispose();
            }
        });

        Container container = this.getContentPane();
        GridBagLayout gbl = new GridBagLayout();
        container.setLayout(gbl);

        GridBagConstraints f = new GridBagConstraints();
        f.gridx = 1;
        f.gridy = 0;
        f.gridwidth = 4;
        f.gridheight = 1;
        gbl.setConstraints(writeTeacher, f);
        container.add(writeTeacher);

        GridBagConstraints e = new GridBagConstraints();
        e.gridx = 1;
        e.gridy = 1;
        e.gridwidth = 4;
        e.gridheight = 1;
        gbl.setConstraints(inputTeacher, e);
        container.add(inputTeacher);

        GridBagConstraints h = new GridBagConstraints();
        h.gridx = 1;
        h.gridy = 2;
        h.gridwidth = 4;
        h.gridheight = 1;
        gbl.setConstraints(writeSubject, h);
        container.add(writeSubject);

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 1;
        g.gridy = 3;
        g.gridwidth = 4;
        g.gridheight = 1;
        gbl.setConstraints(inputSubject, g);
        container.add(inputSubject);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;//номер столбца и
        c.gridy = 4;// номер строки для ячейки
        c.gridwidth = 10;//количество ячеек,
        c.gridheight = 1;//занимаемых добавляемым компонентом
        gbl.setConstraints(writeQuote, c);
        container.add(writeQuote);

        GridBagConstraints a = new GridBagConstraints();//текст цитаты
        a.gridx = 1;
        a.gridy = 5;
        a.gridwidth = 8;
        a.gridheight = 5;
        gbl.setConstraints(inputQuote, a);
        container.add(inputQuote);

        GridBagConstraints b = new GridBagConstraints(); //кнопка
        b.gridx = 1;
        b.gridy = 10;
        b.gridwidth = 4;
        b.gridheight = 1;
        gbl.setConstraints(buttonPost, b);
        container.add(buttonPost);

        GridBagConstraints d = new GridBagConstraints(); //кнопка
        d.gridx = 1;
        d.gridy = 11;
        d.gridwidth = 4;
        d.gridheight = 1;
        gbl.setConstraints(buttonCancel, d);
        container.add(buttonCancel);

        GridBagConstraints i = new GridBagConstraints();
        i.gridx = 1;
        i.gridy = 12;
        i.gridwidth = 4;
        i.gridheight = 1;
        gbl.setConstraints(buttonDelete, i);
        container.add(buttonDelete);
    }

    public Quote getQuote() {
        return quote;
    }
}
