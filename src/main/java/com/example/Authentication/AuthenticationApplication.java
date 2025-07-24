package com.example.Authentication;

import com.example.Authentication.Entity.Role;
import com.example.Authentication.Entity.User;
import com.example.Authentication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AuthenticationApplication  implements CommandLineRunner {
@Autowired
private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}


	public void run(String... args){
		User adminAccount=userRepository.findByRole(Role.ADMIN);
		if(adminAccount==null){
			User user=new User();

			user.setEmail("admin@gmail.com");
			user.setFirstName("admin");
			user.setSecondName("admin");
			user.setRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
userRepository.save(user);

		}




	}
}
