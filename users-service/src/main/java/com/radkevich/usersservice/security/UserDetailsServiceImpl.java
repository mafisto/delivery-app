package com.radkevich.usersservice.security;

import com.radkevich.usersservice.entity.PersonsEntity;
import com.radkevich.usersservice.entity.UsersEntity;
import com.radkevich.usersservice.repository.UsersRepository;
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

    private final UsersRepository personsRepository;

    @Override
    @Transactional(readOnly = true)
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsersEntity> person = personsRepository.findByUsername(username);

        if (person.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        CustomUserDetails user = new CustomUserDetails(person.get().getUsername(), person.get().getPassword(), person.get().getActive(), true, true, true, AuthorityUtils.createAuthorityList(person.get().getRoleId().getRole()), person.get().getId());
        return user;
    }
}