package com.company.gestaogastos.services.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.company.gestaogastos.domain.GastoDomain;
import com.company.gestaogastos.domain.UsuarioDomain;
import com.company.gestaogastos.domain.dto.GastoDTO;
import com.company.gestaogastos.domain.entity.Gasto;
import com.company.gestaogastos.domain.repository.CategoriaRepository;
import com.company.gestaogastos.domain.repository.GastoRepository;
import com.company.gestaogastos.services.GastoService;

@Service
public class GastoServiceImpl implements GastoService {

	@Autowired
	private GastoRepository gastoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Override
	public Page<GastoDTO> retrieveGastos(Map<String, String> allRequestParams) {
		GastoDomain gasto = new GastoDomain(gastoRepository, categoriaRepository);
		if (allRequestParams.get("codusuario") != null) {
			Long usuarioId = Long.decode(allRequestParams.get("codusuario"));
			gasto.setUsuario(new UsuarioDomain(usuarioId, null));
		}
		if (allRequestParams.get("data") != null) {
			String data = allRequestParams.get("data");
		    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		    Date parsedDate;
			try {
				parsedDate = dateFormat.parse(data);
				gasto.setData(new Timestamp(parsedDate.getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Page<GastoDTO> gastos = gasto.convertPageGastoToPageGastoDTO(gasto.retrieveGastos(allRequestParams));
		return gastos;
	}

	@Override
	public GastoDTO retrieveGasto(long id) {
		GastoDomain gasto = new GastoDomain(gastoRepository, categoriaRepository);
		gasto.setId(id);
		Gasto gastoBase = gasto.retrieveGasto();
		return gasto.convertGastoToGastoDTO(gastoBase);
	}

	@Override
	public GastoDTO createGasto(GastoDTO gastoDTO) {
		GastoDomain gasto = new GastoDomain(gastoRepository, categoriaRepository);
		Gasto entity = gasto.toEntity(gastoDTO);
		Gasto gastoBase = gasto.createGasto(entity);
		return gasto.convertGastoToGastoDTO(gastoBase);
	}
    
	@Override
	public GastoDTO updateGasto(GastoDTO gastoDTO, long id) {
		GastoDomain gasto = new GastoDomain(gastoRepository, categoriaRepository);
		Gasto entity = gasto.toEntity(gastoDTO);
		Gasto gastoBase = gasto.updateGasto(entity);
		return gasto.convertGastoToGastoDTO(gastoBase);
	}

	@Override
	public void deleteGasto(long id) {
		GastoDomain gasto = new GastoDomain(gastoRepository, categoriaRepository);
		gasto.setId(id);
		gasto.deleteGasto();
	}
	
	@Override
	public boolean equals(Object o) {
		return true;
	}

}