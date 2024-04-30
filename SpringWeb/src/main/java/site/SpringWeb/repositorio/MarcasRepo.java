package site.SpringWeb.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;

import site.SpringWeb.modelos.MarcaCarro;

public interface MarcasRepo extends JpaRepository<MarcaCarro, Long> {

  @SuppressWarnings("null")
  @Query(value = "select CASE WHEN count(1) > 0 THEN true ELSE false END from MarcaCarro where id = :id")
  public boolean existsById(Long id);
}

