/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njt.supplier.SupplierApp.DAO;

import njt.supplier.SupplierApp.entity.User;

/**
 *
 * @author Ivan
 */
public interface UserDAO {
    public User findByUsername(String username);

    public void save(User user);
}
