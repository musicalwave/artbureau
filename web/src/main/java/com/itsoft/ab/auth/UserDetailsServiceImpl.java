package com.itsoft.ab.auth;

import com.itsoft.ab.model.UserModel;
import com.itsoft.ab.persistence.UsersMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static com.itsoft.ab.logger.LoggerConstants.AUTH_LOGGER;

/**
 * Created with IntelliJ IDEA.
 * User: Павел
 * Date: 05.12.13
 * Time: 22:17
 * To change this template use File | Settings | File Templates.
 */
public class UserDetailsServiceImpl implements UserDetailsService, InitializingBean {
    private final static Logger LOGGER = Logger.getLogger(AUTH_LOGGER);

    @Autowired
    private UsersMapper usersMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Attempting to log in with login \"" + username + "\"");
        final UserModel user = usersMapper.selectByLogin(username);

        if (user == null){
            throw new UsernameNotFoundException("User \"" + username + "\" not found");
        }

        final String role = usersMapper.selectRoleById(user.getRoleId());

        LOGGER.debug("Username = " + username);
        LOGGER.debug("RoleId = " + user.getRoleId());

        User user1 =  new User(
                user.getLogin(),
                user.getPasswordHash(),
                Arrays.<GrantedAuthority>asList(new GrantedAuthority[]{new GrantedAuthority() {

                    @Override
                    public String getAuthority() {
                        return role;
                    }
                }}));
        LOGGER.info("Loaded user with login \"" + user1.getUsername() + "\"");
        return user1;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (usersMapper == null) {
            throw new NullPointerException("usersMapper is null.");
        }
    }

    public void setUsersMapper(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }
}
