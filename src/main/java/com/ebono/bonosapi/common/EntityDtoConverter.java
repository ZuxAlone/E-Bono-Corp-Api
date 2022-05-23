package com.ebono.bonosapi.common;

import com.ebono.bonosapi.dto.TipoCambioResponse;
import com.ebono.bonosapi.dto.UsuarioResponse;
import com.ebono.bonosapi.entities.TipoCambio;
import com.ebono.bonosapi.entities.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityDtoConverter {

    private final ModelMapper modelMapper;

    public EntityDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UsuarioResponse convertEntityToDtoUser(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioResponse.class);
    }

    public TipoCambioResponse convertEntityToDtoTipoCambio(TipoCambio tipoCambio) {
        return modelMapper.map(tipoCambio, TipoCambioResponse.class);
    }
}
