package site.SpringWeb.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import site.SpringWeb.modelos.Fornecedor;

public interface FornecedoresRepo extends CrudRepository<Fornecedor, Integer> {

  @Query(value = "select CASE WHEN count(1) > 0 THEN 'true' ELSE 'false' END from fornecedores where id = :id", nativeQuery = true)
  public boolean exist(int id);

  // @Query(value="select * from fornecedores where email = :email and senha =
  // :senha", nativeQuery = true)
  // public Fornecedor login(String email, String senha);

  // @Query(value="select * from fornecedores where nome like %:nome% or email
  // like
  // %:email%", nativeQuery = true)
  // public ArrayList<Fornecedor> findAllByNomeEmail(@Param("nome") String nome,
  // @Param("email") String email);
}
