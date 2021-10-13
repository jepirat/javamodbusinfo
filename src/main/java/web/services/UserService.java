package web.services;

import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    public User findByLogin(String login);
    public Optional<User> findById(Long id);
    List<User> findAll();
    void save(User user);
    void delete(User user);
    void update(User user);
}
