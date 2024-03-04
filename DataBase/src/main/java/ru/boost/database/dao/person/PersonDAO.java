package ru.boost.database.dao.person;

import java.sql.*;
import java.util.List;

public interface PersonDAO {

    static PersonDAO getInstance(){
        return PersonDAOImpl.getInstance();
    }

    void insert(PersonPOJO person) throws SQLException;
    List<PersonPOJO> getAll() throws SQLException;
    void update(PersonPOJO person) throws SQLException;
    void delete(long id) throws SQLException;
}
