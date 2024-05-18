package hello.jdbc.connection;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;

public class DBConnectionUtilTest {
    @Test
    void connection() {
        // save
        Connection connection = DBConnectionUtil.getConnection();
        assertThat(connection).isNotNull();
    }
}
