package com.pereira.jaala.prontuarios.controller;

import com.pereira.jaala.prontuarios.model.Login;
import com.pereira.jaala.prontuarios.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private LoginService loginService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new LoginController(loginService)).build();
    }

    @Test
    void deveAceitarFormularioUrlEncodedSemExporCredenciaisNaUrl() throws Exception {
        Login usuario = new Login();
        usuario.setId(7L);
        usuario.setNome("psicologa");
        when(loginService.findUsuario(argThat(dto ->
                "psicologa".equals(dto.getNome()) && "1234".equals(dto.getSenha()))))
                .thenReturn(usuario);

        MvcResult resultado = mockMvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("nome", "psicologa")
                        .param("senha", "1234"))
                .andExpect(status().isSeeOther())
                .andExpect(redirectedUrl("/html/home.html"))
                .andReturn();

        assertEquals("7", resultado.getRequest().getSession().getAttribute("usuarioId").toString());
    }
}
