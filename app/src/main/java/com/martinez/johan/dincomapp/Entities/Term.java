package com.martinez.johan.dincomapp.Entities;

import java.io.Serializable;

public class Term implements Serializable {
    //private Integer id_term;
    private String t_Name;
    private String t_description;
    private String t_Definition;
    private String t_linkTutorial;

    public Term() {
    }

    public Term(String t_Name, String t_description, String t_Definition, String t_linkTutorial) {
        //this.id_term = id_term;
        this.t_Name = t_Name;
        this.t_description = t_description;
        this.t_Definition = t_Definition;
        this.t_linkTutorial = t_linkTutorial;
    }


    public String getT_description() {
        return t_description;
    }

    public void setT_description(String t_description) {
        this.t_description = t_description;
    }


    public String getT_Name() {
        return t_Name;
    }

    public void setT_Name(String t_Name) {
        this.t_Name = t_Name;
    }

    public String getT_Definition() {
        return t_Definition;
    }

    public void setT_Definition(String t_Definition) {
        this.t_Definition = t_Definition;
    }

    public String getT_linkTutorial() {
        return t_linkTutorial;
    }

    public void setT_linkTutorial(String t_linkTutorial) {
        this.t_linkTutorial = t_linkTutorial;
    }
}
