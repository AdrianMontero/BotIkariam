/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

/**
 *
 * @author ADRIAN
 */
public class Construccion {

    private String nombreConstruccion; //Nombre del edificio (intendencia, cuartel, academia....)
    private int idConstruccion; //ID de la construccion en la BD
    private int idCiudad; //Id que asocia el edificio que queremos subir y la ciudad a la que pertenece
    private String posIkaCon; //Nombre especifico de la posicion del edificio en la web de ikariam

    //Getters y setters
    public void setIdConstruccion(int idConstruccion) {
        this.idConstruccion = idConstruccion;
    }

    public int getIdConstruccion() {
        return idConstruccion;
    }

    public String getNombreConstruccion() {
        return nombreConstruccion;
    }

    public void setNombreConstruccion(String nombreConstruccion) {
        this.nombreConstruccion = nombreConstruccion;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getPosIkaCon() {
        return posIkaCon;
    }

    public void setPosIkaCon(String posIkaEdi) {
        this.posIkaCon = posIkaEdi;
    }

    //Constructores
    /**
     * Constructor completo para las construcciones
     *
     * @param nombreConstruccion Nombre de la construccion
     * @param idConstruccion Id de la construccion
     * @param idCiudad Id de la ciudad a la que se va a asociar la construccion
     * @param posIkaCon Id de la construccion en la web de ikariam
     */
    public Construccion(String nombreConstruccion, int idConstruccion, int idCiudad, String posIkaCon) {
        this.nombreConstruccion = nombreConstruccion;
        this.idConstruccion = idConstruccion;
        this.idCiudad = idCiudad;
        this.posIkaCon = posIkaCon;
    }

    /**
     * Constructor usado para actualizar construcciones
     *
     * @param nombreConstruccion Nombre de la construccion
     * @param idCiudad Id de la ciudad a la que se va a asociar la construccion
     * @param posIkaCon Id de la construccion en la web de ikariam
     */
    public Construccion(String nombreConstruccion, int idCiudad, String posIkaCon) {
        this.nombreConstruccion = nombreConstruccion;
        this.idCiudad = idCiudad;
        this.posIkaCon = posIkaCon;
    }

    /**
     * Constructor vacio
     */
    public Construccion() {
    }

}
