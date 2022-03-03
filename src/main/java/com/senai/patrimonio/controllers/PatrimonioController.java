package com.senai.patrimonio.controllers;

import com.senai.patrimonio.model.Funcionario;
import com.senai.patrimonio.model.Local;
import com.senai.patrimonio.model.Patrimonio;
import com.senai.patrimonio.payload.request.PatrimonioCreate;
import com.senai.patrimonio.respository.FuncionarioRepository;
import com.senai.patrimonio.respository.LocalRepository;
import com.senai.patrimonio.respository.PatrimonioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/patrimonio")
public class PatrimonioController {

    @Autowired
    PatrimonioRepository patrimonioRepository;

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    LocalRepository localRepository;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Patrimonio> allAccess() {
        return  patrimonioRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Patrimonio> create( @RequestBody PatrimonioCreate patrimonioCreate) {

        Long funcionarioId = patrimonioCreate.getFuncionarioId();
        Long localId = patrimonioCreate.getLocalId();

        Optional<Funcionario> funcionario = funcionarioRepository.findById(funcionarioId);
        if(!funcionario.isPresent()) {
            return ResponseEntity.status(400).build();
        }

        Optional<Local> local = localRepository.findById(localId);
        if(!local.isPresent()) {
            return ResponseEntity.status(400).build();
        }

        Patrimonio p = new Patrimonio();
        p.setDescricao(patrimonioCreate.getDescricao());
        p.setFuncionario(funcionario.get());
        p.setLocal(local.get());

        Patrimonio patrimonioRes = patrimonioRepository.save(p);

        return ResponseEntity.status(HttpStatus.CREATED).body(patrimonioRes);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Patrimonio> patrimonio = patrimonioRepository.findById(id);
        if(!patrimonio.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        patrimonioRepository.deleteById(id);

        return ResponseEntity.ok(null);
    }

}