package com.martinez.johan.dincomapp.Utilities;

public class Utilities {

    /**Constantes de la tabla terminos*/
    public static final String TABLE_TERMS ="terminos";
    public static final String FIELD_ID ="id_term";
    public static final String FIELD_T_NAME ="Name";
    public static final String FIELD_T_DEFINITION ="definicion";
    public static final String FIELD_T_LINKTUTORIAL ="link_tutorial";

    /**Constantes de la tabla platos*/
    public static final String TABLE_PLATES ="platos";
    public static final String FIELD_P_NAME="nombre";
    public static final String FIELD_P_TYPE="tipo";
    public static final String FIELD_P_PRICE="precio";
    public static final String FIELD_P_TIME="tiempo_preparacion";

    /**Constantes de la tabla bebidas*/
    public static final String TABLE_DRINKS ="bebidas";
    public static final String FIELD_D_NAME="nombre";
    public static final String FIELD_D_PRICE="precio";

    public static final String CREATE_TABLE_TERMS ="CREATE TABLE "+ TABLE_TERMS +" ("+ FIELD_ID +" INTEGER NOT NULL PRIMARY KEY, "+ FIELD_T_NAME +" TEXT, "+ FIELD_T_DEFINITION +" TEXT, "+ FIELD_T_LINKTUTORIAL +" TEXT)";
    public static final String CREATE_TABLE_PLATES="CREATE TABLE "+ TABLE_PLATES +" ("+FIELD_P_NAME+" TEXT, "+FIELD_P_TYPE+" TEXT, "+FIELD_P_PRICE+" TEXT, "+FIELD_P_TIME+" TEXT)";
    public static final String CREATE_TABLE_DRINKS="CREATE TABLE "+ TABLE_DRINKS +" ("+FIELD_D_NAME+" TEXT, "+FIELD_D_PRICE+" TEXT)";

    /**Información de la tabla terminos*/

    public static final String CREATE_TERM_1 = "INSERT INTO terminos VALUES(004,'3D','Ensayando_ingreso_de_datos','Sin turorial')";

    public static final String CREATE_INFO_TABLE = "INSERT " +
            "INTO terminos VALUES(001, '@ - arroba', '(en inglés significa at [en]). En las direcciones de e-mail, es el símbolo que separa el nombre del usuario del nombre de su proveedor de correo electrónico. Por ejemplo: pepe@hotmail.com', 'No tiene Tutorial')" +
            "INSERT INTO terminos VALUES(002,'3D','Ensayando_ingreso_de_datos','Sin turorial')" +
            "INSERT INTO terminos VALUES(003,'Acceso Directo','Ensayando_ingreso_de_datos','Sin turorial')" +
            "INSERT INTO terminos VALUES(004,'Acrobat','Ensayando_ingreso_de_datos','Sin turorial')" +
            "INSERT INTO terminos VALUES(005,'ADSL','Ensayando_ingreso_de_datos','Sin turorial')" +
            "INSERT INTO terminos VALUES(006,'AGP','Ensayando_ingreso_de_datos','Sin turorial')" +
            "INSERT INTO terminos VALUES(007,'AI','Ensayando_ingreso_de_datos','Sin turorial')" +
            "INSERT INTO terminos VALUES(008,'Algoritmo','Ensayando_ingreso_de_datos','Sin turorial')" +
            "INSERT INTO terminos VALUES(009,'Amazon','Ensayando_ingreso_de_datos','Sin turorial')" +
            "INSERT INTO terminos VALUES(010,'AMIBIOS','Ensayando_ingreso_de_datos','Sin turorial')" +
            "INSERT INTO terminos VALUES(011,'Ancho de Banda','Ensayando_ingreso_de_datos','Sin turorial')" +
            "INSERT INTO terminos VALUES(012,'AOL','Ensayando_ingreso_de_datos','Sin turorial')";



}
