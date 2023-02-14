import java.io.File;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TimetableFunctions extends BaseFunctions{
    private final List<Entities.TimeTable> timeTableList = new ArrayList<>();
    private Entities.TimeTable timeTable;
    private int number_of_report = 1;
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
    public HashMap<Integer, String> get_week(){
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                HashMap<Integer, String> weekHashMap = new HashMap<>();
                try {
                    Statement stmt = con.createStatement();
                    String query = "select * from week";
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        weekHashMap.put(rs.getInt(1), rs.getString(2));
                    }
                    rs.close();
                    stmt.close();

                } finally {
                    con.close();
                }
                return weekHashMap;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public void addEntry(Entities.TimeTable entry) {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                String insert = "INSERT INTO timetable(dentist_id, day_id, admission_time)" +
                        " VALUES (?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(insert);
                DentistFunctions dentistFunctions = new DentistFunctions();
                HashMap<Integer, String> week = get_week();
                List<Entities.Dentist> DocList = dentistFunctions.get_dentists();
                for (Entities.Dentist doc: DocList){
                    if (entry.getDentist().equals(doc.getDentist_name())){
                        stmt.setInt(1, doc.getDentist_id());
                        break;
                    }
                }
                for (Integer day: week.keySet()){
                    if (entry.getDay().equals(week.get(day))){
                        stmt.setInt(2, day);
                        break;
                    }
                }
                stmt.setString(3, entry.getAdmission_time());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public String createReport(List<Entities.TimeTable> entries){
        String[] headers = {"ID", "Врач", "Клиника", "День", "Время"};
        String name_of_report = "-";
        try{
            name_of_report = "Расписание №" + number_of_report + ".csv";
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
            for (Entities.TimeTable t: entries){
                text.append(t.getTt_id()).append(", ");
                text.append(t.getDentist()).append(", ");
                text.append(t.getDentistry()).append(", ");
                text.append(t.getDay()).append(", ");
                text.append(t.getAdmission_time()).append("\n");
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
