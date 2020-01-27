/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njt.supplier.SupplierApp.REST;

import njt.supplier.SupplierApp.DAO.UserDAO;
import njt.supplier.SupplierApp.DAO.implementation.UserDAOImpl;
import njt.supplier.SupplierApp.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ivan
 */
@RestController
@RequestMapping("/users")
public class UserREST {

    private UserDAO userDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserREST(UserDAO userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/")
    public void signUp(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }



}
