package com.codmind.api_order.security;

import com.codmind.api_order.entity.User;
import com.codmind.api_order.exceptions.DataServiceException;
import com.codmind.api_order.repository.UserRepository;
import com.codmind.api_order.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //CHANGE OF RESPONSIBILITY PATTERN
        try{
            //1- EXTRAER EL TOKEN DEL REQUEST
            String jwt = getJWTFrontRequest(httpServletRequest);
            if (StringUtils.hasText(jwt) && userService.validateToken(jwt)){
                //2- EXTRAER EL USUARIO AUTENTICADO DEL TOKEN (sub: orlando)
                String username = userService.getUserNameFrontToken(jwt);
                //3- VERIFICAR SI EL USUARIO EXISTE
                User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new DataServiceException("User does not exist" ));
                //4- DECIRLE A SPRING SECURITY QUE ESTE EL USUARIO AUTENTICADO
                //HACE UN WRAPPER AL USER
                UserPrincipal principal = UserPrincipal.create(user);
                //5- CREAR el objeto de Autenticación resultante en el SecurityContext
                // HACE UN WRAPPER AL PRINCIPAL
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
                //6- DEFINIR LA FUENTE DE AUTENTICACION -> VIA WEB
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                //7- DECIRLE AL SecurityContext ESTE ES EL USUARIO
                //AHORA PODEMOS EXTRAER EL USUARIO DESDE CUALQUIER PARTE DE LA APLICACION
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }catch (Exception e){
            log.error("ERROR HANDLER - AUTHENTICATE USER", e);
        }
        // VERIFICA SI EXISTEN MAS FILTROS
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
    public String getJWTFrontRequest(HttpServletRequest httpServletRequest){
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
