package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.Rol;


public interface IRolService {
	public List<Rol> findAll();
	
	public Rol findOne(Long id_rol);

	public void save(Rol rol);

	public void delete(Long id_rol);

}