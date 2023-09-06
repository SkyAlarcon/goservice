package com.soulcode.goserviceapp.controller;

import com.soulcode.goserviceapp.domain.Endereco;
import com.soulcode.goserviceapp.domain.Usuario;
import com.soulcode.goserviceapp.repository.EnderecoRepository;
import com.soulcode.goserviceapp.service.EnderecoService;
import com.soulcode.goserviceapp.service.UsuarioService;
import com.soulcode.goserviceapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping(value = "/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping(value = "/new")
    public ModelAndView alterarEndereco(Authentication authentication) {
        ModelAndView mv = new ModelAndView("alterarEndereco");
        try{
            Usuario usuario = usuarioService.findAuthenticated(authentication);
            Long enderecoUser = usuarioRepository.findIdEndByIdUsu(usuario.getId());
            Endereco endereco = enderecoRepository.findEnderecoById(enderecoUser);
            mv.addObject("endereco", endereco);
            return mv;
        } catch (Exception ex) {
            mv.addObject("errorMessage", "Erro ao carregar endereço.");
            return mv;
        }
    }

    @PostMapping(value = "/new")
    public String update(
            Authentication authentication,
            RedirectAttributes attributes,
            @RequestParam(name = "logradouro") String logradouro,
            @RequestParam(name = "numero") Integer numero,
            @RequestParam(name = "complemento") String complemento,
            @RequestParam(name = "cidade") String cidade,
            @RequestParam(name = "uf") String uf
    ) {
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario  = usuarioService.findAuthenticated(authentication);
            Long idEndereco = usuarioRepository.findIdEndByIdUsu(usuario.getId());
            try {
                Endereco endUpdate = enderecoRepository.findEnderecoById(idEndereco);
                enderecoService.updateEndereco(logradouro, numero, complemento, cidade, uf, idEndereco, endUpdate);
                attributes.addFlashAttribute("successMessage", "Endereço alterado.");
            } catch (Exception ex) {
                attributes.addFlashAttribute("errorMessage", "Erro ao alterar endereço.");
            }
            return "redirect:/endereco/new";
        }
        throw new RuntimeException("Usuário não autenticado.");
    }

}
