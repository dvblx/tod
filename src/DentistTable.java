import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import java.util.List;

public class DentistTable extends AbstractTableModel {
    private static final String[] headers = {"ID", "ФИО", "Клиника", "Стаж","Специализация"};
    private final List<Entities.Dentist> dentistList;

    public DentistTable(List<Entities.Dentist> dlist) {
        this.dentistList = dlist;
    }

    @Override
    public int getRowCount() {
        return dentistList.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int col) {
        return headers[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Entities.Dentist dentist = dentistList.get(row);
        return switch (col) {
            case 0 -> dentist.getDentist_id();
            case 1 -> dentist.getDentist_name();
            case 2 -> dentist.getDentistry();
            case 3 -> dentist.getExperience();
            default -> dentist.getDentist_type();
        };
    }
}
