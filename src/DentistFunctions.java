import java.io.File;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DentistFunctions extends BaseFunctions{
    private final List<Entities.Dentist> dentistList = new ArrayList<>();
    private Entities.Dentist dentist;
    private DentistryFunctions dentistryFunctions = new DentistryFunctions();
    private int number_of_report = 1;
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
                List<Entities.Dentistry> list = df.get_all_dentistry();
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
    public void updateDentist(Entities.Dentist dentist){
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                String upd = "UPDATE DENTIST SET dentist_name = ?, dentistry_id = ? , experience = ?, dentist_type_id = ? " +
                        "WHERE dentist_id = ?";
                PreparedStatement stmt = con.prepareStatement(upd);
                stmt.setString(1,dentist.getDentist_name() );
                List<Entities.Dentistry> dentistries = dentistryFunctions.get_all_dentistry();
                HashMap<String, Integer> types = get_types();
                for (Entities.Dentistry dentistry: dentistries){
                    if (dentistry.getName().equals(dentist.getDentistry())){
                        stmt.setInt(2, dentistry.getDentistry_id());
                        break;
                    }
                }
                stmt.setInt(3, dentist.getExperience());
                for (String type: types.keySet()){
                    if (dentist.getDentist_type().equals(type)){
                        stmt.setInt(4, types.get(type));
                    }
                }
                stmt.setInt(5, dentist.getDentist_id());
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
    public Entities.Dentist get_one_dentist(int id) {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                Statement stmt = con.createStatement();
                String query = """
                        select dentist.dentist_id, dentist.dentist_name, dentistry.dentistry_name,
                        dentist.experience, dentist_type.type_name from dentist\s
                        join dentistry on dentistry.dentistry_id = dentist.dentistry_id\s
                        join dentist_type on dentist_type.type_id = dentist.dentist_type_id
                        where dentist.dentist_id=""" + id;
                ResultSet rs = stmt.executeQuery(query);
                Entities.Dentist dentist1 = null;
                while (rs.next()) {
                    dentist1 = new Entities.Dentist(rs.getInt(1), rs.getString(2),rs.getString(3),
                           rs.getInt(4), rs.getString(5));
                }
                rs.close();
                stmt.close();
                return dentist1;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public String createReport(List<Entities.Dentist> dentists){
        String[] headers = {"ID", "ФИО", "Клиника", "Стаж", "Специализация"};
        String name_of_report = "-";
        try{
            name_of_report = "Врачи №" + number_of_report + ".csv";
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
            for (Entities.Dentist d: dentists){
                text.append(d.getDentist_id()).append(", ");
                text.append(d.getDentist_name()).append(", ");
                text.append(d.getDentistry()).append(", ");
                text.append(d.getExperience()).append(", ");
                text.append(d.getDentist_type()).append("\n");
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
