# Simple DSL example file
[keyword]avoid looping=no-loop true
[when]There is an Event=$c:Event()
[when]- with type {type:\w*} = type == Event.Type.{type}
[when]- with registrationNumber set = registrationNumber != null
[when]- with identificationNumber set = identificationNumber != null
[then]Enrich with Vehicle data = EventToBeEnrichWithVehicle ewVehicle = don($c, EventToBeEnrichWithVehicle.class);
[then]Enrich with Driver data = EventToBeEnrichWithDriver ewDriver = don($c, EventToBeEnrichWithDriver.class);