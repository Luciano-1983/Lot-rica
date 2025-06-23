package br.faccat.sistdist.springbootrestapi.controller;

import java.util.List;
import java.util.Optional;
import br.faccat.sistdist.springbootrestapi.entity.Resultado; // Add this import if Resultado is an existing class in your project
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import br.faccat.sistdist.springbootrestapi.entity.Aposta;
import br.faccat.sistdist.springbootrestapi.repository.ApostaRepository;
import br.faccat.sistdist.springbootrestapi.repository.ResultadoRepository;

@RestController
// @RequestMapping("/aposta")
public class ApostaController {

    @Autowired
    private ApostaRepository _apostaRepository;

    @Autowired
    private ResultadoRepository resultadoRepository; // Add this line to declare and inject resultadoRepository

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

    // Endpoint para realizar o sorteio
    @PostMapping("/aposta/sorteio")
    public ResponseEntity<Resultado> realizarSorteio() {
        // Lógica para realizar o sorteio
        Random random = new Random();
        int numeroSorteado = random.nextInt(100) + 1; // Sorteio de um número entre 1 e 100

        // Criar o objeto Resultado para armazenar o número sorteado e os acertadores
        Resultado resultado = new Resultado();
        resultado.setNumeroSorteado(numeroSorteado);

        // Validar as apostas e encontrar os acertadores
        List<Aposta> apostas = _apostaRepository.findAll();
        apostas.stream()
                .filter(aposta -> aposta.getNumero() == numeroSorteado)
                .forEach(aposta -> resultado.getAcertadores().add(aposta));

        // Salvar o resultado no banco de dados
        resultadoRepository.save(resultado);

        // Retornar o resultado do sorteio
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    // Endpoint para listar numeros sorteio
    @RequestMapping(value = "/aposta/sorteio", method = RequestMethod.GET)
    public ResponseEntity<List<Resultado>> listarResultados() {
        List<Resultado> resultados = resultadoRepository.findAll();
        if (resultados.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(resultados, HttpStatus.OK);
    }

    // Endpoint para listar ganhadores
    @RequestMapping(value = "/aposta/sorteio/{id}/ganhadores", method = RequestMethod.GET)
    public ResponseEntity<List<Aposta>> listarGanhadores(@PathVariable(value = "id") Long id) {
        Optional<Resultado> resultado = resultadoRepository.findById(id);
        if (resultado.isPresent()) {
            List<Aposta> ganhadores = resultado.get().getAcertadores();
            if (ganhadores.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(ganhadores, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
