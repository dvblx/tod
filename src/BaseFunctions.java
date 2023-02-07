public class BaseFunctions {
    public static String[] connect_to_db() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/dentistrydb";
            String login = "postgres";
            String password = "postgres";
            return new String[]{url, login, password};
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
