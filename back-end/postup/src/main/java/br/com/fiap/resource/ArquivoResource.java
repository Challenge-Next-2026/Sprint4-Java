package br.com.fiap.resource;

import br.com.fiap.bo.ArquivoBO;
import br.com.fiap.to.ArquivoTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postup/arquivo")
public class ArquivoResource {
    // Atributo
    private ArquivoBO arquivoBO = new ArquivoBO();

    @GetMapping
    public ResponseEntity<List<ArquivoTO>> findAll() {
        List<ArquivoTO> arquivos = arquivoBO.findAll();
        if (arquivos != null) {
            // mensagem de retorno OK
            return ResponseEntity.status(HttpStatus.OK).body(arquivos);
        } else {
            // mensagem de retorno Error 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{idArquivo}")
    public ResponseEntity<?> findById(@PathVariable int idArquivo) {
        ArquivoTO arquivo = arquivoBO.findById(idArquivo);

        if (arquivo != null) {
            // mensagem de retorno OK
            return ResponseEntity.status(HttpStatus.OK).body(arquivo);
        } else {
            // mensagem de retorno Error 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arquivo não encontrado!");
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid ArquivoTO arquivo) {
        try {
            ArquivoTO resultado = arquivoBO.save(arquivo);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao salvar o arquivo");
        }
    }

    @PutMapping("/{idArquivo}")
    public ResponseEntity<?> update(@PathVariable int idArquivo, @RequestBody @Valid ArquivoTO arquivo) {
        try {
            ArquivoTO resultado = arquivoBO.update(arquivo);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar arquivo");
        }
    }

    @DeleteMapping("/{idArquivo}")
    public ResponseEntity<String> delete(@PathVariable int idArquivo) {
        if (arquivoBO.delete(idArquivo)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Arquivo deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arquivo não encontrado!");
        }
    }
}
