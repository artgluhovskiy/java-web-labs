package org.art.web.labs.dao;

import org.art.web.labs.dao.exceptions.DAOSystemException;
import org.art.web.labs.model.chat.User;

import java.util.List;

public interface UserDao extends DAO<User> {

    /**
     * Getting user by login
     *
     * @param login user's login
     * @return user with specified login and password
     * @throws DAOSystemException if {@link java.sql.SQLException}
     *                            was thrown during the user reading from the database
     */
    User getUserByLogin(String login) throws DAOSystemException;

    /**
     * Getting all users from the database
     *
     * @return the list of all users
     * @throws DAOSystemException if {@link java.sql.SQLException}
     *                            was thrown during the users reading from the database
     */
    List<User> getAllUsers() throws DAOSystemException;
}
