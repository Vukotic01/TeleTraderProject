package com.example.projectteletrader.Repository;
import com.example.projectteletrader.Model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<UserInfo, Integer>{

    UserInfo findByEmail(String username);

}
