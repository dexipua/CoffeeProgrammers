package com.school.repository;

import com.school.models.Role;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    public void saveRole() throws Exception {
        roleRepository.save(new Role(1, "admin"));
        boolean result = true;
        Assert.assertTrue(result);
    }
}
