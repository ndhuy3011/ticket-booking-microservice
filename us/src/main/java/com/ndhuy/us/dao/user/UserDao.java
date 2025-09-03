package com.ndhuy.us.dao.user;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.ndhuy.us.dao.UserDao;
import com.ndhuy.us.entity.User;
import com.ndhuy.us.valueobject.Username;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;

@Service
@Transactional
class IUserDao implements UserDao {

    @Resource
    private UserRepository userRepository;

    /*
     * Check if user is null
     * 
     * @ndhuy3011
     * Parameter: user - user to check
     * Return: void
     */
    public void checkNull(User user) {
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("User is null");
        }
        if (Objects.isNull(user.getUsername())) {
            throw new IllegalArgumentException("Username is null");
        }

        if (Objects.isNull(user.getPassword())) {
            throw new IllegalArgumentException("Password is null");
        }
    }

    /*
     * Validate before insert
     * 
     * @ndhuy3011
     * Parameter: user - user to validate
     * Return: void
     */
    public void beforeInsert(User user) {
        checkNull(user);

        if (Objects.nonNull(findByUsername(user.getUsername()))) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }
    }

    /*
     * Insert user
     * 
     * @ndhuy3011
     * Parameter: user - user to insert
     * Return: inserted user
     */
    @Override
    public User insert(User user) {
        beforeInsert(user);
    
        return userRepository.save(user);
    }

    /*
     * Validate before update
     * 
     * @ndhuy3011
     * Parameter: user - user to validate
     * Return: void
     */
    public void beforeUpdate(User user) {
        checkNull(user);

        if (Objects.isNull(findByUsername(user.getUsername()))) {
            throw new IllegalArgumentException("Username not found: " + user.getUsername());
        }
        if (user.getVersion() == null) {
            throw new IllegalArgumentException("Version is null");
        }
    }

    /*
     * Compare two users
     * 
     * @ndhuy3011
     * Parameter: oldUser - old user data
     * Parameter: newUser - new user data
     * 
     */
    public void compareUsers(User oldUser, User newUser) {
        if (!oldUser.getVersion().equals(newUser.getVersion())) {
            throw new IllegalArgumentException("Version is not the same");
        }

    }

    /*
     * Update user
     * 
     * @ndhuy3011
     * Parameter: userNew - new user data
     * Return: updated user
     */
    @Override
    public User update(User userNew) {
        beforeUpdate(userNew);
        var userOld = lockByUsername(userNew.getUsername());
        compareUsers(userOld, userNew);
        changes(userOld, userNew);
        return userRepository.saveAndFlush(userNew);
    }

    private void changes(User userOld, User userNew) {
        int chang = 0;
        if (!userOld.getPwd().matches(userNew.getPassword())) {
            userOld.updatePassword(userNew.getPassword());
            
            chang++;
        }

        if (chang > 0) {
          throw new IllegalArgumentException("Nothing to change");
        }

    }

    /*
     * Find by username with option to throw exception if not found
     * 
     * @ndhuy3011
     * Parameter: username - username to find
     * Parameter: exception - if true, throw exception if not found
     */
    @Override
    public User findByUsername(String username, boolean exception) {
        if (exception) {
            return userRepository.findById(Username.of(username))
                    .orElseThrow(() -> new IllegalArgumentException("Username not found: " + username));

        }
        return userRepository.findById(Username.of(username)).orElse(null);
    }

    /*
     * Find by username without exception
     * 
     * @ndhuy3011
     */
    @Override
    public User findByUsername(String username) {
        return findByUsername(username, false);
    }

    /*
     * Lock user by username
     * 
     * @ndhuy3011
     * Parameter: username - username to lock
     * Return: locked user
     */
    @Override
    public User lockByUsername(String username) {
        return userRepository.findByUsername(Username.of(username))
                .orElseThrow(() -> new IllegalArgumentException("Username not found: " + username));
    }
}
