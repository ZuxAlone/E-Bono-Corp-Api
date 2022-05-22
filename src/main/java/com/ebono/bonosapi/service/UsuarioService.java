package com.ebono.bonosapi.service;

import com.ebono.bonosapi.common.UsuarioValidator;
import com.ebono.bonosapi.converter.UsuarioConverter;
import com.ebono.bonosapi.dto.LoginRequest;
import com.ebono.bonosapi.dto.LoginResponse;
import com.ebono.bonosapi.dto.SignupRequest;
import com.ebono.bonosapi.entities.Usuario;
import com.ebono.bonosapi.exception.BadResourceRequestException;
import com.ebono.bonosapi.exception.GeneralServiceException;
import com.ebono.bonosapi.exception.ResourceNotFoundException;
import com.ebono.bonosapi.repositories.UsuarioRepository;
import com.ebono.bonosapi.security.UserPrincipal;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
public class UsuarioService {

    @Value("${jwt.password}")
    private String jwtSecret;

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioService(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository, UsuarioConverter usuarioConverter) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.usuarioConverter = usuarioConverter;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Usuario signupUsuario(SignupRequest signupRequest) {
        try {
            UsuarioValidator.validateUser(signupRequest);
            Usuario existUser=usuarioRepository.findByCorreo(signupRequest.getCorreo()).orElse(null);

            if(existUser!=null) throw new BadResourceRequestException("Ya se registro una cuenta con este correo.");

            Usuario usuario = initUsuario(signupRequest);
            return usuarioRepository.save(usuario);
        } catch (BadResourceRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    public LoginResponse loginUsuario(LoginRequest loginRequest) {
        try {
            Usuario usuario = usuarioRepository.findByCorreo(loginRequest.getCorreo())
                    .orElseThrow(()->new BadResourceRequestException("No existe un usuario creado con este correo"));

            if(!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword()))
                throw new BadResourceRequestException("Contraseña incorrecta");

            String token = createToken(usuario);
            return usuarioConverter.fromEntity(usuario, token);
        } catch (BadResourceRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Usuario getUsuarioInfo() {
        Usuario usuario = UserPrincipal.getCurrentUser();
        return usuario;
    }

    public String createToken(Usuario usuario){
        Date now =new Date();
        Date expiryDate=new Date(now.getTime()+ (1000*60*60*24));

        return Jwts.builder()
                .setSubject(usuario.getCorreo())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (UnsupportedJwtException e) {
            log.error("JWT in a particular format/configuration that does not match the format expected");
        }catch (MalformedJwtException e) {
            log.error(" JWT was not correctly constructed and should be rejected");
        }catch (SignatureException e) {
            log.error("Signature or verifying an existing signature of a JWT failed");
        }catch (ExpiredJwtException e) {
            log.error("JWT was accepted after it expired and must be rejected");
        }
        return false;
    }

    public String getUsernameFromToken(String jwt) {
        try {
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();
        } catch (Exception e) {
            throw new BadResourceRequestException("Invalid Token");
        }
    }

    private Usuario initUsuario(SignupRequest signupRequest) {
        Usuario usuario = new Usuario();
        usuario.setNombres(signupRequest.getNombres());
        usuario.setApellidos(signupRequest.getApellidos());
        usuario.setCorreo(signupRequest.getCorreo());
        usuario.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        usuario.setNum_bonos_simulados(0);
        return usuario;
    }
}
