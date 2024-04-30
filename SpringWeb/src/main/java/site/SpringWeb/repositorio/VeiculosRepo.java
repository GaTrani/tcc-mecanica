package site.SpringWeb.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import site.SpringWeb.modelos.Veiculo;

public interface VeiculosRepo extends CrudRepository<Veiculo, Long> {

  @SuppressWarnings("null")
  @Query(value = "select CASE WHEN count(1) > 0 THEN true ELSE false END from Veiculo where id = :id")
  public boolean existsById(Long id);
}

