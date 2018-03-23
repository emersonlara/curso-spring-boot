package br.com.danielschmitz.meuprojeto.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.danielschmitz.meuprojeto.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	public Optional<User> findById(Integer id);
	
	public User findByEmail(String email);
	
	public List<User> findAll();
	
	public User save(User user);

	public User findByFirstnameAndLastname(String firstname, String lastname);
	
}
