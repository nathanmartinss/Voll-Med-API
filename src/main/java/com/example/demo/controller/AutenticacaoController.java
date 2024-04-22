package com.example.demo.controller;

import com.example.demo.domain.usuario.DadosAutenticacao;
import com.example.demo.domain.usuario.Usuario;
import com.example.demo.infra.security.DadosTokenJWT;
import com.example.demo.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Indica que a classe é um controlador REST
@RequestMapping("/login") // Indica o caminho para acessar o controlador
public class AutenticacaoController {

    @Autowired // Injeção de dependência
    private AuthenticationManager manager;

    @Autowired // Injeção de dependência
    private TokenService tokenService;

    @PostMapping // Método POST
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) { // Recebe um objeto DadosAutenticacao

        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha()); // Cria um token de autenticação
        var authenticacion = manager.authenticate(authenticationToken); // Autentica o token

        var tokenJWT = tokenService.gerarToken((Usuario) authenticacion.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT)); // Retorna o token gerado

    }

}
