package com.security.board.repository;

import com.security.board.model.Login;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findByEmailAndSenha(String email, String senha);
    Optional<Login> findByEmail(String email);
}
