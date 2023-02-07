import java.sql.*;
import java.util.HashMap;

public class PGManager {
    Connection connection = null;

    public void hashMapToPG(HashMap<String, String> personInfo) throws SQLException {
        DatabaseMetaData dbmd = connection.getMetaData();
        try (ResultSet tables = dbmd.getTables(null, null, "%", new String[]{"TABLE"})) {
            while (tables.next()) {
                System.out.println(tables.getString("TABLE_NAME"));
            }
        }
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO person_info.person_info " +
                "(REFERENCE_NO, CREATION_DATE, CREATED_BY, DATE_OF_ENTRY, MACHINE_ID, NAME_ENG, FATHER_NAME_ENG, " +
                "MOTHER_NAME_ENG, DATE_OF_BIRTH, PLACE_OF_BIRTH, GENDER, RELIGION, NATIONALITY, AGE, " +
                "COUNTRY, ADDRESS, VILLAGE, POLICE_STATION, DISTRICT, LI, LM, LR, LL, LT, RI, RM, RR, RL, RT)" +
                " VALUES (" +
                " '" + personInfo.get("REFERENCE_NO") + "', " +
                " '" + personInfo.get("CREATION_DATE") + "', " +
                " '" + personInfo.get("CREATED_BY") + "', " +
                " '" + personInfo.get("DATE_OF_ENTRY") + "', " +
                " '" + personInfo.get("MACHINE_ID") + "', " +
                " '" + personInfo.get("NAME_ENG ") + "', " +
                " '" + personInfo.get("FATHER_NAME_ENG") + "', " +
                " '" + personInfo.get("MOTHER_NAME_ENG") + "', " +
                " '" + personInfo.get("DATE_OF_BIRTH") + "', " +
                " '" + personInfo.get("PLACE_OF_BIRTH ") + "', " +
                " '" + personInfo.get("GENDER") + "', " +
                " '" + personInfo.get("RELIGION ") + "', " +
                " '" + personInfo.get("NATIONALITY ") + "', " +
                " " + personInfo.get("AGE") + ", " +
                " '" + personInfo.get("COUNTRY") + "', " +
                " '" + personInfo.get("ADDRESS") + "', " +
                " '" + personInfo.get("VILLAGE") + "', " +
                " '" + personInfo.get("POLICE_STATION ") + "', " +
                " '" + personInfo.get("DISTRICT ") + "', " +
                " '"+personInfo.get("LI")       + "', "  +
                " '"+personInfo.get("LM")       + "', "  +
                " '"+personInfo.get("LR")       + "', "  +
                " '"+personInfo.get("LL")       + "', "  +
                " '"+personInfo.get("LT")       + "', "  +
                " '"+personInfo.get("RI")       + "', "  +
                " '"+personInfo.get("RM")       + "', "  +
                " '"+personInfo.get("RR")       + "', "  +
                " '"+personInfo.get("RL")       + "', "  +
                " '"+personInfo.get("RT")       + "' "  +
                ");";
        sql = sql.replace("'null'", "null");
        statement.executeUpdate(sql);
//        connection.commit();
        statement.close();
    }

    public PGManager() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/rohinga",
                            "postgres", "mysecretpassword");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");

    }
}

