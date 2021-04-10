# MVI Clean Architecture

Work in progress...

### Clean Architecture Description and Implementation

This project is my attempt to write a modular Android app using MVI Clean Architecture

The architecture is based on Robert C. Martin's (Uncle Bob) original [Clean Architecture blog post](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) and accompanying schematic diagram. It stresses a design which is based on inversion of control and enforcing inner layers knowing nothing about how they are being used in the outer layers. This is composed of 3 different layers:

* Data (Web API responses, Repository and Datasources plus other interfaces)
* Domain (Entities, Use Cases, Interfaces for flow of control implemented by Data layer)
* Presentation (Activities, Fragments, ViewModel)

Domain contains the Entities which in this case are data classes with app wide logic and data. Use cases are the business operations on the Entities which are invoked by the presentation layer for display and fulfilled by the data layer. The domain has interfaces which are implemented by outer layers such as MovieRepository and data structures of UseCase Input and UseCase Output to define the boundaries between the domain and other layers.
The presentation and data layers are only aware of the domain layer and not each other. Mappers are used by the data layer to convert API responses, database results etc. from the data layer to the domain layer.

