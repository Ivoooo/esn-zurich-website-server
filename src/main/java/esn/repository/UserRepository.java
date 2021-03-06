package esn.repository;
import esn.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByNickname(String nickname);
}
