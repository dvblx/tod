import java.io.File;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppointmentFunctions extends BaseFunctions {
    private final List<Entities.Appointments> appointmentsList = new ArrayList<>();
    private Entities.Appointments appointment;
    private int number_of_report = 1;

    public List<Entities.Appointments> get_appointments() {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                appointmentsList.clear();
                try {
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("select * from appointments");
                    while (rs.next()) {
                        System.out.println(rs);
                    }
                    rs.close();
                    stmt.close();

                } finally {
                    con.close();
                }
                return appointmentsList;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public void addAppointment(Entities.Appointments appointment){
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                String insert = "insert into appointments(dentist_id, appointment_day, appointment_time) values" +
                        "(?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(insert);
                stmt.setInt(1, 0);
                stmt.setDate(2, Date.valueOf(appointment.getAppointment_day()));
                stmt.setTime(3, Time.valueOf(appointment.getAppointment_time()));
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public String createReport(List<Entities.Appointments> appointments){
        String[] headers = {"ID", "Врач", "Клиника", "Дата приёма", "Время приёма"};
        String name_of_report = "-";
        try{
            name_of_report = "Приёмы №" + number_of_report + ".csv";
            File file = new File(name_of_report);
            if (!file.exists()) {
                file.createNewFile();
                number_of_report++;
            }
            StringBuilder text = new StringBuilder();
            for (int i = 0; i<headers.length; i++){
                if(i != headers.length-1) {text.append(headers[i]).append(", ");}
                else {text.append(headers[i]).append("\n");}
            }
            for (Entities.Appointments a: appointments){
                text.append(a.getId()).append(", ");
                text.append(a.getDentist()).append(", ");
                text.append(a.getDentistry()).append(", ");
                text.append(a.getAppointment_day()).append(", ");
                text.append(a.getAppointment_time()).append("\n");
            }
            if (file.exists()) {
                PrintWriter pw = new PrintWriter(file);
                pw.println(text);
                pw.close();
            }
        }
        catch (Exception exc) {
            System.out.print("Exeption");
        }

        return name_of_report;
    }
}
