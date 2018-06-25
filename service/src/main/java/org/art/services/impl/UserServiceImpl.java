package org.art.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.dao.UserDao;
import org.art.dao.exceptions.DAOSystemException;
import org.art.entities.User;
import org.art.services.UserService;
import org.art.services.exceptions.ServiceBusinessException;
import org.art.services.exceptions.ServiceSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);

    private UserDao userDao;

    public UserServiceImpl() {
    }

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User save(User user) throws ServiceSystemException {
        User savedUser;
        try {
            savedUser = userDao.save(user);
        } catch (DAOSystemException e) {
            LOG.error("Exception while saving user into the database!", e);
            throw new ServiceSystemException("Exception while saving user into the database!", e);
        }
        return savedUser;
    }

    @Override
    public User get(Long id) throws ServiceSystemException, ServiceBusinessException {
        User user;
        try {
            user = userDao.get(id);
            if (user == null) {
                throw new ServiceBusinessException("No user was found!");
            }
        } catch (DAOSystemException e) {
            LOG.error("Cannot get user from the database! ID: " + id, e);
            throw new ServiceSystemException("Cannot get user from the database! ID: " + id, e);
        }
        return user;
    }

    @Override
    public User update(User user) throws ServiceSystemException, ServiceBusinessException {
        User modifUser;
        try {
            modifUser = userDao.update(user);
            if (modifUser == null) {
                throw new ServiceBusinessException("Cannot find user with such ID");
            }
        } catch (DAOSystemException e) {
            LOG.error("Cannot update user in the database!", e);
            throw new ServiceSystemException("Cannot update user in the database!", e);
        }
        return modifUser;
    }

    @Override
    public void delete(Long id) throws ServiceBusinessException, ServiceSystemException {
        try {
            userDao.delete(id);
        } catch (DAOSystemException e) {
            LOG.error("Can't delete user from the database!", e);
            throw new ServiceSystemException("Can't delete user from the database!", e);
        }
    }

    @Override
    public User getUserByLogin(String login) throws ServiceBusinessException, ServiceSystemException {
        User user;
        try {
            user = userDao.getUserByLogin(login);
            if (user == null) {
                throw new ServiceBusinessException("No user was found!");
            }
        } catch (DAOSystemException e) {
            LOG.error("Cannot get user from the database!", e);
            throw new ServiceSystemException("Cannot get user from the database!", e);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() throws ServiceBusinessException, ServiceSystemException {
        List<User> usersList;
        try {
            usersList = userDao.getAllUsers();
            if (usersList.size() == 0) {
                throw new ServiceBusinessException("No users were found!");
            }
        } catch (DAOSystemException e) {
            LOG.error("Cannot get users from the database!", e);
            throw new ServiceSystemException("Cannot get users from the database!", e);
        }
        return usersList;
    }
}
