package chat.server;

import java.io.Serializable;
import java.net.Socket;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String login;
    private String password;
    private transient Socket socket;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, Socket socket) {
        this.login = login;
        this.password = password;
        this.socket = socket;
    }

    public User(User user) {
        this.login = user.getLogin();
        this.password = user.getLogin();
        this.socket = user.socket;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return login.equals(user.login) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
