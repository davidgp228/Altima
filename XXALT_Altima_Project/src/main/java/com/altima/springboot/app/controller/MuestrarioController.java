package com.altima.springboot.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.DisenioMaterial;
import com.altima.springboot.app.models.entity.DiseñoPrendaImagen;
import com.altima.springboot.app.models.entity.ProduccionDetallePedido;
import com.altima.springboot.app.models.service.IInventarioService;
import com.altima.springboot.app.models.service.IUploadService;

@Controller
public class MuestrarioController {
	
	@Autowired
	private IInventarioService inventario;
	
	@Autowired
	private IUploadService UploadService;
	
	
	
	

	
	
	@GetMapping("/inventario")
	public String Inventario(Model model) {
		
		List<ProduccionDetallePedido> listInventario= inventario.listInventario();
		model.addAttribute("listInventario", listInventario);
		System.out.println("Si esta entrando al controller");	
		return "inventario";
	}
	
	
	
	
	
	
	@PostMapping("/find-edit/img")
	public String findEdit( Model model, @RequestParam(name = "idPrenda", required = false) Long idPrenda,
			@RequestParam(name = "idPrendaReal", required = false) Long idPrendaReal,
			@RequestParam(value="imagen", required=false) MultipartFile imagenInventario) {
		
		
		System.out.println("si entro al controller editar img");
		System.out.println("asi llego el id desde el controller"+ idPrenda );	   	    	 	    		    	    	 	    	    	  
	    	System.out.println("si entro al ifffffffff");	    		    	    		  	    	
	    	 DiseñoPrendaImagen prenda =  inventario.findOne(idPrenda);
	    	   
			if (!imagenInventario.isEmpty()){
				if ( prenda.getRutaPrenda() != null && prenda.getRutaPrenda().length() > 0) {
					UploadService.deleteInventario(prenda.getRutaPrenda());
				}
				String uniqueFilename = null;
				try {
					uniqueFilename = UploadService.copyInventario(imagenInventario);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				prenda.setRutaPrenda(uniqueFilename); 
			}
			
		   inventario.save(prenda);
		
	    	    
	 
	
	
	
					
	   return "redirect:/inventario";
		

	}
	
	
	
	

	
	
	
}
