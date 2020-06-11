package com.example.sqlitebaseadapter;

public class Persona 
{
    private int id;
	private String nombre;
	private String telefono;
	private int sexo;  // 0 masculino, 1 femenino
	
	public Persona() {	}

	public Persona(int id, String nombre, String telefono, int sexo) {
        this.setId(id);
        this.setNombre(nombre);
		this.setTelefono(telefono);
		this.setSexo(sexo);
	}

    public int getId() { return id; }
    public void setId(int id) { this.id = id;   }
	public String toString() { return nombre; }
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public int getSexo() {
		return sexo;
	}
	public void setSexo(int sexo) {
		this.sexo = sexo;
	}
}
