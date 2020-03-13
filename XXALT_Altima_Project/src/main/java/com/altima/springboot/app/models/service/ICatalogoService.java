package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioLookup;

public interface ICatalogoService {

	List<DisenioLookup> findAll();

	void save(DisenioLookup diseniolookup);

	void delete(Long id);

	DisenioLookup findOne(Long id);

}
