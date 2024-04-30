package site.SpringWeb.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import site.SpringWeb.modelos.Produto;

public interface ProdutosRepo extends CrudRepository<Produto, Integer> {

  @Query(value = "select CASE WHEN count(1) > 0 THEN 'true' ELSE 'false' END from produtos where id = :id", nativeQuery = true)
  public boolean exist(int id);



}
