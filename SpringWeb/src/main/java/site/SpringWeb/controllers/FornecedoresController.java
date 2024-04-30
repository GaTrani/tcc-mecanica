package site.SpringWeb.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import site.SpringWeb.modelos.Fornecedor;
import site.SpringWeb.repositorio.FornecedoresRepo;

@Controller
public class FornecedoresController {

    @Autowired
    private FornecedoresRepo repo;

    @GetMapping("/fornecedores")
    public String index(Model model) {
        List<Fornecedor> fornecedores = (List<Fornecedor>) repo.findAll();
        model.addAttribute("fornecedores", fornecedores);
        return "fornecedores/index";
    }

    @GetMapping("/fornecedores/novo")
    public String novo() {
        return "fornecedores/novo";
    }

    @PostMapping("/fornecedores/criar")
    public String criar(Fornecedor fornecedor) {
        repo.save(fornecedor);
        return "redirect:/fornecedores";
    }

    @GetMapping("/fornecedores/{id}")
    public String busca(@PathVariable int id, Model model) {
        Optional<Fornecedor> fornecedor = repo.findById(id);
        try {
            model.addAttribute("fornecedor", fornecedor.get());
        } catch (Exception err) {
            return "redirect:/fornecedores";
        }

        return "fornecedores/editar";
    }

    @PostMapping("/fornecedores/{id}/atualizar")
    public String atualizar(@PathVariable int id, Fornecedor fornecedor) {
        if (!repo.existsById(id)) {
            return "redirect:/fornecedores";
        }

        repo.save(fornecedor);

        return "redirect:/fornecedores";
    }

    @GetMapping("/fornecedores/{id}/excluir")
    public String excluir(@PathVariable int id) {
        repo.deleteById(id);
        return "redirect:/fornecedores";
    }
}
