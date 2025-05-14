package br.faccat.sistdist.springbootrestapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.faccat.sistdist.springbootrestapi.entity.Aposta;
import br.faccat.sistdist.springbootrestapi.repository.ApostaRepository;

@RestController
public class ApostaController {
    @Autowired
    private ApostaRepository _apostaRepository;

    // Endpoint para listar todas as apostas
    @RequestMapping(value = "/aposta", method = RequestMethod.GET)
    public List<Aposta> Get() {
        return _apostaRepository.findAll();
    }

    // Endpoint para obter uma aposta por ID
    @RequestMapping(value = "/aposta/{id}", method = RequestMethod.GET)
    public ResponseEntity<Aposta> GetById(@PathVariable(value = "id") long id) {
        Optional<Aposta> aposta = _apostaRepository.findById(id);
        if (aposta.isPresent())
            return new ResponseEntity<Aposta>(aposta.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Endpoint para registrar uma nova aposta
    @RequestMapping(value = "/aposta", method = RequestMethod.POST)
    public Aposta Post(@RequestBody Aposta aposta) {
        return _apostaRepository.save(aposta);
    }

    // Endpoint para atualizar uma aposta existente
    @RequestMapping(value = "/aposta/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Aposta> Put(@PathVariable(value = "id") long id, @RequestBody Aposta newAposta) {
        Optional<Aposta> oldAposta = _apostaRepository.findById(id);
        if (oldAposta.isPresent()) {
            Aposta aposta = oldAposta.get();
            aposta.setNumero(newAposta.getNumero());
            aposta.setValor(newAposta.getValor());
            _apostaRepository.save(aposta);
            return new ResponseEntity<Aposta>(aposta, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Endpoint para excluir uma aposta
    @RequestMapping(value = "/aposta/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id) {
        Optional<Aposta> aposta = _apostaRepository.findById(id);
        if (aposta.isPresent()) {
            _apostaRepository.delete(aposta.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
