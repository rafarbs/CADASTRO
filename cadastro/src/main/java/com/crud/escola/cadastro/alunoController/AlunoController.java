package com.crud.escola.cadastro.alunoController;


import com.crud.escola.cadastro.alunoDto.AlunoDTO;
import com.crud.escola.cadastro.alunoRepository.AlunoRepository;
import com.crud.escola.cadastro.model.Aluno;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cadastro")
@Tag("alunos")
@Slf4j

public class AlunoController {

    @Autowired
    private AlunoRepository aluno;

    @GetMapping("/alunos")
    public ResponseEntity<?> listaAlunos() {
        List<Aluno> pesquisaluno = aluno.findAll();

        if (pesquisaluno.isEmpty() || pesquisaluno == null) {
            return new ResponseEntity<>("Nenhum dado encontrado na tabela", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(pesquisaluno, HttpStatus.OK);
        }
    }

    @GetMapping("/alunos/{id}")
    public ResponseEntity<?> buscarAluno(@PathVariable("id") Integer id) {
        Optional<Aluno> alunounico = aluno.findById(id);
        if (alunounico.isEmpty() || alunounico == null) {
            return new ResponseEntity<>("Nenhum dado encontrado para o usuario", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(alunounico, HttpStatus.OK);
        }
    }

    @PostMapping("/alunos")
    public ResponseEntity<?> cadastrarAluno(@RequestBody AlunoDTO request) {

        if (request.getNome().length() > 6){
            ArrayList<String> teste = new ArrayList<>();
            teste.add("Entrada deve ter menos de 6 caracteres");
            teste.add("Olá");
            log.info("Sente a vibe, sente a vibe desse som");
            return new ResponseEntity<>(teste, HttpStatus.BAD_REQUEST);
        }
        else {
            Aluno aluno1 = new Aluno();
            aluno1.setNome(request.getNome());
            Aluno resp = aluno.save(aluno1);
            return new ResponseEntity<>(resp, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/alunos")
    public ResponseEntity<?> deletarTodos() {
        aluno.deleteAll();
        return new ResponseEntity<>("Completo", HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/alunos/{id}")
    public String deletarAluno(@PathVariable("id") Integer id) {
        Optional<Aluno> aluno1 = aluno.findById(id);

        if (aluno1.isPresent()) {
            aluno.deleteById(id);
            return "Dados de usuario excluidos";
        } else {
            return "Dados de usuário inexistentes";
        }
    }

    @PutMapping("/alunos/{id}")
    public ResponseEntity<Aluno> alterarAluno(@PathVariable("id") Integer id, @RequestBody AlunoDTO request) {
        Optional<Aluno> aluno1 = aluno.findById(id);

        if (aluno1.isPresent()) {
            Aluno alunoPut = aluno1.get();
            alunoPut.setNome(request.getNome());
            return new ResponseEntity<>(aluno.save(alunoPut), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
