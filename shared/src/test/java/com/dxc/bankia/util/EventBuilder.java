package com.dxc.bankia.util;


import com.dxc.bankia.model.Country;
import com.dxc.bankia.model.Event;
import com.dxc.bankia.model.Vehicle;

import java.util.Date;
import java.util.Optional;

public class EventBuilder {

    private final Event instance;

    private static Long iddGenerator = 0L;

    public EventBuilder() {
        instance = new Event();
        instance.setId(iddGenerator++);
    }

    public EventBuilder withId(Long id){
        instance.setId(id);
        return this;
    }


    public EventBuilder withRegistrationNumber(String registrationNumber){
        instance.setRegistrationNumber(registrationNumber);
        return this;
    }

    public EventBuilder withIdentificationNumber(String idenyificationNumber){
        instance.setIdentificationNumber(idenyificationNumber);
        return this;
    }

    public EventBuilder withType(Event.Type type){
        instance.setType(type);
        return this;
    }


    public Event build(){
        return instance;
    }

    public EventBuilder end() {
        return this;
    }
}
