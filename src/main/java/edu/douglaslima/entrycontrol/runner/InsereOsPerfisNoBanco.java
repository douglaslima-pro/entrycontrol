package edu.douglaslima.entrycontrol.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import edu.douglaslima.entrycontrol.domain.perfil.Perfil;
import edu.douglaslima.entrycontrol.domain.perfil.PerfilEnum;
import edu.douglaslima.entrycontrol.repository.PerfilRepository;

@Component
public class InsereOsPerfisNoBanco implements CommandLineRunner {
	
	@Autowired
	private PerfilRepository perfilRepository;

	@Override
	public void run(String... args) throws Exception {
		
		Perfil perfilUser = new Perfil();
		perfilUser.setNome(PerfilEnum.USER);
		
		Perfil perfilAdmin = new Perfil();
		perfilAdmin.setNome(PerfilEnum.ADMIN);
		
		perfilRepository.save(perfilUser);
		perfilRepository.save(perfilAdmin);
	}

}
