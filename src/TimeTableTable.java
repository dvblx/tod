import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TimeTableTable extends AbstractTableModel {
    private static final String[] headers = {"ID", "Врач", "Клиника", "День","Время приёма"};
    private final List<Entities.TimeTable> timeTableList;

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
            case 1 -> timeTable.getDentist_id();
            case 2 -> timeTable.getDentistry_id();
            case 3 -> timeTable.getDay_id();
            default -> timeTable.getAdmission_time();
        };
    }
}
