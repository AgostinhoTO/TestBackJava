package com.company.gestaogastos.domain.repository;
import java.sql.Timestamp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.company.gestaogastos.domain.entity.Gasto;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long>{
	Page<Gasto> findByDescricao(String descricao,
    		Pageable pageRequest);
	
	@Query("SELECT g FROM Gasto g")
	Page<Gasto> findAllGastos(Pageable pageRequest);
	
	Page<Gasto> findByUsuarioIdOrderByDataDesc(Long usuario_id, Pageable pageRequest);
	
	@Query("SELECT g FROM Gasto g WHERE g.usuario.id = :codigousuario AND g.data >= :dataInferior AND g.data <= :dataSuperior order by data desc")
    public Page<Gasto> findByCodigousuarioOrderByDataDesc(
	    		@Param("codigousuario") Long codigousuario,
	    		@Param("dataInferior") Timestamp dataInferior,
	    		@Param("dataSuperior") Timestamp dataSuperior,
	    		Pageable pageRequest);
	
	@Query("SELECT g FROM Gasto g WHERE g.usuario.id = :codigousuario AND descricao = :descricao AND categoria_id is not null order by descricao ASC")
    public Page<Gasto> findByDescricaoCategoria(
    			@Param("codigousuario") Long codigousuario,
	    		@Param("descricao") String descricao,
	    		Pageable pageRequest);
	
	@Query("SELECT g FROM Gasto g, Categoria c WHERE g.categoria.id=c.id AND g.usuario.id = :codigousuario AND c.nome = :nome AND categoria_id is not null order by descricao ASC")
    public Page<Gasto> findByNomeCategoria(
    			@Param("codigousuario") Long codigousuario,
	    		@Param("nome") String nome,
	    		Pageable pageRequest);

}
