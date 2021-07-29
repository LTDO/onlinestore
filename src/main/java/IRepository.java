import java.sql.SQLException;
import java.util.Map;

public interface IRepository {
    String storeName(String name, String language) throws SQLException;

    Integer addedName(String name) throws SQLException;

    int counter() throws SQLException;

    Integer counterName(String name) throws SQLException;

    Map<String, Integer> userList() throws SQLException;

    String clear() throws SQLException;

    String clearName(String name) throws SQLException;

    void exit() throws SQLException;
}
