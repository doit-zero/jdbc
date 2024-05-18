package hello.jdbc.connection;

// 상수로 쓰기위해 추상 클래스로 만듦 객체가 아님
public abstract class ConnectionConst {
    public static final String URL="jdbc:h2:tcp://localhost/~/test";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";
}
