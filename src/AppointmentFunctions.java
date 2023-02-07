import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentFunctions extends BaseFunctions {
    private final List<Entities.Appointments> appointmentsList = new ArrayList<>();
    private Entities.Appointments appointment;

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
}
