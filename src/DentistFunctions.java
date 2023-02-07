import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DentistFunctions extends BaseFunctions{
    private final List<Entities.Dentist> dentistList = new ArrayList<>();
    private Entities.Dentist dentist;
    //private static final String[] week = new String[]{"Понедельник", "Вторник", "Среда", "Четверг","Пятница", "Суббота", "Воскресенье"};


    public List<Entities.Dentist> get_dentists() {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                dentistList.clear();
                try {
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("""
                                    select dentist.dentist_id, dentist.dentist_name, dentistry.dentistry_name,
                                    dentist.experience, dentist_type.type_name from dentist\s
                                    join dentistry on dentistry.dentistry_id = dentist.dentistry_id\s
                                    join dentist_type on dentist_type.type_id = dentist.dentist_type_id""");
                    while (rs.next()) {
                        dentist = new Entities.Dentist(rs.getInt(1), rs.getString(2), rs.getString(3),
                                rs.getInt(4), rs.getString(5));
                        dentistList.add(dentist);
                    }
                    rs.close();
                    stmt.close();

                } finally {
                    con.close();
                }
                return dentistList;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public List<Entities.Dentist> filter_by_clinic(String clinic_name) {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                dentistList.clear();
                try {
                    Statement stmt = con.createStatement();
                    String query = """
                            select dentist.dentist_id, dentist.dentist_name, dentistry.dentistry_name,
                            dentist.experience, dentist_type.type_name from dentist\s
                            join dentistry on dentistry.dentistry_id = dentist.dentistry_id\s
                            join dentist_type on dentist_type.type_id = dentist.dentist_type_id
                            where dentistry.dentistry_name = '""" + clinic_name + "'";
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        dentist = new Entities.Dentist(rs.getInt(1), rs.getString(2), rs.getString(3),
                                rs.getInt(4), rs.getString(5));
                        dentistList.add(dentist);
                    }
                    rs.close();
                    stmt.close();

                } finally {
                    con.close();
                }
                return dentistList;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public void addDentist(Entities.Dentist dentist) {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                String insert = "INSERT INTO dentist(dentist_name, dentistry_id, experience, dentist_type_id)" +
                        " VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(insert);
                stmt.setString(1, dentist.getDentist_name());
                DentistryFunctions df = new DentistryFunctions();
                List<Entities.Dentistry> list = df.get_dentistry();
                for (Entities.Dentistry d: list){
                    if (d.getName().equals(dentist.getDentistry())){
                        stmt.setInt(2, d.getDentistry_id());
                        break;
                    }
                }
                stmt.setInt(3, dentist.getExperience());
                HashMap<String, Integer> hashMap = get_types();
                stmt.setInt(4, hashMap.get(dentist.getDentist_type()));
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public HashMap<String, Integer> get_types(){
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                HashMap<String, Integer> types = new HashMap<>();
                try {
                    Statement stmt = con.createStatement();
                    String query = "select * from dentist_type";
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        if (!types.containsKey(rs.getString(2))){
                            types.put(rs.getString(2), rs.getInt(1));
                        }
                    }
                    rs.close();
                    stmt.close();

                } finally {
                    con.close();
                }
                return types;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}