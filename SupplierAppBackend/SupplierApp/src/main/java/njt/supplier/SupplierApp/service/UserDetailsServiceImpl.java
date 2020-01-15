/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njt.supplier.SupplierApp.service;

import java.util.ArrayList;
import java.util.Collection;
import njt.supplier.SupplierApp.DAO.UserDAO;
import njt.supplier.SupplierApp.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ivan
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    
    UserDAO userDao;

    public UserDetailsServiceImpl(UserDAO userDao) {
        this.userDao = userDao;
    }
    
    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userDao.findByUsername(username);
        
        return new org.springframework.security.core.userdetails.User(u.getUsername(),u.getPassword(), new ArrayList<>());
    }
    
}
