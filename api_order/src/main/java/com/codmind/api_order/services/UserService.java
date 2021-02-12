package com.codmind.api_order.services;

import com.codmind.api_order.converters.UserConverter;
import com.codmind.api_order.dtos.LoginRequestDTO;
import com.codmind.api_order.dtos.LoginResponseDTO;
import com.codmind.api_order.entity.User;
import com.codmind.api_order.exceptions.DataServiceException;
import com.codmind.api_order.exceptions.GeneralServiceException;
import com.codmind.api_order.exceptions.ValidateServiceException;
import com.codmind.api_order.repository.UserRepository;
import com.codmind.api_order.validator.UserValidator;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class UserService {
    @Value("${jwt.secret-key}")
    private String jwtSecretKey;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User create (User user){
        try{
            UserValidator.signup(user);
            User existUser = userRepository.findByUsername(user.getUsername()).orElse(null);
            if (existUser != null) throw new ValidateServiceException("User already exist");
            String encoder = passwordEncoder.encode(user.getPassword());
            user.setPassword(encoder);
            return userRepository.save(user);
        }catch (ValidateServiceException | DataServiceException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw  new GeneralServiceException(e.getMessage(), e);
        }
    }

    public LoginResponseDTO login (LoginRequestDTO request){
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new ValidateServiceException("user or password incorrect"));
/*        if (!user.getPassword().equals(request.getPassword())) throw new ValidateServiceException("user or password incorrect");*/
        //COMPARA PASSWORD ENCRIPTADO - CON EL PASSWORD CRUDO
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) throw new ValidateServiceException("user or password incorrect");
        String token = createToken(user);
        return new LoginResponseDTO(userConverter.frontEntity(user), token);
    }

    public String createToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + (1000*60*60));

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token);
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
    public String getUserNameFrontToken(String jwt){
        try{
            return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(jwt).getBody().getSubject();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new ValidateServiceException("Invalid Token");
        }
    }
}
