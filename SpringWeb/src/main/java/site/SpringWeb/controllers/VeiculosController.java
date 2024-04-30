package site.SpringWeb.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//simport org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import site.SpringWeb.modelos.Cliente;
import site.SpringWeb.modelos.MarcaCarro;
import site.SpringWeb.modelos.ModeloCarro;
import site.SpringWeb.modelos.Veiculo;
//import site.SpringWeb.repositorio.ModelosRepo;
import site.SpringWeb.repositorio.VeiculosRepo;
import site.SpringWeb.servicos.ClienteService;
import site.SpringWeb.servicos.MarcaCarroService;
import site.SpringWeb.servicos.ModeloCarroService;
//import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VeiculosController {

    @Autowired
    private VeiculosRepo repo;

    // @Autowired
    // private VeiculosRepo veiculosRepo;

    @GetMapping("/veiculos")
    public String index(Model model) {
        List<Veiculo> veiculos = (List<Veiculo>) repo.findAll();
        model.addAttribute("veiculos", veiculos);
        return "veiculos/index";
    }

    @GetMapping("/veiculos/novo")
    public String exibirFormularioNovoVeiculo(Model model) {
        List<Cliente> listaClientes = ClienteService.buscarTodos();
        List<MarcaCarro> listaMarcasCarro = MarcaCarroService.buscarTodos();
        List<ModeloCarro> listaModelosCarro = ModeloCarroService.buscarTodos();
        model.addAttribute("listaClientes", listaClientes);
        model.addAttribute("listaMarcasCarro", listaMarcasCarro);
        model.addAttribute("listaModelosCarro", listaModelosCarro);
        return "veiculos/novo";
    }

    @PostMapping("/veiculos/criar")
    public String criar(Veiculo veiculo, RedirectAttributes redirectAttributes) {
        try {
            repo.save(veiculo);
            return "redirect:/veiculos";
        } catch (DataIntegrityViolationException e) {
            // Se ocorrer uma violação de restrição de integridade, significa que a placa já
            // existe
            redirectAttributes.addFlashAttribute("erro", "A placa do veículo já está em uso.");
            return "redirect:/veiculos/novo"; // Redireciona de volta para a página de criação de veículos
        }
    }

    public String criar(@RequestParam("cliente") String clienteId,
            @RequestParam("marca") Long marcaId,
            @RequestParam("modelo") Long modeloId,
            @RequestParam("placa") String placa,
            @RequestParam("km") int km,
            RedirectAttributes redirectAttributes) {
        try {
            // Busca o cliente pelo ID
            // Cliente cliente = ClienteService.buscarPorId(clienteId);
            // Busca a marca pelo ID
            MarcaCarro marca = MarcaCarroService.buscarPorId(marcaId);
            // Busca o modelo pelo ID
            ModeloCarro modelo = ModeloCarroService.buscarPorId(modeloId);

            // Verifica se o modelo foi encontrado
            if (modelo != null) {
                // Cria o objeto Veiculo e associa o cliente, marca e modelo
                Veiculo veiculo = new Veiculo();
                // veiculo.setCliente(cliente.getNome());
                veiculo.setMarca(marca.getNome());
                veiculo.setModelo(modelo.getModelo());
                veiculo.setPlaca(placa);
                veiculo.setKm(km);

                // Salva o veículo no banco de dados
                repo.save(veiculo);

                return "redirect:/veiculos";
            } else {
                // Se o modelo não foi encontrado, redireciona de volta para a página de criação
                // de veículos
                redirectAttributes.addFlashAttribute("erro", "Modelo de carro não encontrado.");
                return "redirect:/veiculos/novo";
            }
        } catch (DataIntegrityViolationException e) {
            // Se ocorrer uma violação de restrição de integridade, significa que a placa já
            // existe
            redirectAttributes.addFlashAttribute("erro", "A placa do veículo já está em uso.");
            return "redirect:/veiculos/novo"; // Redireciona de volta para a página de criação de veículos
        }
    }

    @GetMapping("/veiculos/{id}")
    public String busca(@PathVariable int id, Model model) {
        Optional<Veiculo> veiculo = repo.findById((long) id);
        try {
            model.addAttribute("veiculo", veiculo.get());
        } catch (Exception err) {
            return "redirect:/veiculos";
        }

        return "veiculos/editar";
    }

    @PostMapping("/veiculos/{id}/atualizar")
    public String atualizar(@PathVariable int id, Veiculo veiculo) {
        if (!repo.existsById((long) id)) {
            return "redirect:/veiculos";
        }

        repo.save(veiculo);

        return "redirect:/veiculos";
    }

    @GetMapping("/veiculos/{id}/excluir")
    public String excluir(@PathVariable int id) {
        repo.deleteById((long) id);
        return "redirect:/veiculos";
    }

    // @Autowired
    // private Veiculo veiculoService;

    // @GetMapping("/marcas")
    // public ResponseEntity<List<MarcaCarro>> obterMarcasCarro() {
    // List<MarcaCarro> marcas = veiculoService.obterMarcasCarro();
    // return ResponseEntity.ok(marcas);
    // }

    @GetMapping("/buscarPorMarca")
    @ResponseBody
    public static List<ModeloCarro> buscarPorMarca(Long marcaId) {
        // Busca a marca pelo ID
        MarcaCarro marca = MarcaCarroService.buscarPorId(marcaId);
        if (marca != null) {
            // Se a marca for encontrada, retorna os modelos associados a essa marca
            return ModeloCarroService.buscarPorMarca();
        } else {
            // Se a marca não for encontrada, retorna uma lista vazia
            return Collections.emptyList();
        }
    }

}
