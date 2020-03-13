package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioCalidad;

public interface IDisenioCalidadService {

	List<DisenioCalidad> findAll();

	void save(DisenioCalidad diseniocalidad);

	void delete(Long id);

	DisenioCalidad findOne(Long id);

}
