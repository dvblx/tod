import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AppointmentTable extends AbstractTableModel {
    private static final String[] headers = {"ID", "Врач", "Клиника", "День", "Время", "Пациент"};
    private final List<Entities.ForthcomingAppointment> appointmentsList;

    public AppointmentTable(List<Entities.ForthcomingAppointment> alist) {
        this.appointmentsList = alist;
    }

    @Override
    public int getRowCount() {
        return appointmentsList.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int col) {
        return headers[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Entities.ForthcomingAppointment appointments = appointmentsList.get(row);
        return switch (col) {
            case 0 -> appointments.getId();
            case 1 -> appointments.getDentist();
            case 2 -> appointments.getDentistry();
            case 3 -> appointments.getAppointment_day();
            case 4 -> appointments.getAppointment_time();
            default -> appointments.getPatient();
        };
    }
}
