package org.art.web.labs.services;

import org.art.web.labs.dao.exceptions.DAOSystemException;
import org.art.web.labs.model.chat.User;
import org.art.web.labs.services.exceptions.ServiceBusinessException;
import org.art.web.labs.services.exceptions.ServiceSystemException;

import java.util.List;

public interface UserService extends Service<User> {

    /**
     * Service method for getting user by login
     *
     * @param login user's login
     * @return user with specified login and password
     * @throws ServiceBusinessException if no user was found in the database
     * @throws ServiceSystemException   if {@link DAOSystemException}
     *                                  was thrown during the user reading from the database
     */
    User getUserByLogin(String login) throws ServiceBusinessException, ServiceSystemException;

    /**
     * Service method for getting all users from the database
     *
     * @return the list of all users
     * @throws ServiceBusinessException if no users were found in the database
     * @throws ServiceSystemException   if {@link DAOSystemException}
     *                                  was thrown during the users reading from the database
     */
    List<User> getAllUsers() throws ServiceBusinessException, ServiceSystemException;
}
