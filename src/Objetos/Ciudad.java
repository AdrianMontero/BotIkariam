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
public class Ciudad {

    private int idCiudad;
    private String nombreCiu;
    private int coordXCiu;
    private int coordYCiu;
    private int maderaCiu;
    private int vinoCiu;
    private int marmolCiu;
    private int cristalCiu;
    private int azufreCiu;

    //Getters y setters
    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombreCiu() {
        return nombreCiu;
    }

    public void setNombreCiu(String nombreCiu) {
        this.nombreCiu = nombreCiu;
    }

    public int getCoordXCiu() {
        return coordXCiu;
    }

    public void setCoordXCiu(int coordXCiu) {
        this.coordXCiu = coordXCiu;
    }

    public int getCoordYCiu() {
        return coordYCiu;
    }

    public void setCoordYCiu(int coordYCiu) {
        this.coordYCiu = coordYCiu;
    }

    public int getMaderaCiu() {
        return maderaCiu;
    }

    public void setMaderaCiu(int maderaCiu) {
        this.maderaCiu = maderaCiu;
    }

    public int getVinoCiu() {
        return vinoCiu;
    }

    public void setVinoCiu(int vinoCiu) {
        this.vinoCiu = vinoCiu;
    }

    public int getMarmolCiu() {
        return marmolCiu;
    }

    public void setMarmolCiu(int marmolCiu) {
        this.marmolCiu = marmolCiu;
    }

    public int getCristalCiu() {
        return cristalCiu;
    }

    public void setCristalCiu(int cristalCiu) {
        this.cristalCiu = cristalCiu;
    }

    public int getAzufreCiu() {
        return azufreCiu;
    }

    public void setAzufreCiu(int azufreCiu) {
        this.azufreCiu = azufreCiu;
    }

    //Constructores
    public Ciudad(int idCiudad, String nombreCiu, int coordXCiu, int coordYCiu, int maderaCiu, int vinoCiu, int marmolCiu, int cristalCiu, int azufreCiu) {
        this.idCiudad = idCiudad;
        this.nombreCiu = nombreCiu;
        this.coordXCiu = coordXCiu;
        this.coordYCiu = coordYCiu;
        this.maderaCiu = maderaCiu;
        this.vinoCiu = vinoCiu;
        this.marmolCiu = marmolCiu;
        this.cristalCiu = cristalCiu;
        this.azufreCiu = azufreCiu;
    }

    public Ciudad(String nombreCiu, int coordXCiu, int coordYCiu, int maderaCiu, int vinoCiu, int marmolCiu, int cristalCiu, int azufreCiu) {
        this.nombreCiu = nombreCiu;
        this.coordXCiu = coordXCiu;
        this.coordYCiu = coordYCiu;
        this.maderaCiu = maderaCiu;
        this.vinoCiu = vinoCiu;
        this.marmolCiu = marmolCiu;
        this.cristalCiu = cristalCiu;
        this.azufreCiu = azufreCiu;
    }
    
    

    public Ciudad(int idCiudad, int maderaCiu, int vinoCiu, int marmolCiu, int cristalCiu, int azufreCiu) {
        this.idCiudad = idCiudad;
        this.maderaCiu = maderaCiu;
        this.vinoCiu = vinoCiu;
        this.marmolCiu = marmolCiu;
        this.cristalCiu = cristalCiu;
        this.azufreCiu = azufreCiu;
    }

    public Ciudad() {
    }

}
