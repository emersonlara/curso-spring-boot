package br.com.danielschmitz.meuprojeto.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.danielschmitz.meuprojeto.model.User;
import br.com.danielschmitz.meuprojeto.model.UserRepository;

@RestController
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/users")
	public List<User> getAll(){
		return userRepository.findAll();
	}
	
	@PostMapping("/user")
	public User save(@RequestBody @Valid User user) {
		return userRepository.save(user);
	}
	
	@GetMapping("/user/getByEmail")
	public User getByEmail (@RequestBody User user) throws Exception {
		User u = userRepository.findByEmail(user.getEmail());
		if (u == null)
			throw new Exception("Usuário não encontrado");
		return u;
	}

	@GetMapping("/user/get/{firstname}/{lastname}")
	public User getByFirstnameAndLastname (@PathVariable String firstname, @PathVariable String lastname) throws Exception{
		User user = userRepository.findByFirstnameAndLastname(firstname,lastname);
		if (user == null)
			throw new Exception("Usuário não encontrado");
		return user;
		
	}

	@GetMapping("/")
	public String HelloWorld(){
		return "Hello World";
	}
	
}
