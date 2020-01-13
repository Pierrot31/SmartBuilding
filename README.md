SmartBuilding
===============

This is the implementation of following subject:

# Automation of the INSA's rooms management

You are requested to develop a Web application (Proof-of-Concept) for managing  INSA's rooms . This application must allow automatic closing windows, doors, turning on heating, turning off lights ... etc. This application relies on software services, sensors, and actuators. The goal is to retrieve data from sensors and analyze them to enable taking decisions. For instance, through software services, the application retreives data of temperature and presence sensors of rooms and temperature sensors from outside, and for example depending on the values of the data, actions can be triggered. Your application must be based on a service-oriented architecture.

An example of a scenario can be: the data of the temperature sensors of a given room are retrieved periodically. If the outside temperature is lower than the indoor temperature and the outside temperature is between 18 and 27°, the windows should be opened automatically. We can also consider the scenario of closing doors, windows and lights if the current time is outside working hours and there is nobody. However, if there are presence activities from 22h, an alarm must be triggered.

# Required work  :

* Design your application based on SOA architecture ( on a choisi de faire une API REST basée sur le framework SpringBoot)
* Implement the different services and services calls
* Implement a web interface for viewing the history of actions
* Tools and technologies to be used: Java, OM2M

# Overview:

Technologies used to do this project : SpringBoot, Maven.

![Alt text](SmartBuilding.png?raw=true "SmartBuilding Main Concepts")

# How to use:

This project is made of a core service called smartbuilding and requires a browser and a OM2M infrastructure node running with default credentials (admin:admin) :
* Import in eclipse the maven project from this repository 
* First, launch your om2m Infrastructure node and make sure that you've cleaned it's database before (*mandatory*). In order to do that, delete DB files under the main directory of OM2M (rm database/indb.*)
* Secondly, launch the smartbuilding app : at that stage you have om2m listening on 8080 and the smartbuilding on 8000
* Call with curl commandline the API listening on port 8000 to manage our virtual building by hand, or open smartbuilding-gui/index.html inside a browser for javascript control.
* When using the javascript interface :
** Create rooms only with [a-zA-Z0-9]
** When setting outside temperatures and brightness, fill both input forms with numerical value before submitting
** When setting target temperatures and brightness, make sure you've set "outside" parameters before, and fill both input forms with numerical value before submitting

Curl can be used to make direct calls to the REST API:
```bash
curl -XPOST -H "Content-type: application/json" -d '{"name": "Room01"}' '127.0.0.1:8000/addroom/'
curl -XPOST -H "Content-type: application/json" -d '{"name": "Room02"}' '127.0.0.1:8000/addroom/'
curl -XPUT -H "Content-type: application/json" '127.0.0.1:8000/outside/0/1000/'
curl -XPUT -H "Content-type: application/json" '127.0.0.1:8000/targettemp/30'
curl -XPUT -H "Content-type: application/json" '127.0.0.1:8000/targetbrightness/500'
curl -XGET -H "Content-type: application/json" '127.0.0.1:8000/logs/'
# ...
```

# Here is what the GUI should look like:

![Alt text](gui-sample.png?raw=true "GUI display example")

# Here is what is corresponding inside OM2M:

![Alt text](om2m-sample.png?raw=true "GUI display example")
