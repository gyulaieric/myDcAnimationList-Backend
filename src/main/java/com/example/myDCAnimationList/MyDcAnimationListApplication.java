package com.example.myDCAnimationList;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.myDCAnimationList.model.User;
import com.example.myDCAnimationList.model.Role;
import com.example.myDCAnimationList.repository.RoleRepository;
import com.example.myDCAnimationList.repository.UserRepository;

@SpringBootApplication
public class MyDcAnimationListApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyDcAnimationListApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode){
		return args ->{
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			User admin = new User(1L, "admin", passwordEncode.encode("password"), roles);

			userRepository.save(admin);
		};
	}
}
