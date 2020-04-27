package com.altima.springboot.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.altima.springboot.app.models.entity.DisenioCalidad;
import com.altima.springboot.app.models.entity.DisenioPruebaEncogimientoLavado;
import com.altima.springboot.app.models.entity.DisenioPruebaLavadoContaminacionCostura;
import com.altima.springboot.app.models.service.IDisenioCalidadService;
import com.altima.springboot.app.models.service.IDisenioLookupService;
import com.altima.springboot.app.models.service.IDisenioMaterialService;
import com.altima.springboot.app.models.service.IDisenioPruebaEncogimientoLavadoService;
import com.altima.springboot.app.models.service.IDisenioPruebaLavadoContaminacionCosturaService;

@Controller
public class CalidadController {
	
	@Autowired
	IDisenioCalidadService disenioCalidad;
	@Autowired
	IDisenioPruebaEncogimientoLavadoService pruebaEncogiLavado;
	@Autowired
	IDisenioPruebaLavadoContaminacionCosturaService pruebaContaCostura;
	@Autowired
	IDisenioLookupService disenioLookup;
	@Autowired
	IDisenioMaterialService materialService;

	private String tipo(String tipo){
		String tipoint="tipo";
		if(tipo.equals("tela")){
			tipoint="1";
		}
		else if(tipo.equals("forro")){
			tipoint="3";
		}
		return tipoint;

	}
	
	@GetMapping("/calidad")
	public String listCalidad(Model model) {
		// si se ocupa este metodo
		model.addAttribute("listCalidades",disenioCalidad.findAllWithIdTextTela());
		return "calidad";
	}
	
	
	@GetMapping("calidad-nueva-prueba-otro")
	public String addCalidadOtro() {
		return "calidad-nueva-prueba-otro";
	}
	
	@GetMapping("detalle-calidad")
	public String infoCalidad() {
		return "detalle-calidad";
	}

	@GetMapping("calidad-nueva-prueba/{tipo}/{id}")
	public String addCalidad(@PathVariable(name = "id") Long id,@PathVariable(name = "tipo") String tipo, Model model) {
		if(tipo(tipo).equals("tipo")){
			return "redirect:/calidad-nueva-prueba/"+tipo+"/"+id;
		}
		try {
			addPruebaCalidad(disenioCalidad.findOneById(id, tipo(tipo)).getIdCalidad(),id,tipo(tipo),model);
		} catch (Exception e) {
			System.out.println("nuevo: "+e);
		}
		if(tipo.equals("forro")) {
			System.out.println("si esta entrando aqui");
			model.addAttribute("forro", "true");
		}
		else {
			model.addAttribute("forro", "false");
		}
		model.addAttribute("tipoMaterial", tipo(tipo));
		model.addAttribute("idMaterial", id);
		return "calidad-nueva-prueba";
	}
	

	public String addPruebaCalidad(Long id,Long idM,String tipo, Model model) {
		List<DisenioPruebaEncogimientoLavado> pruebasEL = pruebaEncogiLavado.findAllByCalidad(id);
		List<DisenioPruebaLavadoContaminacionCostura> pruebasLCC = pruebaContaCostura.findAllByCalidad(id);
		DisenioCalidad Calidad = disenioCalidad.findOne(id);
		//model.addAttribute("idTela", Calidad.getIdMaterial());
		model.addAttribute("idCalidad", id);
		model.addAttribute("idMaterial", id);
		model.addAttribute("tipoMaterial", tipo);
		model.addAttribute("readtela", "true");
		
		
		if (pruebaContaCostura.ifExist(id) == 1 || pruebaEncogiLavado.ifExist(id) == 1) {
			
			for (DisenioPruebaEncogimientoLavado u : pruebasEL) {
				if (u.getTipoPrueba().equalsIgnoreCase("Prueba de Fusión")) {
					// model.addAttribute("read", "true");
					model.addAttribute("operarioEncogi", u.getCreadoPor());
					model.addAttribute("fechaRealizacionEncogi", u.getFechaRealizacion().replace(" ", "T"));
					model.addAttribute("adherenciaEncogi", u.getAdherenciaPruebaVapor());
					model.addAttribute("proveedorEncogi", u.getProveedorPruebaVapor());
					model.addAttribute("temperaturaPruebaVapor", u.getTemperaturaPruebaVapor());
					model.addAttribute("tiempoPruebaVapor", u.getTiempoPrueba());
					model.addAttribute("presionPruebaVapor", u.getPresionPrueba());
					model.addAttribute("medidaHiloPruebaVapor", u.getMedidaInicialHilo());
					model.addAttribute("finalTramaPruebaVapor", u.getMedidaInicialTrama());
					model.addAttribute("diferenciaHiloPruebaVapor", u.getMedidaFinalHilo());
					model.addAttribute("finalHiloMedPruebaVapor", u.getDiferenciaMedidaHilo());
					model.addAttribute("diferenciaTramaPruebaVapor", u.getMedidaFinalTrama());
					model.addAttribute("finalTramaMedPruebaVapor", u.getDiferenciaMedidaTrama());
					model.addAttribute("observacionesReultPruebaVapor", u.getObservacionesResultados());
					model.addAttribute("proovedores", u.getProveedorPruebaVapor());
					model.addAttribute("entretelas", u.getEntretelaPruebaVapor());
					// model.addAttribute("displa", "true");
				}
				
				if (u.getTipoPrueba().equalsIgnoreCase("Plancha con Vapor")) {
					model.addAttribute("medidaHiloPlanchaVapor", u.getMedidaInicialHilo());
					model.addAttribute("medidaTramaPlanchaVapor", u.getMedidaInicialTrama());
					model.addAttribute("diferenciaHiloPlanchaVapor", u.getMedidaFinalHilo());
					model.addAttribute("finalHiloMedPlanchaVapor", u.getDiferenciaMedidaHilo());
					model.addAttribute("diferenciaTramaPlanchaVapor", u.getMedidaFinalTrama());
					model.addAttribute("finalTramaMedPlanchaVapor", u.getDiferenciaMedidaTrama());
					model.addAttribute("observacionesReultPlanchaVapor", u.getObservacionesResultados());
				}
				
				if (u.getTipoPrueba().equalsIgnoreCase("Prueba de Lavado")) {
					// model.addAttribute("readLavado", "true");
					model.addAttribute("operarioLavado", u.getCreadoPor());
					model.addAttribute("fechaRealizacionLavado", u.getFechaRealizacion().replace(" ", "T"));
					model.addAttribute("medidaHiloPruebaLavado", u.getMedidaInicialHilo());
					model.addAttribute("medidaTramaPruebaLavado", u.getMedidaInicialTrama());
					model.addAttribute("diferenciaHiloPruebaLavado", u.getMedidaFinalHilo());
					model.addAttribute("finalHiloMedPruebaLavado", u.getDiferenciaMedidaHilo());
					model.addAttribute("diferenciaTramaPruebaLavado", u.getMedidaFinalTrama());
					model.addAttribute("finalTramaMedPruebaLavado", u.getDiferenciaMedidaTrama());
					model.addAttribute("observacionesReultPruebaLavado", u.getObservacionesResultados());
					// model.addAttribute("displaLavado", "true");
				}
				
				model.addAttribute("idoperar", u.getCreadoPor());
			}
			
			for (DisenioPruebaLavadoContaminacionCostura cc : pruebasLCC) {
				if (cc.getTipoPrueba().equalsIgnoreCase("Solidez/Color")) {
					model.addAttribute("observacionesReultSolidez", cc.getObservacionesResultados());
					
					if (cc.getPruebaCalidad().equals("buena")) {
						model.addAttribute("checkBLavado", "true");
					}
					if (cc.getPruebaCalidad().equals("regular")) {
						model.addAttribute("checkRLavado", "true");
					}
					if (cc.getPruebaCalidad().equals("mala")) {
						model.addAttribute("checkMLavado", "true");
					}
				}
				if (cc.getTipoPrueba().equalsIgnoreCase("Resultado pilling")) {
					
					model.addAttribute("observacionesReultPilling", cc.getObservacionesResultados());
					
					if (cc.getPrueba_pilling().equals("si")) {
						model.addAttribute("checkSLavado", "true");
					}
					if (cc.getPrueba_pilling().equals("no")) {
						model.addAttribute("checkNLavado", "true");
					}
				}
				if (cc.getTipoPrueba().equalsIgnoreCase("Resultado costura")) {
					model.addAttribute("operarioCostura", cc.getCreadoPor());
					model.addAttribute("fechaRealizacionCostura", cc.getFechaRealizacion().replace(" ", "T"));
					// model.addAttribute("readCostura", "true");
					model.addAttribute("observacionesRasgado", cc.getObservacionesResultados());
					// model.addAttribute("displaCostura", "true");
					model.addAttribute("aguja", cc.getTipoAguja());
					
					if (cc.getDeslizamientoTela().equals("si")) {
						model.addAttribute("checkSDeslizamiento", "true");
					}
					if (cc.getDeslizamientoTela().equals("no")) {
						model.addAttribute("checkNDeslizamiento", "true");
					}

					if (cc.getRasgadoTela().equals("si")) {
						model.addAttribute("checkSCostura", "true");
					}
					if (cc.getRasgadoTela().equals("no")) {
						model.addAttribute("checkNCostura", "true");
					}
				}
				
				if (cc.getTipoPrueba().equalsIgnoreCase("Resultado de Contaminación")) {
					model.addAttribute("operarioContaminacion", cc.getCreadoPor());
					model.addAttribute("fechaRealizacionContaminacion", cc.getFechaRealizacion().replace(" ", "T"));
					// model.addAttribute("readContamin", "true");
					model.addAttribute("observacionesReultContaminacion", cc.getObservacionesResultados());
					// model.addAttribute("displaConta", "true");
					
					if (cc.getPruebaCalidad().equals("buena")) {
						model.addAttribute("checkBConta", "true");
					}
					if (cc.getPruebaCalidad().equals("regular")) {
						model.addAttribute("checkRConta", "true");
					}
					if (cc.getPruebaCalidad().equals("mala")) {
						model.addAttribute("checkMConta", "true");
					}
					
				}
				
				model.addAttribute("idoperar", cc.getCreadoPor());
				
			}
			
			return "/calidad-nueva-prueba";
		} else {
			return "calidad-nueva-prueba";
		}
	}
	
	@RequestMapping(value = "/detalle-calidad/{tipo}/{id}", method = RequestMethod.GET)
	public String infoPruebasCalidad(@PathVariable(name = "id") Long id,@PathVariable(name = "tipo") String tipo, Model model) {
		
		try {
			List<DisenioPruebaEncogimientoLavado> pruebasEL = pruebaEncogiLavado.findAllByCalidad(disenioCalidad.findOneById(id, tipo(tipo)).getIdCalidad());
			List<DisenioPruebaLavadoContaminacionCostura> pruebasLCC = pruebaContaCostura.findAllByCalidad(disenioCalidad.findOneById(id, tipo(tipo)).getIdCalidad());
			if (pruebaContaCostura.ifExist(id) == 1 || pruebaEncogiLavado.ifExist(id) == 1) {
			
				for (DisenioPruebaEncogimientoLavado u : pruebasEL) {
					if (u.getTipoPrueba().equalsIgnoreCase("Prueba de Fusión")) {
						
						model.addAttribute("adherenciaEnco", u.getAdherenciaPruebaVapor());
						model.addAttribute("proveedorEncogi", u.getProveedorPruebaVapor());
						try {
							model.addAttribute("entretela",
									materialService.findOne(Long.valueOf(u.getEntretelaPruebaVapor())).getNombreMaterial());
						} catch (Exception e) {
							model.addAttribute("entretela", " ");
						}
						
						model.addAttribute("temperaturaVapor", u.getTemperaturaPruebaVapor());
						model.addAttribute("tiempoVapor", u.getTiempoPrueba());
						model.addAttribute("presionVapor", u.getPresionPrueba());
						model.addAttribute("finalMedHiloPruebaVapor", u.getMedidaFinalHilo());
						model.addAttribute("diferenciaMedPruebaVapor", u.getDiferenciaMedidaHilo());
						model.addAttribute("finalTramaMedPruebaVapor", u.getMedidaFinalTrama());
						model.addAttribute("diferenciaTramaPruebaVapor", u.getDiferenciaMedidaTrama());
						model.addAttribute("observacionesReultPruebaVapor", u.getObservacionesResultados());
					}

					if (u.getTipoPrueba().equalsIgnoreCase("Plancha con Vapor")) {
						model.addAttribute("finalHiloMedPlanchaVapor", u.getMedidaFinalHilo());
						model.addAttribute("diferenciaHiloPlanchaVapor", u.getDiferenciaMedidaHilo());
						model.addAttribute("finalTramaMedPlanchaVapor", u.getMedidaFinalTrama());
						model.addAttribute("diferenciaTramaPlanchaVapor", u.getDiferenciaMedidaTrama());
						model.addAttribute("observacionesReultPlanchaVapor", u.getObservacionesResultados());
					}
					
					if (u.getTipoPrueba().equalsIgnoreCase("Prueba de Lavado")) {
						
						model.addAttribute("finalHiloMedPruebaLavado", u.getMedidaFinalHilo());
						model.addAttribute("diferenciaHiloPruebaLavado", u.getDiferenciaMedidaHilo());
						model.addAttribute("finalTramaMedPruebaLavado", u.getMedidaFinalTrama());
						model.addAttribute("diferenciaTramaPruebaLavado", u.getDiferenciaMedidaTrama());
						model.addAttribute("observacionesReultPruebaLavado", u.getObservacionesResultados());
					}
				}
				
				for (DisenioPruebaLavadoContaminacionCostura cc : pruebasLCC) {
					
					if (cc.getTipoPrueba().equalsIgnoreCase("Solidez/Color")) {
						model.addAttribute("observacionesReultSolidez", cc.getObservacionesResultados());
						model.addAttribute("solidezColor", cc.getPruebaCalidad());
					}
					
					if (cc.getTipoPrueba().equalsIgnoreCase("Resultado pilling")) {
						
						model.addAttribute("observacionesReultPilling", cc.getObservacionesResultados());
						model.addAttribute("resultadoPilling", cc.getPrueba_pilling());
					}
					
					if (cc.getTipoPrueba().equalsIgnoreCase("Resultado costura")) {
						model.addAttribute("observacionescostura", cc.getObservacionesResultados());
						model.addAttribute("Deslizamiento", cc.getDeslizamientoTela());
						model.addAttribute("Costura", cc.getRasgadoTela());
						
					}

					
					if (cc.getTipoPrueba().equalsIgnoreCase("Resultado de contaminación")
							|| cc.getTipoPrueba().equalsIgnoreCase("Resultado de contaminacion")) {
						model.addAttribute("observacionesReultContaminacion", cc.getObservacionesResultados());
						model.addAttribute("calidadContaminacion", cc.getPruebaCalidad());
						
					}
					
				}
				return "/detalle-calidad";
			} else {
				return "/detalle-calidad";
			}
		} catch (Exception e) {
			System.out.println("nuevo: "+e);
			return "redirect:/calidad";
		}

		

	}
}
