package com.caort.security.config.security;

import com.caort.security.pojo.SystemAuthority;
import com.caort.security.pojo.SystemRole;
import com.caort.security.pojo.SystemUser;
import com.caort.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Caort
 * @date 2022/2/20 10:22
 */
@Component
public class CustomUserDetailService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailService.class);
    private static final String ROLE_PREFIX = "ROLE_";
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("开始查找用户");
        Optional<SystemUser> foundUserOpt = userRepository.findByUsername(username);
        if (foundUserOpt.isEmpty()) {
            return null;
        }
        SystemUser foundUser = foundUserOpt.get();
        SystemRole role = foundUser.getRole();
        List<GrantedAuthority> authorityList = role == null ? AuthorityUtils.NO_AUTHORITIES : convertAuthority(role.getAuthorityList());
        return new User(foundUser.getUsername(), foundUser.getPassword(), foundUser.getEnable(),
                true, true, true, authorityList);
    }

    private List<GrantedAuthority> convertAuthority(List<SystemAuthority> systemAuthorityList) {
        if (CollectionUtils.isEmpty(systemAuthorityList)) {
            return AuthorityUtils.NO_AUTHORITIES;
        }
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for (SystemAuthority systemAuthority : systemAuthorityList) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(ROLE_PREFIX + systemAuthority.getName()));
        }
        return grantedAuthorityList;
    }

}
