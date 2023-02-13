import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DentistryTable extends AbstractTableModel {
    private static final String[] headers = {"ID", "Название", "Адрес", "Телефон", "Заведующий клиникой",
            "Год основания", "Количество клиентов"};
    private final List<Entities.Dentistry> dentistryList;

    public DentistryTable(List<Entities.Dentistry> dlist) {
        this.dentistryList = dlist;
    }

    @Override
    public int getRowCount() {
        return dentistryList.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public String getColumnName(int col) {
        return headers[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Entities.Dentistry dentistry = dentistryList.get(row);
        return switch (col) {
            case 0 -> dentistry.getDentistry_id();
            case 1 -> dentistry.getName();
            case 2 -> dentistry.getAddress();
            case 3 -> dentistry.getPhone();
            case 4 -> dentistry.getHead_of_clinic();
            case 5 -> dentistry.getFoundation_year();
            default -> dentistry.getCustomer_count();
        };
    }

}
