package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioPruebaEncogimientoLavado;

public interface IDisenioPruebaEncogimientoLavadoService {

	public List<DisenioPruebaEncogimientoLavado> findAll(DisenioPruebaEncogimientoLavado pruebaEncogimientoLavado);
	
	public void save (DisenioPruebaEncogimientoLavado pruebaEncogimientoLavado);	
	
	
	
}