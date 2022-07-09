package entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private Integer id;
    private String login;
    private String hash_pass;
    private Integer groupe;
    private String role;
    private String function;

    public User() {

    }

    public User(ResultSet resultSet) throws SQLException {
        id = resultSet.getInt("id");
        login = resultSet.getString("login");
        hash_pass = resultSet.getString("hash_pass");
        groupe = resultSet.getInt("groupe");
        role = resultSet.getString("role");
        function = resultSet.getString("function");
    }

    public void save(Connection connection) throws SQLException {//сохраняет в БД пользователей
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO users(login,hash_pass, groupe, role, function) values(?,?,?,?,?)");
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, hash_pass);
        preparedStatement.setInt(3, groupe);
        preparedStatement.setString(4, role);
        preparedStatement.setString(5, function);
        preparedStatement.executeUpdate();
    }

    //для получения значения поля класса и его изменения
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHash_pass() {
        return hash_pass;
    }

    public void setHash_pass(String hash_pass) {
        this.hash_pass = hash_pass;
    }

    public Integer getGroupe() {
        return groupe;
    }

    public void setGroupe(Integer groupe) {
        this.groupe = groupe;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}
