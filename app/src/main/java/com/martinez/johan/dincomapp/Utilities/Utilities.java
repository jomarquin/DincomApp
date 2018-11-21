package com.martinez.johan.dincomapp.Utilities;

public class Utilities {

    /**Constantes de la tabla terminos*/
    public static final String TABLE_TERMS ="terminos";
    public static final String FIELD_ID ="id_term";
    public static final String FIELD_T_NAME ="Name";
    public static final String FIELD_T_DEFINITION ="definicion";
    public static final String FIELD_T_LINKTUTORIAL ="link_tutorial";

    public static final String CREATE_TABLE_TERMS ="CREATE TABLE "+ TABLE_TERMS +" ("+ FIELD_ID +" INTEGER NOT NULL PRIMARY KEY, "+ FIELD_T_NAME +" TEXT, "+ FIELD_T_DEFINITION +" TEXT, "+ FIELD_T_LINKTUTORIAL +" TEXT)";

    /**Otras variables*/

    public static String WORD_SEARCH ="";
    public static String TUTORIAL_SEARCH ="";
    public static String SEARCH_TYPE ="";
    public static String ID_VIDEO ="";
    public static String FRAG_TOOLBAR ="";





}
