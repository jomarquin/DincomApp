package com.martinez.johan.dincomapp;

import com.martinez.johan.dincomapp.Entities.Drink;
import com.martinez.johan.dincomapp.Entities.Term;

public interface IComunicaFragments {
    public void sendTerm(Term term);

    public void sendDrink(Drink drink);
}
