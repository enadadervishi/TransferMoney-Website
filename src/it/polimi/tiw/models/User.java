package it.polimi.tiw.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User implements Model {

    private long id;
    private String username;
    private String password;
    private String email;

    public User() {
    }

    public User(ResultSet resultSet) throws SQLException {
        id = resultSet.getLong("id");
        username = resultSet.getString("username");
        password = resultSet.getString("password");
        email = resultSet.getString("email");
    }

    @Override
    public String[] getFieldDescriptors() {
    	//type: INTEGER
    	//flags: 
    	//AUTO_INCREMENT it automatically gives number from 1 to ...
    	//PRIMARY KEY the way to identify rows, if delete one row "2" any other row will be "2"
        return new String[] {"id INTEGER AUTO_INCREMENT PRIMARY KEY", "username VARCHAR(255) NOT NULL UNIQUE",
                "password VARCHAR(255) NOT NULL", "email VARCHAR(255) NOT NULL"};
    }

    @Override
    public String[] getFieldNames() {
        return new String[]{"username", "password", "email"};
    }

    @Override
    public Object[] getFieldValues() {
        return new Object[]{username, password, email};
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
