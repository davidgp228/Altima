package com.altima.springboot.app.controller;

import java.net.MalformedURLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.altima.springboot.app.models.entity.DisenioPrenda;
import com.altima.springboot.app.models.service.IDisenioFamiliaPrendaService;
import com.altima.springboot.app.models.service.IDisenioImagenPrendaService;
import com.altima.springboot.app.models.service.IDisenioLookupService;
import com.altima.springboot.app.models.service.IDisenioMaterialService;
import com.altima.springboot.app.models.service.IDisenioPrendaMarcadorService;
import com.altima.springboot.app.models.service.IDisenioPrendaService;
import com.altima.springboot.app.models.service.IDisenioRutaService;
import com.altima.springboot.app.models.service.IUploadService;

@Controller
public class PrendasController {
	@Autowired
	IDisenioPrendaService disenioPrendaService;
	@Autowired
	IDisenioFamiliaPrendaService disenioFamiliaPrendaService;
	@Autowired
	IDisenioMaterialService disenioMaterialService;
	@Autowired
	IDisenioRutaService disenioRutaService;
	@Autowired
	IDisenioImagenPrendaService disenioImagenPrendaService;
	@Autowired
	IUploadService uFileService;
	@Autowired
	IDisenioLookupService disenioLookupService;
	@Autowired
	private IDisenioPrendaMarcadorService disenioPrendaMarcadorService;
	
	@GetMapping("prendas")
	public String listClothes(Model model, Map<String, Object> m) throws InterruptedException {
		model.addAttribute("prendas", disenioPrendaService.findAll());
		model.addAttribute("tipos", disenioMaterialService.findAllFamiliaPrenda());
		
		return "prendas";
	}
	
	@GetMapping("detalle-prenda")
	public String infoClothes() {
		
		return "detalle-prenda";
	}
	
	@GetMapping("agregar-prenda")
	public String addClothes(Model model, Map<String, Object> m) {
		DisenioPrenda prenda = new DisenioPrenda();
		
		model.addAttribute("familias", disenioMaterialService.findAllFamiliaPrenda());
		model.addAttribute("materiales", disenioMaterialService.findAllForCreate());
		model.addAttribute("patronajes", disenioMaterialService.findLookUps());
		model.addAttribute("rutas", disenioRutaService.findAll());
		model.addAttribute("marcadores", disenioLookupService.findByTipoLookup("Marcador"));
		model.addAttribute("imagenesPrenda", disenioImagenPrendaService.findByPrenda(0L));
		m.put("prenda", prenda);
		m.put("accion", "agregar");
		return "agregar-confirmar-prenda";
	}
	
	@RequestMapping(value = "/confirmar-prenda/{id}")
	public String confirmar(@PathVariable(value = "id") Long id, Model model, Map<String, Object> m) {
		DisenioPrenda disenio = new DisenioPrenda();
		DisenioPrenda prenda = disenioPrendaService.findOne(id);
		model.addAttribute("marcadores", disenioLookupService.findByTipoLookup("Marcador"));
		model.addAttribute("familias", disenioMaterialService.findAllFamiliaPrenda());
		model.addAttribute("materiales", disenioMaterialService.findAllForCreate());
		model.addAttribute("patronajes", disenioMaterialService.findLookUps());
		model.addAttribute("prenda", prenda);
		m.put("disenio", disenio);
		m.put("accion", "confirmar");
		return "agregar-confirmar-prenda";
	}
	
	@RequestMapping(value = "/editar-prenda/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model, Map<String, Object> m) {
		DisenioPrenda disenio = new DisenioPrenda();
		DisenioPrenda prenda = disenioPrendaService.findOne(id);
		
		model.addAttribute("marcadores", disenioLookupService.findByTipoLookup("Marcador"));
		model.addAttribute("prendasmarcadores", disenioPrendaMarcadorService.findByIdPrenda(id));
		model.addAttribute("familias", disenioMaterialService.findAllFamiliaPrenda());
		model.addAttribute("materiales", disenioMaterialService.findAllForCreate());
		model.addAttribute("materialesPrenda", disenioMaterialService.findAllFromPrenda(id));
		model.addAttribute("patronajes", disenioMaterialService.findLookUps());
		model.addAttribute("patronajesPrenda", disenioMaterialService.findAllPatronajeFromPrenda(id));
		model.addAttribute("imagenesPrenda", disenioImagenPrendaService.findByPrenda(id));
		model.addAttribute("prenda", prenda);
		m.put("accion", "editar");
		m.put("disenio", disenio);
		
		return "agregar-confirmar-prenda";
	}
	
	@GetMapping(value = "/uploads/prendas/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {
		
		Resource recurso = null;
		try {
			recurso = uFileService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}
	
}
