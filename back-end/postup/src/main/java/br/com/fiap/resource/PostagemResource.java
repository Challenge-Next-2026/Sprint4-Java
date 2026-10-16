package br.com.fiap.resource;

import br.com.fiap.bo.PostagemBO;
import br.com.fiap.to.PostagemTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("postup/postagem")
public class PostagemResource {
    // Atributo
    private PostagemBO postagemBO = new PostagemBO();

    @GetMapping
    public ResponseEntity<List<PostagemTO>> findAll() {
        List<PostagemTO> postagens = postagemBO.findAll();
        if (postagens != null) {
            // mensagem de retorno OK
            return ResponseEntity.status(HttpStatus.OK).body(postagens);
        } else {
            // mensagem de retorno Error 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{idPostagem}")
    public ResponseEntity<?> findById(@PathVariable int idPostagem) {
        PostagemTO postagem = postagemBO.findById(idPostagem);

        if (postagem != null) {
            // mensagem de retorno OK
            return ResponseEntity.status(HttpStatus.OK).body(postagem);
        } else {
            // mensagem de retorno Error 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Postagem não encontrado!");
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid PostagemTO postagem) {
        try {
            PostagemTO resultado = postagemBO.save(postagem);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao salvar a postagem");
        }
    }

    @PutMapping("/{idPostagem}")
    public ResponseEntity<?> update(@PathVariable int idPostagem, @RequestBody @Valid PostagemTO postagem) {
        try {
            PostagemTO resultado = postagemBO.update(postagem);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar postagem");
        }
    }

    @DeleteMapping("/{idPostagem}")
    public ResponseEntity<String> delete(@PathVariable int idPostagem) {
        if (postagemBO.delete(idPostagem)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Postagem deletada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Postagem não encontrada!");
        }
    }
}
