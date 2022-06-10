package com.ebono.bonosapi.common;

import com.ebono.bonosapi.dto.MetodoFinancieroResponse;
import com.ebono.bonosapi.dto.TipoCambioResponse;
import com.ebono.bonosapi.dto.UsuarioResponse;
import com.ebono.bonosapi.entities.MetodoFinanciero;
import com.ebono.bonosapi.entities.TipoCambio;
import com.ebono.bonosapi.entities.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public MetodoFinancieroResponse convertEntityToDtoMetodoFinanciero(MetodoFinanciero metodoFinanciero) {
        return modelMapper.map(metodoFinanciero, MetodoFinancieroResponse.class);
    }

    public List<TipoCambioResponse> convertEntityToDtoTipoCambio(List<TipoCambio> tipoCambios) {
        return tipoCambios.stream()
                .map(this::convertEntityToDtoTipoCambio)
                .collect(Collectors.toList());
    }

    public List<MetodoFinancieroResponse> convertEntityToDtoMetodoFinanciero(List<MetodoFinanciero> metodoFinancieros) {
        return metodoFinancieros.stream()
                .map(this::convertEntityToDtoMetodoFinanciero)
                .collect(Collectors.toList());
    }
}
