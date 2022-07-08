import entity.Quote;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

    private List<Quote> quotes;

    public MyTableModel(List<Quote> quotes) {
        this.quotes = quotes;
    }

    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public int getColumnCount() {
        return 6;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "id";
            case 1:
                return "Цитата";
            case 2:
                return "Преподаватель";
            case 3:
                return "Предмет";
            case 4:
                return "Дата";
            case 5:
                return "Студент";
        }
        return "";
    }

    public int getRowCount() {
        return quotes.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Quote quote = quotes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return quote.getId();
            case 1:
                return quote.getQuote();
            case 2:
                return quote.getTeacher();
            case 3:
                return quote.getSubject();
            case 4:
                return quote.getData();
            case 5:
                return quote.getId_user();
        }
        return "";
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {

    }
}
