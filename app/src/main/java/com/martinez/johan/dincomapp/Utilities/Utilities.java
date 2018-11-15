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

    public static String WORD_SEARCH ="";





}
