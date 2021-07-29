import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseTest {

    final String db_url = "jdbc:h2:file:./target/my_db";
    final String username = "sa";
    final String password = "";
    DatabaseBuilder builder = new DatabaseBuilder();

    public Connection getConn() throws SQLException {
        return DriverManager.getConnection(db_url, username, password);
    }

    @BeforeEach
    public void eraseDB() {
        builder.clear();
    }

    @Test
    public void testGreetingDB() {
        DatabaseBuilder builder = new DatabaseBuilder();
        builder.clear();
        builder.storeName("Lunga", "Tshila");
        builder.storeName("Aya", "Nopo");
        assertEquals(builder.userList().size(), 2);
    }

    @Test
    public void testCounterForNameDB(){
        DatabaseBuilder builder = new DatabaseBuilder();
        builder.clear();
        builder.storeName("Lunga", "Tshila");
        assertEquals(builder.counterName("Lunga"), 1);
    }

    @Test
    public void testCounterDB(){
        DatabaseBuilder builder = new DatabaseBuilder();
        builder.clear();
        builder.storeName("Ovayo", "Tshila");
        builder.storeName("Thembela", "Kwaza");
        assertEquals(builder.counter(), 2);
    }

    @Test
    public void testGreetedNameDB(){
        DatabaseBuilder builder = new DatabaseBuilder();
        builder.clear();
        builder.storeName("Ntando", "Nopo");
        assertEquals(builder.addedName("Ntando"), 1);
    }
    @Test
    public void testClearNameDB(){
        DatabaseBuilder builder = new DatabaseBuilder();
        builder.clear();
        builder.storeName("Nelly", "Kwaza");
        assertEquals(builder.clearName("Nelly"), "Nelly cleared from system");
    }

    @Test
    public void testClearAllUsersInDB(){
        DatabaseBuilder builder = new DatabaseBuilder();
        builder.clear();
        builder.storeName("thabang", "mataire");
        builder.clear();
        assertEquals(builder.counter(), 0);
    }
}
