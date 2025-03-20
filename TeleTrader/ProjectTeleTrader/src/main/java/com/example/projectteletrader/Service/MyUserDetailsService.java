package com.example.projectteletrader.Service;


import com.example.projectteletrader.Model.UserInfo;
import com.example.projectteletrader.Repository.UserRepository;
import com.example.projectteletrader.Security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;




@Service
public class MyUserDetailsService  implements UserDetailsService{

    @Autowired
    private UserRepository userRepo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserInfo user  = userRepo.findByEmail(email);

        if(user!=null)
        {
            return new MyUserDetails(user);
        }

        throw new UsernameNotFoundException("user not found with this email : "+email);
    }

}
