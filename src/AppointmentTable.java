import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AppointmentTable extends AbstractTableModel {
    private static final String[] headers = {"ID", "Врач", "Клиника", "День", "Время"};
    private final List<Entities.Appointments> appointmentsList;

    public AppointmentTable(List<Entities.Appointments> alist) {
        this.appointmentsList = alist;
    }

    @Override
    public int getRowCount() {
        return appointmentsList.size();
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
        Entities.Appointments appointments = appointmentsList.get(row);
        switch (col) {
            case 0:
                return appointments.getId();
            case 1:
                return appointments.getDentist();
            case 2:
                return appointments.getDentistry();
            case 3:
                return appointments.getAppointment_day();
            default:
                return appointments.getAppointment_time();
        }
    }
}
