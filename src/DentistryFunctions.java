import java.io.File;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DentistryFunctions extends BaseFunctions {
    private final List<Entities.Dentistry> dentistryList = new ArrayList<>();
    private Entities.Dentistry dentistry;
    private int number_of_report = 1;

    public List<Entities.Dentistry> get_all_dentistry() {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                dentistryList.clear();
                try {
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("select * from dentistry");
                    while (rs.next()) {
                        dentistry = new Entities.Dentistry(rs.getInt(1), rs.getString(2),
                                rs.getString(4), rs.getString(5), rs.getString(3),
                                rs.getInt(6), rs.getInt(7));
                        dentistryList.add(dentistry);
                    }
                    rs.close();
                    stmt.close();

                } finally {
                    con.close();
                }
                return dentistryList;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public void addDentistry(Entities.Dentistry dentistry) {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                String insert = "INSERT INTO dentistry (dentistry_name, address, phone, head_of_clinic," +
                        " foundation_year, customer_count)" +
                        " VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(insert);
                stmt.setString(1, dentistry.getName());
                stmt.setString(2, dentistry.getAddress());
                stmt.setString(3, dentistry.getPhone());
                stmt.setString(4, dentistry.getHead_of_clinic());
                stmt.setInt(5, dentistry.getFoundation_year());
                stmt.setInt(6, dentistry.getCustomer_count());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateDentistry(Entities.Dentistry dentistry) {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                String upd = "UPDATE DENTISTRY SET dentistry_name = ?, address = ? , phone = ?, head_of_clinic = ?," +
                        " foundation_year = ?, customer_count = ? WHERE dentistry_id = ?";
                PreparedStatement stmt = con.prepareStatement(upd);
                stmt.setString(1, dentistry.getName());
                stmt.setString(2, dentistry.getAddress());
                stmt.setString(3, dentistry.getPhone());
                stmt.setString(4, dentistry.getHead_of_clinic());
                stmt.setInt(5, dentistry.getFoundation_year());
                stmt.setInt(6, dentistry.getCustomer_count());
                stmt.setInt(7, dentistry.getDentistry_id());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Entities.Dentistry get_one_dentistry(int id) {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM DENTISTRY WHERE dentistry_id = " + id);
                Entities.Dentistry dentistry1 = null;
                while (rs.next()) {
                    dentistry1 = new Entities.Dentistry(rs.getInt(1), rs.getString(2),
                            rs.getString(4), rs.getString(5),
                            rs.getString(3), rs.getInt(6), rs.getInt(7));
                }
                rs.close();
                stmt.close();
                return dentistry1;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public void deleteDentistry(int id) {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                String del = "DELETE FROM DENTISTRY WHERE dentistry_id = ?";
                PreparedStatement stmt = con.prepareStatement(del);
                stmt.setInt(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Entities.Dentistry> sortDentistry(String param, String order) {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                dentistryList.clear();
                try {
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("select * from dentistry order by " + param + " " + order);
                    while (rs.next()) {
                        dentistry = new Entities.Dentistry(rs.getInt(1), rs.getString(2),
                                rs.getString(4), rs.getString(5), rs.getString(3),
                                rs.getInt(6), rs.getInt(7));
                        dentistryList.add(dentistry);
                    }
                    rs.close();
                    stmt.close();

                } finally {
                    con.close();
                }
                return dentistryList;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public List<Entities.Dentistry> searchDentistry(String param, String searchText) {
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                dentistryList.clear();
                try {
                    Statement stmt = con.createStatement();
                    String query = "select * from dentistry where " + param + " like '%" + searchText + "%'";
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        dentistry = new Entities.Dentistry(rs.getInt(1), rs.getString(2),
                                rs.getString(4), rs.getString(5), rs.getString(3),
                                rs.getInt(6), rs.getInt(7));
                        dentistryList.add(dentistry);
                    }
                    rs.close();
                    stmt.close();

                } finally {
                    con.close();
                }
                return dentistryList;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public List<String> get_types_in_one_clinic(int clinic_id){
        String[] s = connect_to_db();
        if (s != null) {
            try (Connection con = DriverManager.getConnection(s[0],
                    s[1], s[2])) {
                List<String> types = new ArrayList<>();
                try {
                    Statement stmt = con.createStatement();
                    String query = "select distinct dentist_type.type_name from dentist join dentist_type\n" +
                            "on dentist.dentist_type_id = dentist_type.type_id where dentist.dentistry_id = " + clinic_id;
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        types.add(rs.getString(1));
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
    public String createReport(List<Entities.Dentistry> dentistries){
        String[] headers = {"ID", "Название", "Адрес", "Телефон", "Заведующий клиникой",
                "Год основания", "Количество клиентов"};
        String name_of_report = "-";
        try{
            name_of_report = "Клиники №" + number_of_report + ".csv";
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
            for (Entities.Dentistry d: dentistries){
                text.append(d.getDentistry_id()).append(", ");
                text.append(d.getName()).append(", ");
                text.append(d.getAddress()).append(", ");
                text.append(d.getPhone()).append(", ");
                text.append(d.getHead_of_clinic()).append(", ");
                text.append(d.getFoundation_year()).append(", ");
                text.append(d.getCustomer_count()).append("\n");
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
    // String[] s = connect_to_db();
}
