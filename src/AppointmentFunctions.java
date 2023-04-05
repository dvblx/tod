import java.io.File;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppointmentFunctions extends BaseFunctions {
    private final List<Entities.ForthcomingAppointment> appointmentsList = new ArrayList<>();
    private Entities.ForthcomingAppointment appointment;
    private DentistFunctions dentistFunctions = new DentistFunctions();
    private int number_of_report = 1;

    public List<Entities.ForthcomingAppointment> get_appointments() {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                appointmentsList.clear();
                try {
                    LocalDate localDate = LocalDate.now();
                    LocalTime localTime = LocalTime.now();
                    Statement stmt = con.createStatement();
                    String query = "select appointments.appointment_id, dentist.dentist_name, dentistry.dentistry_name,\n" +
                            "appointments.appointment_day, appointments.appointment_time, appointments.patient from appointments \n" +
                            "join dentist on appointments.dentist_id = dentist.dentist_id\n" +
                            "join dentistry on dentist.dentistry_id = dentistry.dentistry_id\n" +
                            "where appointments.appointment_day > '" + localDate + "' or (appointments.appointment_day = '" +
                            localDate + "' and appointments.appointment_time > '" + localTime + "')";
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        appointment = new Entities.ForthcomingAppointment(rs.getInt(1), rs.getString(2),
                                rs.getString(3), String.valueOf(rs.getDate(4)),
                                String.valueOf(rs.getTime(5)), rs.getString(6));
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
    public List<Entities.ForthcomingAppointment> filter_by_clinic(String clinic_name){
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                appointmentsList.clear();
                try {
                    LocalDate localDate = LocalDate.now();
                    LocalTime localTime = LocalTime.now();
                    Statement stmt = con.createStatement();
                    String query = "select appointments.appointment_id, dentist.dentist_name, dentistry.dentistry_name,\n" +
                            "appointments.appointment_day, appointments.appointment_time, appointments.patient from appointments \n" +
                            "join dentist on appointments.dentist_id = dentist.dentist_id\n" +
                            "join dentistry on dentist.dentistry_id = dentistry.dentistry_id\n" +
                            "where appointments.appointment_day > '" + localDate + "' or (appointments.appointment_day = '" +
                            localDate + "' and appointments.appointment_time > '" + localTime + "') and dentistry.dentistry_name = '" + clinic_name + "'";
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        appointment = new Entities.ForthcomingAppointment(rs.getInt(1), rs.getString(2),
                                rs.getString(3), String.valueOf(rs.getDate(4)),
                                String.valueOf(rs.getTime(5)), rs.getString(6));
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
    public List<Entities.ForthcomingAppointment> get_one_doctor_appointments(String clinic_name, String doctor_name){
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                appointmentsList.clear();
                try {
                    LocalDate localDate = LocalDate.now();
                    LocalTime localTime = LocalTime.now();
                    Statement stmt = con.createStatement();
                    String query = "select appointments.appointment_id, dentist.dentist_name, dentistry.dentistry_name,\n" +
                            "appointments.appointment_day, appointments.appointment_time, appointments.patient from appointments \n" +
                            "join dentist on appointments.dentist_id = dentist.dentist_id\n" +
                            "join dentistry on dentist.dentistry_id = dentistry.dentistry_id\n" +
                            "where appointments.appointment_day > '" + localDate + "' or (appointments.appointment_day = '" +
                            localDate + "' and appointments.appointment_time > '" + localTime + "') and dentistry.dentistry_name = '"
                            + clinic_name + "' and dentist.dentist_name = '" + doctor_name + "'";
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        appointment = new Entities.ForthcomingAppointment(rs.getInt(1), rs.getString(2),
                                rs.getString(3), String.valueOf(rs.getDate(4)),
                                String.valueOf(rs.getTime(5)), rs.getString(6));
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

    public void addAppointment(Entities.ForthcomingAppointment appointment) {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                String insert = "insert into appointments(dentist_id, appointment_day, appointment_time, patient) values" +
                        "(?, ?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(insert);
                List<Entities.Dentist> dentists = dentistFunctions.get_dentists();
                for (Entities.Dentist dentist : dentists) {
                    if (appointment.getDentist().equals(dentist.getDentist_name())) {
                        stmt.setInt(1, dentist.getDentist_id());
                        break;
                    }
                }
                stmt.setDate(2, Date.valueOf(appointment.getAppointment_day()));
                stmt.setTime(3, Time.valueOf(appointment.getAppointment_time()));
                stmt.setString(4, appointment.getPatient());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteAppointment(int id) {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                String del = "DELETE FROM APPOINTMENTS WHERE appointment_id = ?";
                PreparedStatement stmt = con.prepareStatement(del);
                stmt.setInt(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean checkAppointment(String name, String day, String time) {
        boolean result = false;
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                try {
                    appointment = null;
                    Statement stmt = con.createStatement();
                    String query = "select appointments.appointment_id, dentist.dentist_name, dentistry.dentistry_name,\n" +
                            "appointments.appointment_day, appointments.appointment_time, appointments.patient from appointments \n" +
                            "join dentist on appointments.dentist_id = dentist.dentist_id\n" +
                            "join dentistry on dentist.dentistry_id = dentistry.dentistry_id\n" +
                            "where dentist.dentist_name = '" + name + "' and appointments.appointment_day = '" + day + "'\n" +
                            "and appointments.appointment_time = '" + time + "'";
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        appointment = new Entities.ForthcomingAppointment(rs.getInt(1), rs.getString(2),
                                rs.getString(3), String.valueOf(rs.getDate(4)),
                                String.valueOf(rs.getTime(5)), rs.getString(6));

                    }
                    if (appointment == null) {
                        result = true;
                    }
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

    public String createReport(List<Entities.ForthcomingAppointment> appointments) {
        String[] headers = {"ID", "Врач", "Клиника", "Дата приёма", "Время приёма", "Пациент"};
        String name_of_report = "-";
        try {
            name_of_report = "Приёмы №" + number_of_report + ".csv";
            File file = new File(name_of_report);
            if (!file.exists()) {
                file.createNewFile();
                number_of_report++;
            }
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < headers.length; i++) {
                if (i != headers.length - 1) {
                    text.append(headers[i]).append(", ");
                } else {
                    text.append(headers[i]).append("\n");
                }
            }
            for (Entities.ForthcomingAppointment a : appointments) {
                text.append(a.getId()).append(", ");
                text.append(a.getDentist()).append(", ");
                text.append(a.getDentistry()).append(", ");
                text.append(a.getAppointment_day()).append(", ");
                text.append(a.getAppointment_time()).append(", ");
                text.append(a.getPatient()).append("\n");
            }
            if (file.exists()) {
                PrintWriter pw = new PrintWriter(file);
                pw.println(text);
                pw.close();
            }
        } catch (Exception exc) {
            System.out.print("Exeption");
        }

        return name_of_report;
    }
}
