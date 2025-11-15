package com.DATN.Bej.config;

import java.util.HashSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.DATN.Bej.repository.RoleRepository;
import com.DATN.Bej.repository.UserRepository;

import lombok.AllArgsConstructor;


@Component
@AllArgsConstructor
public class DatabaseInitlizer implements CommandLineRunner{

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    private final DataSource dataSource;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Checking Database Initialization ===");
        
        if (isDatabaseEmpty()) {
            System.out.println("Database is empty. Running SQL scripts...");
            //executeSqlScript("schema.sql");
            executeSqlScript("data.sql");
            
            //verifyDataInsertion();
            System.out.println("=== Database Initialization Completed ===");
        } 
    }

    private boolean isDatabaseEmpty() {
        try {
            Integer productCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM product", Integer.class);
            
            System.out.println("Existing products count: " + productCount);
            
            // Chỉ seed data nếu KHÔNG có sản phẩm
            return (productCount == null || productCount == 0);
            
        } catch (Exception e) {
            System.out.println("Tables don't exist or error checking database. Assuming empty.");
            return true;
        }
    }

    private void verifyDataInsertion() {
        try {
            Integer roleCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM roles", Integer.class);
            Integer userCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
            Integer leadCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM leads", Integer.class);
            
            System.out.println("Verification Results:");
            System.out.println("- Roles inserted: " + (roleCount != null ? roleCount : 0));
            System.out.println("- Users inserted: " + (userCount != null ? userCount : 0));
            System.out.println("- Leads inserted: " + (leadCount != null ? leadCount : 0));
            
            if ((roleCount == null || roleCount == 0) || 
                (userCount == null || userCount == 0)) {
                System.err.println("Data insertion seems to have failed!");
            } else {
                System.out.println("Data insertion successful!");
            }
        } catch (Exception e) {
            System.err.println("Error verifying data insertion: " + e.getMessage());
        }
    }

    private void executeSqlScript(String scriptPath) {
        try {
            System.out.println("Executing " + scriptPath + "...");
            
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(new ClassPathResource(scriptPath));
            populator.setContinueOnError(true);
            populator.setIgnoreFailedDrops(true);
            populator.execute(dataSource);
            
            System.out.println("Successfully executed " + scriptPath);
        } catch (Exception e) {
            System.err.println("Error executing " + scriptPath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}