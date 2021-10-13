package web.services;

import org.springframework.stereotype.Service;
import web.model.Role;
import web.model.User;
import web.repos.RoleRepo;
import web.repos.UserRepo;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;
    private RoleRepo roleRepo;

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public User findByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public void save(User user) {
        Set<Role> rolesDefault = new HashSet<>();
        if (roleRepo.findByName("USER") != null) {
            rolesDefault.add(roleRepo.findByName("USER"));
            userRepo.save(user);
        } if (roleRepo.findByName("USER") == null) {
            rolesDefault.forEach(role -> roleRepo.save(role));
        }
        user.setRoles(rolesDefault);
        userRepo.save(user);
    }

    @Override
    public void delete(User user) {
       userRepo.delete(user);
    }

    @Override
    public void update(User user) {
        Set<Role> roleSet = new HashSet<>();
        user.getRole().forEach(role -> {
                    if (roleRepo.findByName(role.getName()) != null) {
                        roleSet.add(roleRepo.findByName(role.getName()));
                    }
                    if (roleRepo.findByName(role.getName()) == null) {
                        roleRepo.save(role);
                        roleSet.add(role);
                    }
                });
        user.setRoles(roleSet);
        userRepo.save(user);
    }
}
