import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PreviousAppointmentFunctions extends BaseFunctions{
    private final AppointmentFunctions appointmentFunctions = new AppointmentFunctions();
    private final DentistFunctions dentistFunctions = new DentistFunctions();
    private final List<Entities.PreviousAppointment> previousAppointmentList = new ArrayList<>();
    private Entities.PreviousAppointment previousAppointment;
    private int number_of_report = 1;


    public List<Entities.PreviousAppointment> get_filled_appointments() {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                previousAppointmentList.clear();
                try {
                    LocalDate localDate = LocalDate.now();
                    LocalTime localTime = LocalTime.now();
                    Statement stmt = con.createStatement();
                    String query = "select previousappointments.previous_appointment_id, dentistry.dentistry_name, dentist.dentist_name, " +
                            "previousappointments.appointment_day, previousappointments.appointment_time, previousappointments.patient," +
                            "previousappointments.diagnosis, previousappointments.admission_price from previousappointments " +
                            "join dentist on previousappointments.dentist_id = dentist.dentist_id " +
                            "join dentistry on dentist.dentistry_id = dentistry.dentistry_id" +
                            " where diagnosis != 'нужно указать' or admission_price != 0 ";
                    ResultSet rs = stmt.executeQuery(query);
                    //PreviousAppointment(int previous_appointment_id, String dentistry, String dentist, String appointment_day,
                    //        String appointment_time, String patient, String diagnosis, int admission_price)
                    while (rs.next()) {
                        previousAppointment = new Entities.PreviousAppointment(rs.getInt(1), rs.getString(2),
                                rs.getString(3), String.valueOf(rs.getDate(4)), String.valueOf(rs.getTime(5)),
                                rs.getString(6), rs.getString(7), rs.getInt(8));
                        previousAppointmentList.add(previousAppointment);

                    }
                    rs.close();
                    stmt.close();

                } finally {
                    con.close();
                }
                return previousAppointmentList;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public List<Entities.PreviousAppointment> get_unfilled_appointments() {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                previousAppointmentList.clear();
                try {
                    LocalDate localDate = LocalDate.now();
                    LocalTime localTime = LocalTime.now();
                    Statement stmt = con.createStatement();
                    String query = "select previousappointments.previous_appointment_id, dentistry.dentistry_name, dentist.dentist_name, " +
                            "previousappointments.appointment_day, previousappointments.appointment_time, previousappointments.patient," +
                            "previousappointments.diagnosis, previousappointments.admission_price from previousappointments " +
                            "join dentist on previousappointments.dentist_id = dentist.dentist_id " +
                            "join dentistry on dentist.dentistry_id = dentistry.dentistry_id" +
                            " where diagnosis = 'нужно указать' or admission_price = 0";
                    ResultSet rs = stmt.executeQuery(query);
                    //PreviousAppointment(int previous_appointment_id, String dentistry, String dentist, String appointment_day,
                    //        String appointment_time, String patient, String diagnosis, int admission_price)
                    while (rs.next()) {
                        previousAppointment = new Entities.PreviousAppointment(rs.getInt(1), rs.getString(2),
                                rs.getString(3), String.valueOf(rs.getDate(4)), String.valueOf(rs.getTime(5)),
                                rs.getString(6), rs.getString(7), rs.getInt(8));
                        previousAppointmentList.add(previousAppointment);

                    }
                    rs.close();
                    stmt.close();

                } finally {
                    con.close();
                }
                return previousAppointmentList;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public List<Entities.PreviousAppointment> get_filled_appointments_with_filter_by_clinic(String clinic_name){
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                previousAppointmentList.clear();
                try {
                    LocalDate localDate = LocalDate.now();
                    LocalTime localTime = LocalTime.now();
                    Statement stmt = con.createStatement();
                    String query = "select previousappointments.previous_appointment_id, dentistry.dentistry_name, dentist.dentist_name, " +
                            "previousappointments.appointment_day, previousappointments.appointment_time, previousappointments.patient," +
                            "previousappointments.diagnosis, previousappointments.admission_price from previousappointments " +
                            "join dentist on previousappointments.dentist_id = dentist.dentist_id " +
                            "join dentistry on dentist.dentistry_id = dentistry.dentistry_id" +
                            " where (diagnosis != 'нужно указать' or admission_price != 0) and dentistry.dentistry_name = '" + clinic_name +"'";
                    ResultSet rs = stmt.executeQuery(query);
                    //PreviousAppointment(int previous_appointment_id, String dentistry, String dentist, String appointment_day,
                    //        String appointment_time, String patient, String diagnosis, int admission_price)
                    while (rs.next()) {
                        previousAppointment = new Entities.PreviousAppointment(rs.getInt(1), rs.getString(2),
                                rs.getString(3), String.valueOf(rs.getDate(4)), String.valueOf(rs.getTime(5)),
                                rs.getString(6), rs.getString(7), rs.getInt(8));
                        previousAppointmentList.add(previousAppointment);

                    }
                    rs.close();
                    stmt.close();

                } finally {
                    con.close();
                }
                return previousAppointmentList;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public List<Entities.PreviousAppointment> get_one_doctor_filled_appointments(String clinic_name, String doctor_name){
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                previousAppointmentList.clear();
                try {
                    LocalDate localDate = LocalDate.now();
                    LocalTime localTime = LocalTime.now();
                    Statement stmt = con.createStatement();
                    String query = "select previousappointments.previous_appointment_id, dentistry.dentistry_name, dentist.dentist_name, " +
                            "previousappointments.appointment_day, previousappointments.appointment_time, previousappointments.patient," +
                            "previousappointments.diagnosis, previousappointments.admission_price from previousappointments " +
                            "join dentist on previousappointments.dentist_id = dentist.dentist_id " +
                            "join dentistry on dentist.dentistry_id = dentistry.dentistry_id" +
                            " where (diagnosis != 'нужно указать' or admission_price != 0) and dentistry.dentistry_name = '"
                            + clinic_name + "' and dentist.dentist_name = '" + doctor_name + "'";
                    ResultSet rs = stmt.executeQuery(query);
                    //PreviousAppointment(int previous_appointment_id, String dentistry, String dentist, String appointment_day,
                    //        String appointment_time, String patient, String diagnosis, int admission_price)
                    while (rs.next()) {
                        previousAppointment = new Entities.PreviousAppointment(rs.getInt(1), rs.getString(2),
                                rs.getString(3), String.valueOf(rs.getDate(4)), String.valueOf(rs.getTime(5)),
                                rs.getString(6), rs.getString(7), rs.getInt(8));
                        previousAppointmentList.add(previousAppointment);

                    }
                    rs.close();
                    stmt.close();

                } finally {
                    con.close();
                }
                return previousAppointmentList;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public List<Entities.PreviousAppointment> get_unfilled_appointments_with_filter_by_clinic(String clinic_name){

        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                previousAppointmentList.clear();
                try {
                    LocalDate localDate = LocalDate.now();
                    LocalTime localTime = LocalTime.now();
                    Statement stmt = con.createStatement();
                    String query = "select previousappointments.previous_appointment_id, dentistry.dentistry_name, dentist.dentist_name, " +
                            "previousappointments.appointment_day, previousappointments.appointment_time, previousappointments.patient," +
                            "previousappointments.diagnosis, previousappointments.admission_price from previousappointments " +
                            "join dentist on previousappointments.dentist_id = dentist.dentist_id " +
                            "join dentistry on dentist.dentistry_id = dentistry.dentistry_id" +
                            " where (diagnosis = 'нужно указать' or admission_price = 0) and dentistry.dentistry_name = '" + clinic_name +"'";
                    ResultSet rs = stmt.executeQuery(query);
                    //PreviousAppointment(int previous_appointment_id, String dentistry, String dentist, String appointment_day,
                    //        String appointment_time, String patient, String diagnosis, int admission_price)
                    while (rs.next()) {
                        previousAppointment = new Entities.PreviousAppointment(rs.getInt(1), rs.getString(2),
                                rs.getString(3), String.valueOf(rs.getDate(4)), String.valueOf(rs.getTime(5)),
                                rs.getString(6), rs.getString(7), rs.getInt(8));
                        previousAppointmentList.add(previousAppointment);

                    }
                    rs.close();
                    stmt.close();

                } finally {
                    con.close();
                }
                return previousAppointmentList;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public List<Entities.PreviousAppointment> get_one_doctor_unfilled_appointments(String clinic_name, String doctor_name){
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                previousAppointmentList.clear();
                try {
                    LocalDate localDate = LocalDate.now();
                    LocalTime localTime = LocalTime.now();
                    Statement stmt = con.createStatement();
                    String query = "select previousappointments.previous_appointment_id, dentistry.dentistry_name, dentist.dentist_name, " +
                            "previousappointments.appointment_day, previousappointments.appointment_time, previousappointments.patient," +
                            "previousappointments.diagnosis, previousappointments.admission_price from previousappointments " +
                            "join dentist on previousappointments.dentist_id = dentist.dentist_id " +
                            "join dentistry on dentist.dentistry_id = dentistry.dentistry_id" +
                            " where (diagnosis = 'нужно указать' or admission_price = 0) and dentistry.dentistry_name = '"
                            + clinic_name + "' and dentist.dentist_name = '" + doctor_name + "'";
                    ResultSet rs = stmt.executeQuery(query);
                    //PreviousAppointment(int previous_appointment_id, String dentistry, String dentist, String appointment_day,
                    //        String appointment_time, String patient, String diagnosis, int admission_price)
                    while (rs.next()) {
                        previousAppointment = new Entities.PreviousAppointment(rs.getInt(1), rs.getString(2),
                                rs.getString(3), String.valueOf(rs.getDate(4)), String.valueOf(rs.getTime(5)),
                                rs.getString(6), rs.getString(7), rs.getInt(8));
                        previousAppointmentList.add(previousAppointment);

                    }
                    rs.close();
                    stmt.close();

                } finally {
                    con.close();
                }
                return previousAppointmentList;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public void migrate_appointments(){
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                List<Entities.ForthcomingAppointment> appointments_to_migrate = new ArrayList<>();
                Entities.ForthcomingAppointment appointment;
                try {
                    LocalDate localDate = LocalDate.now();
                    LocalTime localTime = LocalTime.now();
                    Statement stmt = con.createStatement();
                    String query = "select appointments.appointment_id, dentistry.dentistry_name, dentist.dentist_name,\n" +
                            "appointments.appointment_day, appointments.appointment_time, appointments.patient from appointments \n" +
                            "join dentist on appointments.dentist_id = dentist.dentist_id\n" +
                            "join dentistry on dentist.dentistry_id = dentistry.dentistry_id\n" +
                            "where appointments.appointment_day < '" + localDate + "' or (appointments.appointment_day = '" +
                            localDate + "' and appointments.appointment_time <= '" + localTime + "')";
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        appointment = new Entities.ForthcomingAppointment(rs.getInt(1), rs.getString(2),
                                rs.getString(3), String.valueOf(rs.getDate(4)),
                                String.valueOf(rs.getTime(5)), rs.getString(6));
                        appointments_to_migrate.add(appointment);

                    }
                    for (Entities.ForthcomingAppointment a: appointments_to_migrate){
                        addAppointment(new Entities.PreviousAppointment(a.getId(),a.getDentistry(), a.getDentist(),
                                a.getAppointment_day(), a.getAppointment_time(), a.getPatient(), "нужно указать", 0));
                        appointmentFunctions.deleteAppointment(a.getId());
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
    }

    public void addAppointment(Entities.PreviousAppointment appointment) {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                String insert = "insert into previousappointments(dentist_id, appointment_day, appointment_time, patient, diagnosis, admission_price) values" +
                        "(?, ?, ?, ?, ?, ?)";
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
                stmt.setString(5, appointment.getDiagnosis());
                stmt.setInt(6, appointment.getAdmission_price());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Entities.PreviousAppointment get_one_appointment(int id){
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                Statement stmt = con.createStatement();
                String query = "select previousappointments.previous_appointment_id, dentistry.dentistry_name, dentist.dentist_name, " +
                        "previousappointments.appointment_day, previousappointments.appointment_time, previousappointments.patient," +
                        "previousappointments.diagnosis, previousappointments.admission_price from previousappointments " +
                        "join dentist on previousappointments.dentist_id = dentist.dentist_id " +
                        "join dentistry on dentist.dentistry_id = dentistry.dentistry_id " +
                        "where previousappointments.previous_appointment_id = " + id;
                ResultSet rs = stmt.executeQuery(query);
                Entities.PreviousAppointment appointment = null;
                while (rs.next()) {
                    appointment = new Entities.PreviousAppointment(rs.getInt(1), rs.getString(2),
                            rs.getString(3), String.valueOf(rs.getDate(4)), String.valueOf(rs.getTime(5)),
                            rs.getString(6), rs.getString(7), rs.getInt(8));
                }
                rs.close();
                stmt.close();
                return appointment;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public void updateAppointment(Entities.PreviousAppointment previousAppointment){
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                String upd = "UPDATE previousappointments SET diagnosis = ?, admission_price = ? " +
                        "WHERE previous_appointment_id = ?";
                PreparedStatement stmt = con.prepareStatement(upd);
                stmt.setString(1, previousAppointment.getDiagnosis());
                stmt.setInt(2, previousAppointment.getAdmission_price());
                stmt.setInt(3, previousAppointment.getPrevious_appointment_id());
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
                String del = "DELETE FROM previousappointments WHERE previous_appointment_id = ?";
                PreparedStatement stmt = con.prepareStatement(del);
                stmt.setInt(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
