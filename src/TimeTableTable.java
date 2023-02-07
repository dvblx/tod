import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TimeTableTable extends AbstractTableModel {
    private static final String[] headers = {"ID", "Врач", "Клиника", "День","Время приёма"};
    private final List<Entities.TimeTable> timeTableList;
    private final DentistFunctions dentistFunctions = new DentistFunctions();

    public TimeTableTable(List<Entities.TimeTable> ttlist) {
        this.timeTableList = ttlist;
    }

    @Override
    public int getRowCount() {
        return timeTableList.size();
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
        Entities.TimeTable timeTable = timeTableList.get(row);
        return switch (col) {
            case 0 -> timeTable.getTt_id();
            case 1 -> timeTable.getDentist();
            case 2 -> timeTable.getDentistry();
            case 3 -> timeTable.getDay();
            default -> timeTable.getAdmission_time();
        };
    }
}
