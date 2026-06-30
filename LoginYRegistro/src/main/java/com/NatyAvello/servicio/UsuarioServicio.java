package com.NatyAvello.servicio;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.NatyAvello.modelo.Usuario;
import com.NatyAvello.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final BCryptPasswordEncoder encriptador = new BCryptPasswordEncoder();

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public boolean existeNombreUsuario(String nombreUsuario) {
        return usuarioRepositorio.existsByNombreUsuario(nombreUsuario);
    }

    public Usuario registrar(Usuario usuario) {
        usuario.setContrasena(encriptador.encode(usuario.getContrasena()));
        return usuarioRepositorio.save(usuario);
    }

    public boolean validarLogin(String nombreUsuario, String contrasena) {
        Optional<Usuario> usuarioOpt = usuarioRepositorio.findByNombreUsuario(nombreUsuario);
        if (usuarioOpt.isEmpty()) {
            return false;
        }
        Usuario usuario = usuarioOpt.get();
        return encriptador.matches(contrasena, usuario.getContrasena());
    }

}