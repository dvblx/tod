import java.io.File;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppointmentFunctions extends BaseFunctions {
    private final List<Entities.Appointments> appointmentsList = new ArrayList<>();
    private Entities.Appointments appointment;
    private DentistFunctions dentistFunctions = new DentistFunctions();
    private int number_of_report = 1;

    public List<Entities.Appointments> get_appointments() {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                appointmentsList.clear();
                try {
                    Statement stmt = con.createStatement();
                    String query = "select appointments.appointment_id, dentist.dentist_name, dentistry.dentistry_name,\n" +
                            "appointments.appointment_day, appointments.appointment_time from appointments \n" +
                            "join dentist on appointments.dentist_id = dentist.dentist_id\n" +
                            "join dentistry on dentist.dentistry_id = dentistry.dentistry_id";
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        appointment = new Entities.Appointments(rs.getInt(1), rs.getString(2),
                                rs.getString(3), String.valueOf(rs.getDate(4)), String.valueOf(rs.getTime(5)));
                        appointmentsList.add(appointment);
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
                List<Entities.Dentist> dentists = dentistFunctions.get_dentists();
                for (Entities.Dentist dentist: dentists){
                    if (appointment.getDentist().equals(dentist.getDentist_name())){
                        stmt.setInt(1, dentist.getDentist_id());
                        break;
                    }
                }
                stmt.setDate(2, Date.valueOf(appointment.getAppointment_day()));
                stmt.setTime(3, Time.valueOf(appointment.getAppointment_time()));
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public boolean checkAppointment(String name, String day, String time){
        boolean result = false;
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                try {
                    appointment = null;
                    Statement stmt = con.createStatement();
                    String query = "select appointments.appointment_id, dentist.dentist_name, dentistry.dentistry_name,\n" +
                            "appointments.appointment_day, appointments.appointment_time from appointments \n" +
                            "join dentist on appointments.dentist_id = dentist.dentist_id\n" +
                            "join dentistry on dentist.dentistry_id = dentistry.dentistry_id\n" +
                            "where dentist.dentist_name = '" + name + "' and appointments.appointment_day = '" + day + "'\n" +
                            "and appointments.appointment_time = '" + time + "'";
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        appointment = new Entities.Appointments(rs.getInt(1), rs.getString(2),
                                rs.getString(3), String.valueOf(rs.getDate(4)), String.valueOf(rs.getTime(5)));

                    }
                    if (appointment == null){result =  true;}
                    rs.close();
                    stmt.close();

                } finally {
                    con.close();
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
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
