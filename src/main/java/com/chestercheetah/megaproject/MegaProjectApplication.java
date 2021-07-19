package com.chestercheetah.megaproject;

import com.chestercheetah.megaproject.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MegaProjectApplication {

	@Autowired
	private static RoleService roleService;

	public static void main(String[] args) {

		try {
			roleService.saveRoleListIfNotSaved();
		} catch (Exception ignored) {}

		SpringApplication.run(MegaProjectApplication.class, args);
	}
}
