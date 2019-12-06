SmartBuilding
===============

This is the implementation of following subject:

#Automation of the INSA's rooms management
You are requested to develop a Web application (Proof-of-Concept) for managing  INSA's rooms . This application must allow automatic closing windows, doors, turning on heating, turning off lights ... etc. This application relies on software services, sensors, and actuators. The goal is to retrieve data from sensors and analyze them to enable taking decisions. For instance, through software services, the application retreives data of temperature and presence sensors of rooms and temperature sensors from outside, and for example depending on the values of the data, actions can be triggered. Your application must be based on a service-oriented architecture.

An example of a scenario can be: the data of the temperature sensors of a given room are retrieved periodically. If the outside temperature is lower than the indoor temperature and the outside temperature is between 18 and 27°, the windows should be opened automatically. We can also consider the scenario of closing doors, windows and lights if the current time is outside working hours and there is nobody. However, if there are presence activities from 22h, an alarm must be triggered.

#Required work  :
* Design your application based on SOA architecture ( on a choisi de faire une API REST basée sur le framework SpringBoot)
* Implement the different services and services calls
* Implement a web interface for viewing the history of actions
* Tools and technologies to be used: Java, OM2M

#Overview:
Technologies used to do this project : SpringBoot, Maven.

![Alt text](SmartBuilding.png?raw=true "SmartBuilding Main Concepts")

#How to use:
* This project is made of several maven projects, each one of them representing a separated component.
* @TODO 
