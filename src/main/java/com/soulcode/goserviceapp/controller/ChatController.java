package com.soulcode.goserviceapp.controller;


import com.soulcode.goserviceapp.service.AgendamentoService;
import com.soulcode.goserviceapp.service.MensagemService;
import com.soulcode.goserviceapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/chat")
public class ChatController {

    @Autowired
    private MensagemService mensagemService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AgendamentoService agendamentoService;

    @GetMapping(value = "/")
    public ModelAndView portal(Authentication authentication) {
        ModelAndView mv = new ModelAndView("portalMensagens");
        try {

        } catch (Exception ex) {

        }
        return mv;
    }
}

