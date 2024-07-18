package edu.douglaslima.entrycontrol.domain.usuario;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "telefones", source = "telefones")
	void updateUsuarioFromUsuarioDTO(UsuarioDTO usuarioDTO, @MappingTarget Usuario usuario);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "telefones", source = "telefones")
	Usuario toEntity(UsuarioDTO usuarioDTO);
	
}
