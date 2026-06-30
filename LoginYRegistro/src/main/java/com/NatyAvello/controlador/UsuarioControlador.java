package com.NatyAvello.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.NatyAvello.dto.LoginDTO;
import com.NatyAvello.dto.RegistroDTO;
import com.NatyAvello.modelo.Usuario;
import com.NatyAvello.servicio.UsuarioServicio;

import jakarta.validation.Valid;

@Controller
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/")
    public String mostrarFormularios(Model model) {
        if (!model.containsAttribute("registroDTO")) {
            model.addAttribute("registroDTO", new RegistroDTO());
        }
        if (!model.containsAttribute("loginDTO")) {
            model.addAttribute("loginDTO", new LoginDTO());
        }
        return "index";
    }

    @PostMapping("/procesa/registro")
    public String procesarRegistro(@Valid @ModelAttribute("registroDTO") RegistroDTO registroDTO,
                                    BindingResult bindingResult,
                                    Model model) {

        if (registroDTO.getContrasena() != null
                && !registroDTO.getContrasena().equals(registroDTO.getConfirmacionContrasena())) {
            bindingResult.rejectValue("confirmacionContrasena", "error.registroDTO",
                    "Las contraseñas no coinciden");
        }

        if (registroDTO.getNombreUsuario() != null
                && usuarioServicio.existeNombreUsuario(registroDTO.getNombreUsuario())) {
            bindingResult.rejectValue("nombreUsuario", "error.registroDTO",
                    "Ese nombre de usuario ya está en uso");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("loginDTO", new LoginDTO());
            return "index";
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(registroDTO.getNombreUsuario());
        usuario.setContrasena(registroDTO.getContrasena());
        usuario.setCorreo(registroDTO.getCorreo());
        usuario.setNombre(registroDTO.getNombre());
        usuario.setApellido(registroDTO.getApellido());
        usuario.setFechaDeNacimiento(registroDTO.getFechaDeNacimiento());

        usuarioServicio.registrar(usuario);

        return "redirect:/inicio";
    }

    @PostMapping("/procesa/login")
    public String procesarLogin(@Valid @ModelAttribute("loginDTO") LoginDTO loginDTO,
                                 BindingResult bindingResult,
                                 Model model) {

        if (!bindingResult.hasErrors()) {
            boolean valido = usuarioServicio.validarLogin(loginDTO.getNombreUsuario(), loginDTO.getContrasena());
            if (!valido) {
                bindingResult.rejectValue("contrasena", "error.loginDTO",
                        "Usuario o contraseña incorrectos");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("registroDTO", new RegistroDTO());
            return "index";
        }

        return "redirect:/inicio";
    }

    @GetMapping("/inicio")
    public String mostrarInicio() {
        return "inicio";
    }

}