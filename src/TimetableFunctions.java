import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimetableFunctions extends BaseFunctions{
    private final List<Entities.TimeTable> timeTableList = new ArrayList<>();
    private Entities.TimeTable timeTable;
    public List<Entities.TimeTable> get_timetable(){
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                timeTableList.clear();
                try {
                    Statement stmt = con.createStatement();
                    String query = """
                            select timetable.tt_id, dentist.dentist_name, dentistry.dentistry_name,
                            week.day_name, timetable.admission_time from timetable\s
                            join dentist on dentist.dentist_id = timetable.dentist_id\s
                            join dentistry on dentistry.dentistry_id = dentist.dentistry_id\s
                            join week on week.day_id = timetable.day_id\s
                            order by dentistry.dentistry_name, dentist.dentist_name, week.day_id""" ;
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        timeTable = new Entities.TimeTable(rs.getInt(1), rs.getString(2), rs.getString(3),
                                rs.getString(4), rs.getString(5));
                        timeTableList.add(timeTable);
                    }
                    rs.close();
                    stmt.close();

                } finally {
                    con.close();
                }
                return timeTableList;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public List<Entities.TimeTable> filter_by_clinic(String clinic_name) {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                timeTableList.clear();
                try {
                    Statement stmt = con.createStatement();
                    String query = """
                            select timetable.tt_id, dentist.dentist_name, dentistry.dentistry_name,
                            week.day_name, timetable.admission_time from timetable\s
                            join dentist on dentist.dentist_id = timetable.dentist_id\s
                            join dentistry on dentistry.dentistry_id = dentist.dentistry_id\s
                            join week on week.day_id = timetable.day_id\s
                            where dentistry.dentistry_name = '""" + clinic_name + "' order by dentist.dentist_name, week.day_id";
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        timeTable = new Entities.TimeTable(rs.getInt(1), rs.getString(2), rs.getString(3),
                                rs.getString(4), rs.getString(5));
                        timeTableList.add(timeTable);
                    }
                    rs.close();
                    stmt.close();

                } finally {
                    con.close();
                }
                return timeTableList;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
