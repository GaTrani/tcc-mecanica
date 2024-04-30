package site.SpringWeb.servicos;

import java.util.List;

import org.springframework.stereotype.Service;

import site.SpringWeb.modelos.Fornecedor;
import site.SpringWeb.repositorio.ClientesRepo;
import site.SpringWeb.repositorio.FornecedoresRepo;

@Service
public class FornecedorService {

    private static FornecedoresRepo fornecedoresRepo = null;
    public static ClientesRepo clientesRepo;

    public FornecedorService(FornecedoresRepo fornecedoresRepo) {
        FornecedorService.fornecedoresRepo = fornecedoresRepo;
    }

    public static List<Fornecedor> buscarTodos() {
        return (List<Fornecedor>) fornecedoresRepo.findAll(); // Supondo que você esteja usando Spring Data JPA e tenha um repositório FornecedorRepository
    }
}
