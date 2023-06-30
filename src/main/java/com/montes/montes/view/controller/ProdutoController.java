package com.montes.montes.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.montes.montes.model.Produto;
import com.montes.montes.services.ProdutoService;
import com.montes.montes.shared.ProdutoDTO;
import com.montes.montes.view.model.ProdutoRequest;
import com.montes.montes.view.model.ProdutoResponse;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoService produtoService;
    
    @GetMapping
    public  ResponseEntity<List<ProdutoResponse>> obterTodos(){

        List<ProdutoDTO> produtos =  produtoService.obterTodos();
        ModelMapper modelMapper = new ModelMapper();

        List<ProdutoResponse> resposta = produtos.stream()
        .map(produtoDto -> modelMapper.map(produtoDto, ProdutoResponse.class))
        .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<ProdutoResponse> adicionar(@RequestBody ProdutoRequest produtoReq){
        ModelMapper modelMapper = new ModelMapper();

        ProdutoDTO produtoDto = modelMapper.map(produtoReq, ProdutoDTO.class);

        produtoDto = produtoService.adicionar(produtoDto);

        return new ResponseEntity<>(modelMapper.map(produtoDto, ProdutoResponse.class), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Produto>> obterPorId(@PathVariable Integer id){

        try {
            Optional<ProdutoDTO> produtoDto = produtoService.obterPorId(id);

            ProdutoResponse produto = new ModelMapper().map(produtoDto.get(), ProdutoResponse.class);

            Optional p = Optional.of(produto); 
            
            return new ResponseEntity<>(p, HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id){
        produtoService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar (@RequestBody ProdutoRequest produtoReq, @PathVariable Integer id){
        ModelMapper modelMapper = new ModelMapper();
        ProdutoDTO produtoDto = modelMapper.map(produtoReq, ProdutoDTO.class);

        produtoDto = produtoService.atualizar(id, produtoDto);
        
        return new ResponseEntity<>(
            modelMapper.map(produtoDto, ProdutoResponse.class), 
            HttpStatus.OK);
    }
}