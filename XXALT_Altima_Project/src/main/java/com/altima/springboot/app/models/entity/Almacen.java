package com.altima.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "alt_almacen")

public class Almacen implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_almacen")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idAlmacen;
	
	@Column(name="id_text")
	@NotBlank
	private String idText;
	

	public Long getIdAlmacen() {
		return idAlmacen;
	}

	public void setIdAlmacen(Long idAlmacen) {
		this.idAlmacen = idAlmacen;
	}

	public String getIdText() {
		return idText;
	}

	public void setIdText(String idText) {
		this.idText = idText;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAlmacen == null) ? 0 : idAlmacen.hashCode());
		result = prime * result + ((idText == null) ? 0 : idText.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Almacen other = (Almacen) obj;
		if (idAlmacen == null) {
			if (other.idAlmacen != null)
				return false;
		} else if (!idAlmacen.equals(other.idAlmacen))
			return false;
		if (idText == null) {
			if (other.idText != null)
				return false;
		} else if (!idText.equals(other.idText))
			return false;
		return true;
	}
	
	
	

}
