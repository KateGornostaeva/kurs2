package entity;

import java.sql.*;

public class Quote {
    private Integer id;
    private String quote;
    private String teacher;
    private String subject;
    private Date date;
    private Integer id_user;

    public Quote() {

    }

    public Quote(ResultSet resultSet) throws SQLException {
        id = resultSet.getInt("id");
        quote = resultSet.getString("quote");
        teacher = resultSet.getString("teacher");
        subject = resultSet.getString("subject");
        date = resultSet.getDate("date");
        id_user = resultSet.getInt("id_user");
    }

    public void save(Connection connection) throws SQLException {//сохраняет в БД
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO quote_teacher(quote, teacher, subject, date, id_user) values(?,?,?,?,?)");
        preparedStatement.setString(1, quote);
        preparedStatement.setString(2, teacher);
        preparedStatement.setString(3, subject);
        preparedStatement.setDate(4, date);
        preparedStatement.setInt(5, id_user);
        preparedStatement.executeUpdate();
    }

    public void update(Connection connection) throws SQLException {//изменяет в БД
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE quote_teacher SET quote = ?, teacher = ?, subject = ?, date = ?, id_user = ? WHERE id = ?");
        preparedStatement.setString(1, quote);
        preparedStatement.setString(2, teacher);
        preparedStatement.setString(3, subject);
        preparedStatement.setDate(4, date);
        preparedStatement.setInt(5, id_user);
        preparedStatement.setInt(6, id); //если не указать id, то обновится вся таблица, а не одна строчка
        preparedStatement.executeUpdate();
    }

    public void delete(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM quote_teacher  WHERE id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //для получения значения поля класса и его изменения
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }
}

