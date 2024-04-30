package site.SpringWeb.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

//import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;
import site.SpringWeb.repositorio.ModelosRepo;
import site.SpringWeb.modelos.ModeloCarro;

@Service
public class ModeloCarroService {

    private static ModelosRepo modelosRepo;

    public ModeloCarroService(ModelosRepo modelosRepo) {
        ModeloCarroService.modelosRepo = modelosRepo;
    }

    public static List<ModeloCarro> buscarTodos() {
        return (List<ModeloCarro>) modelosRepo.findAll();
    }

    public static List<ModeloCarro> buscarPorMarca() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorMarca'");
    }

    public static Optional<ModeloCarro> buscarPorMarca(Long marcaId) {
        return modelosRepo.buscaPorId(marcaId);
    }

    public static ModeloCarro buscarPorId(Long id) {
        return modelosRepo.buscaPorId(id).orElse(null);
    }
}