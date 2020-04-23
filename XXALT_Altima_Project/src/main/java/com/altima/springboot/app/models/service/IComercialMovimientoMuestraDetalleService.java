package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialMovimientoMuestraDetalle;

public interface IComercialMovimientoMuestraDetalleService {
	
	void save(ComercialMovimientoMuestraDetalle muestraDetalle);
	
	public List<ComercialMovimientoMuestraDetalle> findAll ();
	
	public List<ComercialMovimientoMuestraDetalle> findAllbyMovimiento(Long id);

	List<ComercialMovimientoMuestraDetalle> findAllbyEstatus(Long id);

	ComercialMovimientoMuestraDetalle findOne(Long id);

	String ifExistCheckBox(Long id);

}
