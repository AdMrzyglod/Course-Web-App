package com.application.mainapp.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final RoleSeeder roleSeeder;
    private final CategorySeeder categorySeeder;
    private final UserSeeder userSeeder;
    private final CourseSeeder courseSeeder;
    private final OrderSeeder orderSeeder;

    @Value("${app.seeding.sample-data.enabled:true}")
    private boolean sampleDataEnabled;

    @Override
    public void run(String... args) {
        roleSeeder.seed();
        categorySeeder.seed();
        userSeeder.seedMainAdmin();

        if (sampleDataEnabled) {
            userSeeder.seedMockUsers();
            courseSeeder.seed();
            orderSeeder.seed();
        }
    }
}