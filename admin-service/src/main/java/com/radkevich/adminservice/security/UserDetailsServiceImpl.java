package com.radkevich.adminservice.security;

import com.radkevich.adminservice.entity.AdminsEntity;
import com.radkevich.adminservice.repository.AdminsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdminsRepository adminsRepository;

    @Override
    @Transactional(readOnly = true)
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AdminsEntity> person = adminsRepository.findByUsername(username);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        CustomUserDetails user = new CustomUserDetails(person.get().getUsername(), person.get().getPassword(),
                person.get().getActive(), true, true, true,
                AuthorityUtils.createAuthorityList(person.get().getRoleId().getRole()), person.get().getId());
        return user;
    }
}