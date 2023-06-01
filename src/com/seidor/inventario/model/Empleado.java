package com.seidor.inventario.model;
// Generated 22 may. 2023 18:00:34 by Hibernate Tools 4.3.5.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Empleado generated by hbm2java
 */
public class Empleado implements java.io.Serializable {

	private Integer idEmpleado;
	private Almacen almacen;
	private String nombre;
	private String APaterno;
	private String AMaterno;
	private String curp;
	private String rfc;
	private String numeroEmpleado;
	private String telefono;
	private String extension;
	private String celular;
	private String EMail;
	private String numeroSegSocial;
	private Date fechaRegistro;
	private String cargo;
	private int activo;
	private Set ordenCompras = new HashSet(0);
	private Set proyectos = new HashSet(0);
	private Set entradas = new HashSet(0);
	private Set salidas = new HashSet(0);
	private Set usuarios = new HashSet(0);

	public Empleado() {
	}

	public Empleado(Date fechaRegistro, String cargo, int activo) {
		this.fechaRegistro = fechaRegistro;
		this.cargo = cargo;
		this.activo = activo;
	}

	public Empleado(Almacen almacen, String nombre, String APaterno, String AMaterno, String curp, String rfc,
			String numeroEmpleado, String telefono, String extension, String celular, String EMail,
			String numeroSegSocial, Date fechaRegistro, String cargo, int activo, Set ordenCompras, Set proyectos,
			Set entradas, Set salidas, Set usuarios) {
		this.almacen = almacen;
		this.nombre = nombre;
		this.APaterno = APaterno;
		this.AMaterno = AMaterno;
		this.curp = curp;
		this.rfc = rfc;
		this.numeroEmpleado = numeroEmpleado;
		this.telefono = telefono;
		this.extension = extension;
		this.celular = celular;
		this.EMail = EMail;
		this.numeroSegSocial = numeroSegSocial;
		this.fechaRegistro = fechaRegistro;
		this.cargo = cargo;
		this.activo = activo;
		this.ordenCompras = ordenCompras;
		this.proyectos = proyectos;
		this.entradas = entradas;
		this.salidas = salidas;
		this.usuarios = usuarios;
	}

	public Integer getIdEmpleado() {
		return this.idEmpleado;
	}

	public void setIdEmpleado(Integer idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public Almacen getAlmacen() {
		return this.almacen;
	}

	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAPaterno() {
		return this.APaterno;
	}

	public void setAPaterno(String APaterno) {
		this.APaterno = APaterno;
	}

	public String getAMaterno() {
		return this.AMaterno;
	}

	public void setAMaterno(String AMaterno) {
		this.AMaterno = AMaterno;
	}

	public String getCurp() {
		return this.curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getRfc() {
		return this.rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getNumeroEmpleado() {
		return this.numeroEmpleado;
	}

	public void setNumeroEmpleado(String numeroEmpleado) {
		this.numeroEmpleado = numeroEmpleado;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getExtension() {
		return this.extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEMail() {
		return this.EMail;
	}

	public void setEMail(String EMail) {
		this.EMail = EMail;
	}

	public String getNumeroSegSocial() {
		return this.numeroSegSocial;
	}

	public void setNumeroSegSocial(String numeroSegSocial) {
		this.numeroSegSocial = numeroSegSocial;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public int getActivo() {
		return this.activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public Set getOrdenCompras() {
		return this.ordenCompras;
	}

	public void setOrdenCompras(Set ordenCompras) {
		this.ordenCompras = ordenCompras;
	}

	public Set getProyectos() {
		return this.proyectos;
	}

	public void setProyectos(Set proyectos) {
		this.proyectos = proyectos;
	}

	public Set getEntradas() {
		return this.entradas;
	}

	public void setEntradas(Set entradas) {
		this.entradas = entradas;
	}

	public Set getSalidas() {
		return this.salidas;
	}

	public void setSalidas(Set salidas) {
		this.salidas = salidas;
	}

	public Set getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Set usuarios) {
		this.usuarios = usuarios;
	}

}
