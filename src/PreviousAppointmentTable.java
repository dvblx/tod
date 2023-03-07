import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PreviousAppointmentTable extends AbstractTableModel {
    private static final String[] headers = {"ID", "Врач", "Клиника", "День", "Время", "Пациент", "Диагноз", "Стоимость"};
    private final List<Entities.PreviousAppointment> appointmentsList;

    public PreviousAppointmentTable(List<Entities.PreviousAppointment> alist) {
        this.appointmentsList = alist;
    }

    @Override
    public int getRowCount() {
        return appointmentsList.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public String getColumnName(int col) {
        return headers[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Entities.PreviousAppointment appointment = appointmentsList.get(row);
        return switch (col) {
            case 0 -> appointment.getPrevious_appointment_id();
            case 1 -> appointment.getDentist();
            case 2 -> appointment.getDentistry();
            case 3 -> appointment.getAppointment_day();
            case 4 -> appointment.getAppointment_time();
            case 5 -> appointment.getPatient();
            case 6 -> appointment.getDiagnosis();
            default ->  appointment.getAdmission_price();
        };
    }
}
