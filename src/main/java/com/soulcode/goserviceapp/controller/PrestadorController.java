package com.soulcode.goserviceapp.controller;

import com.soulcode.goserviceapp.domain.*;
import com.soulcode.goserviceapp.service.AgendamentoService;
import com.soulcode.goserviceapp.service.MensagemService;
import com.soulcode.goserviceapp.service.PrestadorService;
import com.soulcode.goserviceapp.service.ServicoService;
import com.soulcode.goserviceapp.service.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/prestador")
public class PrestadorController {

    @Autowired
    private PrestadorService prestadorService;

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private MensagemService mensagemService;

    @GetMapping(value = "/dados")
    public ModelAndView dados(Authentication authentication) {
        ModelAndView mv = new ModelAndView("dadosPrestador");
        try {
            Prestador prestador = prestadorService.findAuthenticated(authentication);
            mv.addObject("prestador", prestador);
            List<Servico> especialidades = servicoService.findByPrestadorEmail(authentication.getName());
            mv.addObject("especialidades", especialidades);
            List<Servico> servicos = servicoService.findAll();
            mv.addObject("servicos", servicos);
        } catch (UsuarioNaoAutenticadoException | UsuarioNaoEncontradoException ex) {
            mv.addObject("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            mv.addObject("errorMessage", "Erro ao carregar dados do prestador.");
        }
        return mv;
    }

    @PostMapping(value = "/dados")
    public String editarDados(Prestador prestador, RedirectAttributes attributes) {
        try {
            prestadorService.update(prestador);
            attributes.addFlashAttribute("successMessage", "Dados alterados.");
        } catch (UsuarioNaoEncontradoException ex) {
            attributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            attributes.addFlashAttribute("errorMessage", "Erro ao alterar dados cadastrais.");
        }
        return "redirect:/prestador/dados";
    }

    @PostMapping(value = "/dados/especialidade/remover")
    public String removerEspecialidade(
            @RequestParam(name = "servicoId") Long id,
            Authentication authentication,
            RedirectAttributes attributes) {
        try {
            prestadorService.removeServicoPrestador(authentication, id);
            attributes.addFlashAttribute("successMessage", "Especialidade removida");
        } catch (UsuarioNaoAutenticadoException | UsuarioNaoEncontradoException | ServicoNaoEncontradoException ex) {
            attributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            attributes.addFlashAttribute("errorMessage", "Erro ao remover especialidade.");
        }
        return "redirect:/prestador/dados";
    }

    @PostMapping(value = "/dados/especialidade/adicionar")
    public String adicionarEspecialidade(
            @RequestParam(name = "servicoId") Long id,
            Authentication authentication,
            RedirectAttributes attributes) {
        try {
            prestadorService.addServicoPrestador(authentication, id);
            attributes.addFlashAttribute("successMessage", "Especialidade adicionada.");
        } catch (UsuarioNaoAutenticadoException | UsuarioNaoEncontradoException | ServicoNaoEncontradoException ex) {
            attributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            attributes.addFlashAttribute("errorMessage", "Erro ao adicionar nova especialidade.");
        }
        return "redirect:/prestador/dados";
    }

    @GetMapping(value = "/agenda")
    public ModelAndView agenda(Authentication authentication) {
        ModelAndView mv = new ModelAndView("agendaPrestador");
        try {
            List<Agendamento> agendamentos = agendamentoService.findByPrestador(authentication);
            mv.addObject("agendamentos", agendamentos);
        } catch (UsuarioNaoAutenticadoException | UsuarioNaoEncontradoException ex) {
            mv.addObject("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            mv.addObject("errorMessage", "Erro ao carregar dados de agendamentos.");
        }
        return mv;
    }

    @PostMapping(value = "/agenda/pesquisa")
    public ModelAndView searchAgenda(@RequestParam(name = "data-inicio")String dataInicio, @RequestParam(name = "data-fim")String dataFim){
        ModelAndView mv = new ModelAndView("agendaPrestador");
        try {
            List<Agendamento> busca_agendamento = agendamentoService.findByData(dataInicio, dataFim);
            mv.addObject("agendamentos", busca_agendamento);
        }catch (AgendamentoNaoEncontradoException ex){
            mv.addObject("errorMessage",  ex.getMessage());
        }catch (Exception ex){
            mv.addObject("errorMessage", "Erro ao Buscar Agendamento(s)");
        }
        return mv;
    }

    @PostMapping(value = "/agenda/cancelar")
    public String cancelarAgendamento(
            @RequestParam(name = "agendamentoId") Long agendamentoId,
            Authentication authentication,
            RedirectAttributes attributes) {
        try {
            agendamentoService.cancelAgendaPrestador(authentication, agendamentoId);
            attributes.addFlashAttribute("successMessage", "Agendamento cancelado.");
        } catch (UsuarioNaoAutenticadoException | UsuarioNaoEncontradoException |
                AgendamentoNaoEncontradoException | StatusAgendamentoImutavelException ex) {
            attributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            attributes.addFlashAttribute("errorMessage", "Erro ao cancelar agendamento.");
        }
        return "redirect:/prestador/agenda";
    }

    @PostMapping(value = "/agenda/confirmar")
    public String confirmarAgendamento(
            @RequestParam(name = "agendamentoId") Long agendamentoId,
            Authentication authentication,
            RedirectAttributes attributes) {
        try {
            agendamentoService.confirmAgenda(authentication, agendamentoId);
            attributes.addFlashAttribute("successMessage", "Agendamento confirmado.");
        } catch (UsuarioNaoAutenticadoException | UsuarioNaoEncontradoException |
                 AgendamentoNaoEncontradoException | StatusAgendamentoImutavelException ex) {
            attributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            attributes.addFlashAttribute("errorMessage", "Erro ao confirmar agendamento.");
        }
        return "redirect:/prestador/agenda";
    }

    @PostMapping(value = "/agendamento/chat")
    public String chat(
            @RequestParam(name = "conteudo") String conteudo,
            @RequestParam(name = "remetenteId") String remententeId,
            @RequestParam(name = "destinatarioId") String destinatarioId,
            @RequestParam(name = "agendamentoId") String agendamentoId,
            Authentication authentication,
            RedirectAttributes attributes
    ) {
        try {
            mensagemService.enviarMensagem(conteudo, Long.parseLong(remententeId), Long.parseLong(destinatarioId), Long.parseLong(agendamentoId));
        } catch (UsuarioNaoAutenticadoException | UsuarioNaoEncontradoException ex) {
            attributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            attributes.addFlashAttribute("errorMessage", "Erro ao enviar mensagem.");
        }
        return "redirect:/prestador/agendamento/chat/"+agendamentoId;
    }

    @GetMapping(value = "/agendamento/chat/{id}")
    public ModelAndView chat(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("chat");
        try {
            List<Mensagem> mensagens = mensagemService.findMensagens(id);
            mv.addObject("mensagens", mensagens);
            Agendamento agendamento = agendamentoService.findById(id);
            mv.addObject("agendamento", agendamento);
            Prestador prestador = prestadorService.findById(agendamento.getPrestador().getId());
            mv.addObject("usuario", prestador);
            String tipoUsuario = "prestador";
            mv.addObject("tipoUsuario", tipoUsuario);
        } catch (UsuarioNaoAutenticadoException | UsuarioNaoEncontradoException ex) {
            mv.addObject("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            mv.addObject("errorMessage", "Erro ao carregar dados de agendamentos.");
        }
        return mv;
    }
}
