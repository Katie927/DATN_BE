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
            executeSqlScript("schema.sql");
            executeSqlScript("data.sql");
            
            //verifyDataInsertion();
            System.out.println("=== Database Initialization Completed ===");
        } 
        // else {
        //     System.out.println("Database already has data. Checking for leads...");
            
        //     try {
        //         Integer leadCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM leads", Integer.class);
        //         System.out.println("Existing leads count: " + leadCount);
                
        //         if (leadCount == null || leadCount == 0) {
        //             System.out.println("No leads found. Inserting sample leads...");
        //             insertSampleLeads();
        //         }
        //     } catch (Exception e) {
        //         System.out.println("Leads table doesn't exist or error checking leads: " + e.getMessage());
        //     }
        // }

        // initializeUsers();
    }

    private boolean isDatabaseEmpty() {
        try {
            Integer roleCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM roles", Integer.class);
            Integer userCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
            Integer leadCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM leads", Integer.class);
            
            System.out.println("Existing roles count: " + roleCount);
            System.out.println("Existing users count: " + userCount);
            System.out.println("Existing leads count: " + leadCount);
            
            return (roleCount == null || roleCount == 0) && 
                   (userCount == null || userCount == 0) && 
                   (leadCount == null || leadCount == 0);
            
        } catch (Exception e) {
            System.out.println("Tables don't exist. Database is empty.");
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

    // private void initializeUsers() {
    //     if (!userRepository.existsByUsername("admin01")) {
    //         User admin = new User();
    //         admin.setUsername("admin01");
    //         admin.setEmail("admin01@crm.com");
    //         admin.setPassword(passwordEncoder.encode("admin123"));
    //         admin.setRole(ERole.ROLE_ADMIN);
            
    //         Set<Role> adminRoles = new HashSet<>();
    //         Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
    //             .orElseThrow(() -> new RuntimeException("Admin role not found"));
    //         adminRoles.add(adminRole);
    //         admin.setRoles(adminRoles);
            
    //         userRepository.save(admin);
    //         System.out.println("Created admin user: admin01/admin123");
    //     }

    //     if (!userRepository.existsByUsername("marketing01")) {
    //         User marketingUser = new User();
    //         marketingUser.setUsername("marketing01");
    //         marketingUser.setEmail("marketing01@crm.com");
    //         marketingUser.setPassword(passwordEncoder.encode("marketing123"));
    //         marketingUser.setRole(ERole.ROLE_MARKETING);

    //         Set<Role> marketingRoles = new HashSet<>();
    //         Role marketingRole = roleRepository.findByName(ERole.ROLE_MARKETING)
    //             .orElseThrow(() -> new RuntimeException("Marketing role not found"));
    //         marketingRoles.add(marketingRole);
    //         marketingUser.setRoles(marketingRoles);

    //         userRepository.save(marketingUser);
    //         System.out.println("Created marketing user: marketing01/marketing123");
    //     }

    //     if (!userRepository.existsByUsername("telesales01")) {
    //         User telesalesUser = new User();
    //         telesalesUser.setUsername("telesales01");
    //         telesalesUser.setEmail("telesales01@crm.com");
    //         telesalesUser.setPassword(passwordEncoder.encode("telesales123"));
    //         telesalesUser.setRole(ERole.ROLE_TELESALES);

    //         Set<Role> telesalesRoles = new HashSet<>();
    //         Role telesalesRole = roleRepository.findByName(ERole.ROLE_TELESALES)
    //             .orElseThrow(() -> new RuntimeException("Telesales role not found"));
    //         telesalesRoles.add(telesalesRole);
    //         telesalesUser.setRoles(telesalesRoles);

    //         userRepository.save(telesalesUser);
    //         System.out.println("Created telesales user: telesales01/telesales123");
    //     }

    //     if (!userRepository.existsByUsername("user01")) {
    //         User user = new User();
    //         user.setUsername("user01");
    //         user.setEmail("user01@crm.com");
    //         user.setPassword(passwordEncoder.encode("user123"));
    //         user.setRole(ERole.ROLE_USER);
            
    //         Set<Role> userRoles = new HashSet<>();
    //         Role userRole = roleRepository.findByName(ERole.ROLE_USER)
    //             .orElseThrow(() -> new RuntimeException("User role not found"));
    //         userRoles.add(userRole);
    //         user.setRoles(userRoles);
            
    //         userRepository.save(user);
    //         System.out.println("Created regular user: user/user123");
    //     }
    // }
}
