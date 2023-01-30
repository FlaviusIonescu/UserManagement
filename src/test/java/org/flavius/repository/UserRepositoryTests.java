package org.flavius.repository;


import org.assertj.core.api.Assertions;
import org.flavius.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void should_find_one_user() {
        // given
        UserEntity user = new UserEntity();
        user.setUsername("test_user@test.com");
        user.setPassword("password");
        user.setRole("some role");
        user.setEnabled(true);
        entityManager.persist(user);
        entityManager.flush();

        // when
        UserEntity fromRepository = userRepository.findByUsername(user.getUsername());

        // then
        Assertions.assertThat(fromRepository.getUsername())
                .isEqualTo(user.getUsername());
    }
}
