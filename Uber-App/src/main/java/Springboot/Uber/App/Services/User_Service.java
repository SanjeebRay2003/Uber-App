package Springboot.Uber.App.Services;

import Springboot.Uber.App.Entities.User;
import Springboot.Uber.App.Exceptions.ResourceNotFoundException;
import Springboot.Uber.App.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class User_Service implements UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElse(null);
    }

    public User getUserById(Long userId) {
      return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not found with Id "+userId));

    }
}
