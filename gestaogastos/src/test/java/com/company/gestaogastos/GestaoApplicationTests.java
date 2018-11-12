package com.company.gestaogastos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.company.gestaogastos.controllers.CategoriaController;
import com.company.gestaogastos.controllers.GastoController;
import com.company.gestaogastos.domain.entity.Categoria;
import com.company.gestaogastos.domain.entity.Gasto;
import com.company.gestaogastos.services.CategoriaService;
import com.company.gestaogastos.services.GastoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GestaoApplicationTests {
	
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();

    @Autowired
    private GastoController gastoController;

    @Autowired
    private CategoriaController categoriaController;
    
	@Autowired
	GastoService gastoService;

	@Autowired
	CategoriaService categoriaService;

    @Test
    public void acontrollerInitializedCorrectly() {
        assertThat(categoriaController).isNotNull();
        assertThat(gastoController).isNotNull();
        
    }

	@Test
	public void aloadGastosEcategorizacaoAutomaticaGastos() {
		long deltaTempo = 8*60*60*1000;

		Gasto gasto = new Gasto();
		gasto.setCodigousuario(1);
		gasto.setData(new Timestamp(System.currentTimeMillis()));
		gasto.setValor(new BigDecimal("10.5"));
		gasto.setId(2L);
		gasto.setDescricao("UPDATE GASTO 111111");
		gasto.setCategoria(new Categoria(1L, "Categoria 01"));
		
		gasto.setDescricao("gasto 1");
		gasto.setCategoria(new Categoria(null, "Categoria 01"));
		gasto.setData(new Timestamp(System.currentTimeMillis()));
		gasto.setValor(new BigDecimal("10.5"));
		Gasto gastoBase = gastoService.createGasto(gasto );
		assertTrue(gastoBase != null && gastoBase.getId() != null);
		// ----------
        gasto.setDescricao("gasto 2");
        gasto.setCategoria(new Categoria(null, "Categoria 04"));
        gasto.setData(new Timestamp(System.currentTimeMillis() + (deltaTempo)));
		gastoBase = gastoService.createGasto(gasto );
		assertTrue(gastoBase != null && gastoBase.getId() != null);
		// ----------
        gasto.setDescricao("gasto 3");
        gasto.setCategoria(new Categoria(null, "Categoria 01"));
        gasto.setData(new Timestamp(System.currentTimeMillis() + (2 * deltaTempo)));
		gastoBase = gastoService.createGasto(gasto );
		assertTrue(gastoBase != null && gastoBase.getId() != null);
		// ----------
        gasto.setDescricao("gasto 4");
        gasto.setCategoria(null);
        gasto.setData(new Timestamp(System.currentTimeMillis() + (18 * deltaTempo)));
		gastoBase = gastoService.createGasto(gasto );
		assertTrue(gastoBase != null && gastoBase.getId() != null);
		// ----------
        gasto.setDescricao("gasto 1");
        gasto.setData(new Timestamp(System.currentTimeMillis() + (4 * deltaTempo)));
		gastoBase = gastoService.createGasto(gasto );
		assertTrue(gastoBase != null && gastoBase.getId() != null);
		// ----------
        gasto.setDescricao("gasto 2");
        gasto.setData(new Timestamp(System.currentTimeMillis() + (5 * deltaTempo)));
		gastoBase = gastoService.createGasto(gasto );
		assertTrue(gastoBase != null && gastoBase.getId() != null);
		// ----------
        gasto.setDescricao("gasto 2");
        gasto.setData(new Timestamp(System.currentTimeMillis() + (6 * deltaTempo)));
		gastoBase = gastoService.createGasto(gasto );
		assertTrue(gastoBase != null && gastoBase.getId() != null);
		// ----------
        gasto.setDescricao("gasto 8");
        gasto.setData(new Timestamp(System.currentTimeMillis() + (7 * deltaTempo)));
		gastoBase = gastoService.createGasto(gasto );
		assertTrue(gastoBase != null && gastoBase.getId() != null);
		// ----------
        gasto.setDescricao("gasto 3");
        gasto.setData(new Timestamp(System.currentTimeMillis() + (8 * deltaTempo)));
		gastoBase = gastoService.createGasto(gasto );
		assertTrue(gastoBase != null && gastoBase.getId() != null);
		// ----------
        gasto.setDescricao("gasto 10");
        gasto.setData(new Timestamp(System.currentTimeMillis() + (9 * deltaTempo)));
		gastoBase = gastoService.createGasto(gasto );
		assertTrue(gastoBase != null && gastoBase.getId() != null);
		// ----------
		gasto.setDescricao("despesas 1");
        gasto.setCodigousuario(2);
		gastoBase = gastoService.createGasto(gasto );
		System.out.println("=======" + gastoBase.toString());
		assertTrue(gastoBase != null && gastoBase.getId() != null);

	}
	
	@Test
	public void bcategorizacaoGasto() {
		Gasto gasto = new Gasto();
		gasto.setCodigousuario(1);
		gasto.setData(new Timestamp(System.currentTimeMillis()));
		gasto.setValor(new BigDecimal("10.5"));
		gasto.setId(2L);
		gasto.setDescricao("UPDATE GASTO 111111");
		gasto.setCategoria(new Categoria(1L, "Categoria 01"));
		Gasto aa = gastoService.updateGasto(gasto, gasto.getId());
		
		assertTrue(aa.getCategoria().getId().equals(1L));
	}

	@Test
	public void cinserirCategoria() {
		Categoria categoria = new Categoria();
		categoria.setNome("Categoria 01");
		categoriaService.createCategoria(categoria );
		categoria.setNome("Categoria 02");
		categoriaService.createCategoria(categoria );
		categoria.setNome("Categoria 03");
		categoriaService.createCategoria(categoria );
		assertTrue(categoria.getId() != null);
	}

	@Test
	public void dcategorizacaoGasto() {
		Gasto gasto = new Gasto();
		gasto.setCodigousuario(1);
		gasto.setData(new Timestamp(System.currentTimeMillis()));
		gasto.setValor(new BigDecimal("10.5"));
		gasto.setId(2L);
		gasto.setDescricao("UPDATE GASTO 111111");
		gasto.setCategoria(new Categoria(1L, "Categoria 01"));
		
		gastoService.updateGasto(gasto, gasto.getId());
		
		assertTrue(gasto.getCategoria().getNome().equals("Categoria 01"));
	}

	@Test
	public void elistagemGastos() {
		List<Gasto> gastos = gastoService.retrieveAllGastos();
		
		assertTrue(gastos.size() > 0);
	}

	@Test
	public void flistagemGastosDiaEspecifico() {
		Integer codigousuario = 1;		
		String dateFilter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
		Page<Gasto> gastos = gastoService.retrieveGastoByUserDate(codigousuario, dateFilter);
		
		assertTrue(gastos.getSize() > 0);
	}

	@Test
	public void gsugestaoCategoria() {
		String nomeCategoria = "GORia 01";
		Page<Categoria> categorias = categoriaService.retrieveCategoria2(nomeCategoria );
		
		assertTrue(categorias.getSize() > 0);
	}
}