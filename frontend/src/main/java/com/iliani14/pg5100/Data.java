package com.iliani14.pg5100;

import com.iliani14.pg5100.entities.Countries;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * Created by anitailieva on 05/10/2016.
 */
@ManagedBean
@ApplicationScoped
public class Data {

    public Countries[] getCountries() {
        return Countries.values();
    }

}