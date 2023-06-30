package com.montes.montes.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.montes.montes.model.Produto;
import com.montes.montes.model.exception.ResourceNotFoundException;
import com.montes.montes.repository.ProdutoRepository;
import com.montes.montes.shared.ProdutoDTO;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Metodo para retornar uma lista de todos os produtos.
     * @return  lista de produtos.
     */
    public List<ProdutoDTO> obterTodos (){
        // Aqui iria a regra de negócio ...
        List<Produto> produtos = produtoRepository.findAll();
        
        return produtos.stream()
        .map(produto -> new ModelMapper().map(produto, ProdutoDTO.class))
        .collect(Collectors.toList());
    }

     /**
     * Metodo que retorna o produto encontrado pelo seu id.
     * @param id do produto que será localizado.
     * @return um produto caso seja encontrado.
     */
    public Optional<ProdutoDTO> obterPorId(Integer id){
        // Obtendo optional de produto pelo ID
        Optional<Produto> produto = produtoRepository.findById(id);

        //Se não encontrar lança exception
        if (produto.isEmpty()) {
            throw new ResourceNotFoundException("Produto com id " + " não encontrado");
        }
        // Convertendo o optional produto para um produtoDTO
        ProdutoDTO dto =  new ModelMapper().map(produto.get(), ProdutoDTO.class);
        // Criando e retornando um optional de produtoDTO
        return Optional.of(dto); 

    }
    
    /**
     * Metodo para adicionar produto na lista
     * @param produto que será adicionado.
     * @return Retorna o produto que foi adicionado na lista.
     */
    public ProdutoDTO adicionar(ProdutoDTO produtoDto){      
        // Aqui iria a regra de negócio ...  
        produtoDto.setId(null);

        //Criar o Objeto de mapeamento
        ModelMapper mapper = new ModelMapper();
        // Converter o nosso produtodto em um produto
        Produto produto = mapper.map(produtoDto,Produto.class);
        //Salvando no banco
        produto = produtoRepository.save(produto);
        //Setando o id que estava faltando no dto, pois, retornamos um dto e não o produto em sí.
        produtoDto.setId(produto.getId());

        return produtoDto;
    }

    /**
     * Metodo para deletar o produto por id
     * @param id do produto a ser deletado
     */
    public void deletar (Integer id){

        Optional<Produto> produto = produtoRepository.findById(id);

        //Verificar se o produto existe;
        if (produto.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possível encontar o produto com o id: "+ id +" na base de dados");            
        }
        produtoRepository.deleteById(id);
    }

    /**
     * Metodo para atualizar o produto. 
     * @param produto a ser atualizado
     * @return retorna o produto com atualização realizada.
     */
    public ProdutoDTO atualizar (Integer id, ProdutoDTO produtoDto){
    // Aqui iria a regra de negócio ...
        
    // passar o id para o produtoDTO
        produtoDto.setId(id);
    // Criar um objeto de mapeamento
        ModelMapper modelMapper = new ModelMapper();
    // Preciso converter o DTO em um produto
        Produto produto = modelMapper.map(produtoDto, Produto.class);
    //Atualizar o produto no banco de dados
        produtoRepository.save(produto);
    // retornar o produtoDTO atualizado
    return produtoDto;
    }
}
