import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DatabaseBuilder implements IRepository {

    DataLogic dataLogic = new DataLogic();

    final String INSERT_  =                   "INSERT INTO userTable(UserName, counter) VALUES(?, ?)";
    final String SELECT_USER =                "SELECT * FROM userTable WHERE UserName = ? ";
    final String RETRIEVE_ALL =               "SELECT * FROM userTable";
    final String RETRIEVE_SINGLE_USER =       "SELECT counter FROM userTable WHERE UserName = ? ";
    final String UPDATE_ =                    "UPDATE userTable SET counter = counter + 1 WHERE UserName = ? ";
    final String DELETE_A_USER =              "DELETE FROM userTable WHERE UserName  = ? ";
    final String DELETE_ =                    "DELETE FROM userTable";
    final String RETRIEVE_ALL_ROWS =          "SELECT COUNT(*) AS counter FROM userTable";

    Connection co;

    PreparedStatement insert_;
    PreparedStatement select_user;
    PreparedStatement retrieve_all;
    PreparedStatement retrieve_single_user;
    PreparedStatement update_;
    PreparedStatement delete_a_user;
    PreparedStatement delete_;
    PreparedStatement retrieve_all_rows;


    public DatabaseBuilder(){

        try {
            Class.forName("org.h2.Driver");

            final String db_url = "jdbc:h2:./target/my_db";
            String username = "sa";
            String password = "";
            co = DriverManager.getConnection(db_url, username, password);

            insert_ = co.prepareStatement(INSERT_);
            select_user = co.prepareStatement(SELECT_USER);
            retrieve_all = co.prepareCall(RETRIEVE_ALL);
            retrieve_single_user = co.prepareStatement(RETRIEVE_SINGLE_USER);
            update_ = co.prepareStatement(UPDATE_);
            delete_a_user = co.prepareStatement(DELETE_A_USER);
            delete_ = co.prepareStatement(DELETE_);
            retrieve_all_rows = co.prepareStatement(RETRIEVE_ALL_ROWS);

        }  catch (SQLException e) {
            System.out.println("Failed to connect to the db: " + e);
        }
        catch (ClassNotFoundException e) {
            System.out.println("SOME UNEXPECTED ERROR OCCURRED");
        }
    }
    @Override
    public String storeName(String name, String language) {
        try{
            select_user.setString(1, name);
            ResultSet out = select_user.executeQuery();
            if (!out.next()){
                insert_.setString(1, name);
                insert_.setInt(2, 1);
                insert_.execute();
            }else {
                update_.setString(1, name);
                update_.executeUpdate();
            }
        }catch (SQLException e) {
            System.out.println("ERROR" + e);
        }
        return dataLogic.storeName(name, language);
    }

    @Override
    public Map<String, Integer> userList() {
        Map<String, Integer> dbData = new HashMap<>();
        try
        {
            ResultSet out = retrieve_all.executeQuery();
            while (out.next())
            {
                dbData.put(out.getString("Username"), out.getInt("counter"));
            }
        }catch (SQLException e){
            System.out.println("ERROR " + e);
        }
        return dbData;
    }

    @Override
    public Integer addedName(String name) {
        return userList().get(name);
    }

    @Override
    public int counter() {
        return userList().size();
    }

    @Override
    public Integer counterName(String name) {
        return userList().get(name);
    }

    @Override
    public String clear() {
        try {
            delete_.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "All users cleared from system";
    }
    @Override
    public String clearName(String name) {

        try {
            select_user.setString(1, name);
            delete_a_user.setString(1, name);
            ResultSet out = select_user.executeQuery();
            if (out.next()){
                delete_a_user.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return name + " cleared from system";
    }
    @Override
    public void exit() throws SQLException {
        co.close();
        System.exit(1);
    }
}
