package br.com.fiap.resource;

import br.com.fiap.bo.UsuarioBO;
import br.com.fiap.to.UsuarioTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postup")
public class UsuarioResource {
    // Atributo
    private UsuarioBO usuarioBO = new UsuarioBO();

    @GetMapping
    public ResponseEntity<List<UsuarioTO>> findAll() {
        List<UsuarioTO> usuarios = usuarioBO.findAll();
        if (usuarios != null) {
            // mensagem de retorno OK
            return ResponseEntity.status(HttpStatus.OK).body(usuarios);
        } else {
            // mensagem de retorno Error 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<?> findById(@PathVariable int idUsuario) {
        UsuarioTO usuario = usuarioBO.findById(idUsuario);

        if (usuario != null) {
            // mensagem de retorno OK
            return ResponseEntity.status(HttpStatus.OK).body(usuario);
        } else {
            // mensagem de retorno Error 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Remédio não encontrado!");
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid UsuarioTO usuario) {
        try {
            Object resultado = usuarioBO.save(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao salvar o remédio");
        }
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<?> update(@PathVariable int idUsuario, @RequestBody @Valid UsuarioTO usuario) {
        try {
            Object resultado = usuarioBO.update(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar remédio");
        }
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<String> delete(@PathVariable int idUsuario) {
        if (usuarioBO.delete(idUsuario)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Remédio deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Remédio não encontrado!");
        }
    }
}
