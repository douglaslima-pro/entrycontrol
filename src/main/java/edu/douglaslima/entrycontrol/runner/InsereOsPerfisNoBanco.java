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
		Perfil user = new Perfil();
		user.setNome(PerfilEnum.USER);
		Perfil admin = new Perfil();
		admin.setNome(PerfilEnum.ADMIN);
		perfilRepository.save(user);
		perfilRepository.save(admin);
	}

}
