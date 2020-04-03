package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.DisenioMaterialTela;
import com.altima.springboot.app.models.entity.DisenioPrenda;
import com.altima.springboot.app.models.entity.DisenioTela;
import com.altima.springboot.app.models.entity.DisenioTelaForro;
import com.altima.springboot.app.repository.DisenioTelaRepository;


@Service
public class DisenioTelaServiceImpl implements IDisenioTelaService {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private DisenioTelaRepository repository;
	@Override
	@Transactional(readOnly=true)
	public List<DisenioTela> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioTela>) repository.findAll();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object []> findAllBotones(Long id) {
		List<Object[]> re= em.createNativeQuery("SELECT material.id_material, material.nombre_material from alt_disenio_material as material, alt_disenio_lookup as lookup\r\n" + 
				"				where 1=1\r\n" + 
				"				and material.id_tipo_material= lookup.id_lookup\r\n" + 
				"				and lookup.nombre_lookup='boton'\r\n" + 
				"                and NOT EXISTS(\r\n" + 
				"                    select MT.id_material, material.nombre_material   from alt_disenio_material_tela as MT\r\n" + 
				"                    where MT.id_material=material.id_material\r\n" + 
				"                    and MT.id_tela="+id+"\r\n" + 
				"                )").getResultList();
		return re;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioLookup> findAllColores() {
		// TODO Auto-generated method stub
		return em.createQuery("from DisenioLookup where tipo_lookup = 'Color'").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioLookup> findAllFamilaComposicion() {
		// TODO Auto-generated method stub
		return em.createQuery("from DisenioLookup where tipo_lookup = 'Familia Composicion'").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioPrenda> findAllPrenda() {
		// TODO Auto-generated method stub
		return em.createQuery("from DisenioPrenda").getResultList();
	}
	
	
	
	@Override
	@Transactional
	public void save(DisenioTela diseniotela) {
		// TODO Auto-generated method stub
		repository.save(diseniotela);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public DisenioTela findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> ComposicionTelaMN(Long id) {
		
		
		List<Object[]> re= em.createNativeQuery("select alt_disenio_familia_composicion_tela.porcentaje_composicion ,"
				+ "alt_disenio_lookup.id_lookup ,alt_disenio_lookup.nombre_lookup "
				+ "from alt_disenio_familia_composicion_tela, alt_disenio_lookup "
				+ "where alt_disenio_lookup.id_lookup=alt_disenio_familia_composicion_tela.id_composicion "
				+ "and alt_disenio_familia_composicion_tela.id_tela="+id).getResultList();
		return re;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> ComposicionForroMN(Long id) {
		
		System.out.println("Hola erik soy la consulta forro d mm");
		List<Object[]> re= em.createNativeQuery("Select alt_disenio_familia_composicion_forro.composicion, alt_disenio_lookup.id_lookup, alt_disenio_lookup.nombre_lookup "
				+ "from alt_disenio_familia_composicion_forro , alt_disenio_lookup "
				+ "where alt_disenio_lookup.id_lookup=alt_disenio_familia_composicion_forro.id_familia_composicion "
				+ "and alt_disenio_familia_composicion_forro.id_forro="+id).getResultList();
		return re;
	}


	@Override
	@Transactional
	public void borrarBotonesTela(Long id) {
		// TODO Auto-generated method stub
		Query query = em.createNativeQuery("DELETE FROM alt_disenio_material_tela WHERE alt_disenio_material_tela.id_tela="+id);
		
		query.executeUpdate();
		
	}


	@Override
	@Transactional
	public void borrarForroTela(Long id) {
		// TODO Auto-generated method stub
		Query query = em.createNativeQuery("DELETE FROM alt_disenio_tela_forro WHERE alt_disenio_tela_forro.id_tela="+id);
		
		query.executeUpdate();
		
	}


	@Override
	@Transactional
	public void borrarComposicionTela(Long id) {
		// TODO Auto-generated method stub
		Query query = em.createNativeQuery("DELETE FROM alt_disenio_familia_composicion_tela WHERE alt_disenio_familia_composicion_tela.id_tela="+id);
		
		query.executeUpdate();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object []> BonotesSeleccionados(Long id) {
		List<Object[]> re= em.createNativeQuery("select alt_disenio_material_tela.id_material, alt_disenio_material.nombre_material\r\n" + 
				"from alt_disenio_material_tela,alt_disenio_material\r\n" + 
				"where 1=1\r\n" + 
				"and alt_disenio_material_tela.id_material=alt_disenio_material.id_material\r\n" + 
				"and alt_disenio_material_tela.id_tela="+id).getResultList();
		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object []> ForrosSeleccionados(Long id) {
		// TODO Auto-generated method stub
		
		
		List<Object[]> re= em.createNativeQuery("select alt_disenio_tela_forro.id_forro, alt_disenio_forro.nombre_forro from \r\n" + 
				"		alt_disenio_tela_forro,alt_disenio_forro\r\n" + 
				"		where 1=1\r\n" + 
				"		and alt_disenio_tela_forro.id_forro=alt_disenio_forro.id_forro\r\n" + 
				"		and alt_disenio_tela_forro.id_tela="+id).getResultList();
		return re;
	}
}
