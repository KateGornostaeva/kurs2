package entity;

import java.sql.*;

public class Quote {
    private Integer id;
    private String quote;
    private String teacher;
    private String subject;
    private Date data;
    private Integer id_user;

    public Quote() {

    }

    public Quote(ResultSet resultSet) throws SQLException {
        id = resultSet.getInt("id");
        quote = resultSet.getString("quote");
        teacher = resultSet.getString("teacher");
        subject = resultSet.getString("subject");
        data = resultSet.getDate("data");
        id_user = resultSet.getInt("id_user");
    }

    public void save(Connection connection) throws SQLException {//сохраняет в БД
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO quote_teacher(quote, teacher, subject, data, id_user) values(?,?,?,?,?)");
        preparedStatement.setString(1, quote);
        preparedStatement.setString(2, teacher);
        preparedStatement.setString(3, subject);
        preparedStatement.setDate(4, data);
        preparedStatement.setInt(5, id_user);
        preparedStatement.executeUpdate();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }
}

