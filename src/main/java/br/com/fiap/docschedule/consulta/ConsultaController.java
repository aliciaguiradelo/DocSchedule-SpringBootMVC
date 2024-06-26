package br.com.fiap.docschedule.consulta;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.docschedule.user.User;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("consultas")
@Slf4j
public class ConsultaController {

    @Autowired
    ConsultaRepository repository;

    @Autowired
    MessageSource messageSource;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal DefaultOAuth2User  user){
        User myuser = (User) user;
        log.info("usuario carregado: "+ myuser);
        List<Consulta> consultas = repository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        model.addAttribute("user", user.getAttribute("name"));
        model.addAttribute("avatar", user.getAttribute("avatar_url"));

        List<ConsultaDTO> consultasDTO = consultas.stream()
                .map(consulta -> new ConsultaDTO(
                        consulta.getId(),
                        consulta.getEspecialidade(),
                        consulta.getMotivo(),
                        consulta.getDataConsulta().format(formatter)))
                .collect(Collectors.toList());

        model.addAttribute("consultas", consultasDTO);

        return "consulta/index";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        var consulta = repository.findById(id);

        if(consulta.isEmpty()) {
            redirect.addFlashAttribute("message", "Erro ao apagar! Tarefa não encontrada.");
            return "redirect:/consultas";
        }

        repository.deleteById(id);
        redirect.addFlashAttribute("message", messageSource.getMessage("consulta.delete", null, LocaleContextHolder.getLocale()));
        return "redirect:/consultas";
    }

    @GetMapping("new")
    public String form(Consulta consulta) {
        return "consulta/form";
    }

    @PostMapping
    public String create(@Valid Consulta consulta, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) return "consulta/form" ;
        log.info("Agendando nova consulta - " + consulta);
        repository.save(consulta);
        redirect.addFlashAttribute("message", "Tarefa cadastrada com sucesso");
        return "redirect:/consultas";
    }
    
}
