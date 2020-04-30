package com.altima.springboot.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.models.entity.DisenioMaterial;
import com.altima.springboot.app.models.entity.DisenioMaterialPrenda;
import com.altima.springboot.app.models.entity.DisenioPrenda;
import com.altima.springboot.app.models.entity.DisenioPrendaMarcador;
import com.altima.springboot.app.models.entity.DisenioPrendaPatronaje;
import com.altima.springboot.app.models.service.DisenioMaterialPrendaServiceImpl;
import com.altima.springboot.app.models.service.DisenioPrendaPatronajeServiceImpl;
import com.altima.springboot.app.models.service.DisenioPrendaServiceImpl;
import com.altima.springboot.app.models.service.IDisenioLookupService;
import com.altima.springboot.app.models.service.IDisenioMaterialService;
import com.altima.springboot.app.models.service.IDisenioPrendaMarcadorService;
import com.altima.springboot.app.models.service.UploadServiceImpl;

@RestController
public class AgregarPrendaRestController {
	@Autowired
	IDisenioMaterialService disenioMaterialService;
	@Autowired
	private UploadServiceImpl uService;
	@Autowired
	private DisenioPrendaServiceImpl prendaService;
	@Autowired
	private DisenioMaterialPrendaServiceImpl materialPrendaService;
	@Autowired
	private DisenioPrendaPatronajeServiceImpl prendaPatronajeService;
	@Autowired
	private IDisenioLookupService disenioLookupService;
	@Autowired
	private IDisenioPrendaMarcadorService disenioPrendaMarcadorService;
	public String file1;

	public String file2;

	public DisenioPrenda dp = new DisenioPrenda();

	@RequestMapping(value = "/detalle_material", method = RequestMethod.GET)
	public Object detalleMaterial(@RequestParam Long id) {
		Object dm = disenioMaterialService.findUno(id);

		return dm;
	}

	@RequestMapping(value = "/detalle_patronaje", method = RequestMethod.GET)
	public Object detallePatronaje(@RequestParam Long id) {
		Object dl = disenioMaterialService.findLookUp(id);
		System.out.println("pos aqui si entra");
		return dl;
	}
	
	@RequestMapping(value = "/confirmar_prenda", method = RequestMethod.GET)
	public Object confirmarPrenda(@RequestParam Long id) {
		DisenioPrenda diamantePerla = prendaService.findOne(id);
		diamantePerla.setEstatusRecepcionMuestra("Definitivo");
		prendaService.save(diamantePerla);
		return diamantePerla;
	}
	
	@RequestMapping(value = "/alta_baja_prenda", method = RequestMethod.GET)
	public Object AltaBajaPrenda(@RequestParam Long id) {
		DisenioPrenda diamantePerla = prendaService.findOne(id);
		if(diamantePerla.getEstatus() == 0)
		{
			diamantePerla.setEstatus(1L);
		}
		else
		{
			diamantePerla.setEstatus(0L);
		}
		
		prendaService.save(diamantePerla);
		return diamantePerla;
	}

	@RequestMapping(value = "/guardar_prenda", method = RequestMethod.POST)
	public DisenioPrenda guardarPrenda(@RequestParam(name = "disenioprenda") String disenioprenda) {
		// Coso del auth
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		JSONObject prenda = new JSONObject(disenioprenda.toString());

		dp.setIdFamiliaPrenda(Long.valueOf((String) prenda.get("idFamiliaPrenda")));
		dp.setCreadoPor(auth.getName());
		dp.setActualizadoPor(auth.getName());
		dp.setNumeroPrenda(prenda.get("numeroPrenda").toString());
		dp.setDescripcionPrenda(prenda.get("descripcionPrenda").toString());
		dp.setTipoPrenda(prenda.get("tipoPrenda").toString());
		dp.setDetallePrenda(prenda.get("detallePrenda").toString());
		dp.setNotaEspecial(prenda.get("notaEspecial").toString());
		dp.setPrecioLocalActual(prenda.get("precioLocalActual").toString());
		dp.setPrecioLocalAnterior(prenda.get("precioLocalAnterior").toString());
		dp.setPrecioForaneoActual(prenda.get("precioForaneoActual").toString());
		dp.setPrecioForaneoAnterior(prenda.get("precioForaneoAnterior").toString());
		dp.setDetalleConfeccion(prenda.get("detalleConfeccion").toString());
		dp.setMarcadores(prenda.get("marcadores").toString());
		dp.setConsumoTela(prenda.get("consumoTela").toString());
		dp.setConsumoForro(prenda.get("consumoForro").toString());
		dp.setPrecio(prenda.get("precio").toString());
		dp.setCveRuta("1"); // Ruta predefinida siempre
		dp.setTipoLargo(prenda.get("tipoLargo").toString());
		dp.setImprimirEtiquetas(prenda.get("imprimirEtiquetas").toString());
		dp.setEstatusRecepcionMuestra("Definitivo");
		dp.setDevolucion(prenda.get("devolucion").toString());
		dp.setPrecioMprod(prenda.get("precioMprod").toString());
		dp.setPrecioMmuestra(prenda.get("precioMmuestra").toString());
		dp.setCategoria(prenda.get("categoria").toString());
		dp.setEstatus(1L);
		dp.setPrendaLocal("1");

		prendaService.save(dp);
		
		//Ides
		Long envio = Long.valueOf(prenda.get("tipoPrenda").toString());
		String[] res = prendaService.getExistencias(envio);
		dp.setIdText(res[1].toUpperCase().substring(0, 3) + (10000 + (Long.valueOf(res[0]))));
		dp.setIdTextProspecto("PROSP" + res[1].toUpperCase().substring(0, 3) + (10000 + (Long.valueOf(res[0]))));
		dp.setEstatusRecepcionMuestra("Prospecto");
		prendaService.save(dp);
		
		return dp;
	}
	
	
	@RequestMapping(value = "/editar_prenda", method = RequestMethod.POST)
	public DisenioPrenda editarPrenda(@RequestParam(name = "disenioprenda") String disenioprenda) 
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		JSONObject prenda = new JSONObject(disenioprenda.toString());
		dp = prendaService.findOne(Long.valueOf(prenda.get("idPrenda").toString()));
		dp.setIdFamiliaPrenda(Long.valueOf((String) prenda.get("idFamiliaPrenda")));
		dp.setActualizadoPor(auth.getName());
		dp.setNumeroPrenda(prenda.get("numeroPrenda").toString());
		dp.setDescripcionPrenda(prenda.get("descripcionPrenda").toString());
		dp.setTipoPrenda(prenda.get("tipoPrenda").toString());
		dp.setDetallePrenda(prenda.get("detallePrenda").toString());
		dp.setNotaEspecial(prenda.get("notaEspecial").toString());
		dp.setPrecioLocalActual(prenda.get("precioLocalActual").toString());
		dp.setPrecioLocalAnterior(prenda.get("precioLocalAnterior").toString());
		dp.setPrecioForaneoActual(prenda.get("precioForaneoActual").toString());
		dp.setPrecioForaneoAnterior(prenda.get("precioForaneoAnterior").toString());
		dp.setDetalleConfeccion(prenda.get("detalleConfeccion").toString());
		dp.setMarcadores(prenda.get("marcadores").toString());
		dp.setConsumoTela(prenda.get("consumoTela").toString());
		dp.setConsumoForro(prenda.get("consumoForro").toString());
		dp.setPrecio(prenda.get("precio").toString());
		dp.setCveRuta("1"); // Ruta predefinida siempre
		dp.setTipoLargo(prenda.get("tipoLargo").toString());
		dp.setImprimirEtiquetas(prenda.get("imprimirEtiquetas").toString());
		dp.setEstatusRecepcionMuestra("Definitivo");
		dp.setDevolucion(prenda.get("devolucion").toString());
		dp.setPrecioMprod(prenda.get("precioMprod").toString());
		dp.setPrecioMmuestra(prenda.get("precioMmuestra").toString());
		dp.setCategoria(prenda.get("categoria").toString());
		prendaService.save(dp);
		return dp;
	}

	@RequestMapping(value = "/guardar_final", method = RequestMethod.POST)
	public void guardarFinal(@RequestParam(name = "objeto_materiales") String objeto_materiales,
			@RequestParam(name = "objeto_marcadores") String objeto_marcadores,
			@RequestParam(name = "objeto_patronajes") String objeto_patronaje,
			@RequestParam(name = "accion") String accion) throws NoSuchFieldException, SecurityException {
		
		System.out.println(accion);
		if (accion.equalsIgnoreCase("editar")) 
		{
			System.out.println("eliminare losdemas porque voy a editar ");
			disenioPrendaMarcadorService.deleteByIdPrenda(dp.getIdPrenda());
			prendaPatronajeService.deleteAllPatronajeFromPrenda(dp.getIdPrenda());
			materialPrendaService.deleteAllMaterialFromPrenda(dp.getIdPrenda());
			
			System.out.println(objeto_marcadores);
		}

		if(!objeto_marcadores.equals(null)||objeto_marcadores.equals("")){
			for (String marcador_split : objeto_marcadores.split(",")) {
				DisenioPrendaMarcador dpm = new DisenioPrendaMarcador();
				System.out.println(marcador_split);
				int num1 = Integer.parseInt(marcador_split.replaceAll("^\"|\"$", ""));
				Long num = new Long(num1);
				dpm.setIdMarcador(num);
				dpm.setIdPrenda(dp.getIdPrenda());
				disenioPrendaMarcadorService.save(dpm);
			}
		}

		// Coso del auth
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// Se guardan Muchos a Muchos de Materiales
		JSONArray materiales = new JSONArray(objeto_materiales);
		for (int i = 0; i < materiales.length(); i++) {
			JSONObject material = materiales.getJSONObject(i);
			DisenioMaterialPrenda mdp = new DisenioMaterialPrenda();
			mdp.setIdMaterial(Long.valueOf(material.get("id").toString()));
			mdp.setIdPrenda(Long.valueOf(dp.getIdPrenda()));
			mdp.setCreadoPor(auth.getName());
			mdp.setActualizadoPor(auth.getName());
			mdp.setCantidad(material.get("cantidad").toString());
			materialPrendaService.save(mdp);
		}

		// Se guardan Muchos a Muchos de Patronaje
		JSONArray patronajes = new JSONArray(objeto_patronaje);
		for (int i = 0; i < patronajes.length(); i++) {
			JSONObject patronaje = patronajes.getJSONObject(i);
			DisenioPrendaPatronaje dpp = new DisenioPrendaPatronaje();
			dpp.setIdPrenda(Long.valueOf(dp.getIdPrenda()));
			dpp.setIdPatronaje(patronaje.get("id").toString());
			dpp.setCantidadTela(patronaje.get("cantidadTela").toString());
			dpp.setCantidadForro(patronaje.get("cantidadForro").toString());
			dpp.setCantidadEntretela(patronaje.get("cantidadEntretela").toString());
			System.out.println("id= "+patronaje.get("id").toString()+" tela= "+patronaje.get("cantidadTela").toString()+" cantidadForro= "+patronaje.get("cantidadForro").toString()+" entretela= "+patronaje.get("cantidadEntretela").toString());

			prendaPatronajeService.save(dpp);
		}

		dp = null;
		this.dp = new DisenioPrenda();
	}



	@RequestMapping(value = "/prendas", method = RequestMethod.POST)
	public void guardar(HttpServletResponse response, DisenioPrenda dp, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, @RequestParam("file2") MultipartFile foto2,
			RedirectAttributes flash) throws InterruptedException, IOException {
		String[] uniqueFilename = null;
		try {
			uniqueFilename = uService.copy(foto, foto2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.dp.setDibujoEspalda(uniqueFilename[0]);
		this.dp.setDibujoFrente(uniqueFilename[1]);

		Thread.sleep(2000);
		response.sendRedirect("/prendas");
	}

	@RequestMapping(value = "/prendas1", method = RequestMethod.POST)
	public void guardar1(HttpServletResponse response, DisenioPrenda dp, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash)
			throws InterruptedException, IOException {
		String uniqueFilename = null;
		try {
			uniqueFilename = uService.copy2(foto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.dp.setDibujoFrente(uniqueFilename);

		Thread.sleep(2000);
		response.sendRedirect("/prendas");
	}

	@RequestMapping(value = "/prendas2", method = RequestMethod.POST)
	public void guardar2(HttpServletResponse response, DisenioPrenda dp, BindingResult result, Model model,
			@RequestParam("file2") MultipartFile foto2, RedirectAttributes flash)
			throws InterruptedException, IOException {
		String uniqueFilename = null;
		try {
			uniqueFilename = uService.copy2(foto2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.dp.setDibujoEspalda(uniqueFilename);

		Thread.sleep(2000);
		response.sendRedirect("/prendas");
	}
}
