package com.dxc.bankia.event;

import java.util.Date;
import com.dxc.bankia.model.Vehicle;
import com.dxc.bankia.model.Event;
import com.dxc.bankia.services.FinderService;

global FinderService finderService;

declare trait EventToBeEnrichWithVehicle
    registrationNumber:String
    vehicle:Vehicle
end

declare trait EventToBeEnrichWithDriver
    identificationNumber:String
    driver:Driver
end

declare trait EventEnrichedWithVehicle
    registrationNumber:String
    vehicle:Vehicle
end

declare trait EventEnrichedWithDriver
    identificationNumber:String
    driver:Driver
end

declare trait EventNotEnriched end

declare trait EventNotEnrichedWithVehicle extends EventNotEnriched
    registrationNumber:String
end

declare trait EventNotEnrichedWithDriver extends EventNotEnriched
    identificationNumber:String
end

rule "Enrich Event with vehicle data"
no-loop true
salience 100
when
    $c: EventToBeEnrichWithVehicle()
    $v:Vehicle() from finderService.getVehicleByRegistrationNumber($c.registrationNumber)
then
    EventEnrichedWithVehicle event = don($c, EventEnrichedWithVehicle.class);
    shed( $c, EventToBeEnrichWithVehicle.class );
    modify ($c){ setVehicle($v))};
end


rule "Enrich Event with driver data"
no-loop true
salience 100
when
    $c: EventToBeEnrichWithDriver()
    $v:Driver() from finderService.getDriverByIdentificationNumber($c.identificationNumber)
then
    EventEnrichedWithDriver event = don($c, EventEnrichedWithDriver.class);
    shed( $c, EventToBeEnrichWithDriver.class );
    modify ($c){ setDriver($v))};
end


rule "Not Enrich Event with vehicle data"
no-loop true
salience -100
when
    $c: EventToBeEnrichWithVehicle()
then
    EventNotEnrichedWithVehicle event = don($c, EventNotEnrichedWithVehicle.class);
    shed( $c, EventToBeEnrichWithVehicle.class );
end

rule "Not Enrich Event with driver data"
no-loop true
salience -100
when
    $c: EventToBeEnrichWithDriver()
then
    EventNotEnrichedWithDriver event = don($c, EventNotEnrichedWithDriver.class);
    shed( $c, EventToBeEnrichWithDriver.class );
end

rule "Send Event Not Enriched to Error Channel"
no-loop true
when
$so: EventNotEnriched()
then
     channels["error-channel"].send((Event)$so.getCore());
end



