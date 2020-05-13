package com.altima.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "alt_comercial_pedido_informacion")
public class ComercialPedidoInformación implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_pedido_informacion")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idPedidoInformacion;
	
	@Column(name="id_text")
	private String idText;
	
	@Column(name="id_empresa")
	private Long idEmpresa;
	
	@Column(name="tipo_pedido")
	private String tipoPedido;
	
	@Column(name="id_pedido")
	private Long idPedido;
	
	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	@Column(name="fecha_toma_tallas")
	private String fechaTomaTalla;
	
	@Column(name="fecha_anticipo")
	private String fechaAnticipo;
	
	@Column(name="fecha_entrega")
	private String fechaEntrega;
	
	@Column(name="anticipo")
	private String anticipo;
	
	@Column(name="entrega")
	private String entrega;
	
	@Column(name="saldo")
	private String saldo;
	
	@Column(name="total_pedido")
	private String totalPedido;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_creacion")
	private String ultimaFechaCreacion;

	public Long getIdPedidoInformacion() {
		return idPedidoInformacion;
	}

	public void setIdPedidoInformacion(Long idPedidoInformacion) {
		this.idPedidoInformacion = idPedidoInformacion;
	}

	public String getIdText() {
		return idText;
	}

	public void setIdText(String idText) {
		this.idText = idText;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getTipoPedido() {
		return tipoPedido;
	}

	public void setTipoPedido(String tipoPedido) {
		this.tipoPedido = tipoPedido;
	}

	public String getFechaTomaTalla() {
		return fechaTomaTalla;
	}

	public void setFechaTomaTalla(String fechaTomaTalla) {
		this.fechaTomaTalla = fechaTomaTalla;
	}

	public String getFechaAnticipo() {
		return fechaAnticipo;
	}

	public void setFechaAnticipo(String fechaAnticipo) {
		this.fechaAnticipo = fechaAnticipo;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getAnticipo() {
		return anticipo;
	}

	public void setAnticipo(String anticipo) {
		this.anticipo = anticipo;
	}

	public String getEntrega() {
		return entrega;
	}

	public void setEntrega(String entrega) {
		this.entrega = entrega;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	public String getTotalPedido() {
		return totalPedido;
	}

	public void setTotalPedido(String totalPedido) {
		this.totalPedido = totalPedido;
	}

	public String getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(String creadoPor) {
		this.creadoPor = creadoPor;
	}

	public String getActualizadoPor() {
		return actualizadoPor;
	}

	public void setActualizadoPor(String actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getUltimaFechaCreacion() {
		return ultimaFechaCreacion;
	}

	public void setUltimaFechaCreacion(String ultimaFechaCreacion) {
		this.ultimaFechaCreacion = ultimaFechaCreacion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	
}
