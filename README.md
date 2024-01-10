<div style="text-align: justify">

# Proyecto Final: Canary Travel Planner
##### Universidad de las Palmas de Gran Canaria
##### Curso 2023/2024
##### Asignatura: Desarrollo de Aplicaciones para Ciencias de Datos
##### Farid Sánchez Belmadi

***

### Summary of functionality

This Java application is designed to retrieve meteorological data from the eight Canary Islands and store them in a directory.
For this purpose, three modules are used: one for data capture (Weather Provider), another for storage (Event Store Builder), and a broker
that serves as an intermediary, managing the transmission of events between the preceding modules

****

### Resources used

This program has been developed through a Maven project in the IntelliJ IDEA development environment. Additionally, Git version
control tool and its web repository, GitHub, have been used to ensure the persistence of all modifications made.

ActiveMQ was used as a broker, an open-source message broker that implements the Java Message Service (JMS) API, providing a
reliable and scalable messaging solution. It supports various messaging patterns, including point-to-point and publish/subscribe,
and it enables the exchange of messages between different components in a decoupled manner.

On the following page, you will find the getting started guide and installation documentation for ActiveMQ, https://activemq.apache.org/getting-started

****

### Weather Prediction Provider
This Java application is designed to collect predictive weather data for the 8 Canary Islands every 6 hours. To achieve this, an
Open Weather Map REST API has been utilized, allowing us to retrieve predictive data for the next 4 days from the time
of the query, at 3-hour intervals. In this case, only predictions corresponding to 12 PM have been selected.

Once the predictions are obtained, they will be serialized in JSON format and sent to a message broker, also known as
an intermediary, which enables applications, systems, and services to communicate and exchange information. In this project,
a Publish/Subscribe messaging pattern is used, a message distribution pattern where the producer of each message publishes it
to a topic, and various message consumers subscribe to topics from which they want to receive messages. Therefore, all messages
published on a topic are distributed to all subscribed applications. It is a broadcast-type distribution method, where there is a
one-to-many relationship between the message publisher and its consumers.

#### Execution of the program
This program requires 3 arguments for its execution. Firstly, the API key to connect to the OpenWeatherMap API and obtain meteorological data,
the URL of the broker to which it will connect and finally the path where the file with the information of the
locations (locationName, island, latitude, longitude) in order to make requests to the API.

To run this program, access the terminal of your operating system, then navigate to the folder where the JAR is located, and finally, enter the following
command: **java -jar YourApp.jar arg1 arg2 arg3**.

#### Class diagram
![](WeatherPredictionProvider.jpg)


****

### Hotel Price Provider
This Java application is designed to collect information about the prices of a specific hotel for a particular day from different platforms where it is available every six hours. 
Subsequently, this information will be serialized in JSON format and sent to the topic of a broker.

#### Execution of the program
This program requires 2 arguments for its execution. Firstly, the URL of the broker to which it will connect and finally and secondly the path where the file with the information of the 
hotels is located (name, location, island, and code) in order to make requests to the API.

To run this program, access the terminal of your operating system, then navigate to the folder where the JAR is located, and finally, enter the following
command: **java -jar YourApp.jar arg1 arg2**.
#### Class diagram
![](HotelPriceProvider.jpg)



****

### Data-Lake Builder
This Java application will systematically store consumed events from a broker in a directory. The events will be stored in the following format, **{YYYYMMDD}.events**,
where **YYYYMMDD** is the year-month-day obtained from the event's timestamp in which the events associated with a specific day are stored and **.events** is the file extension.

#### Execution of the program
This program requires 2 arguments for its execution. Firstly, the URL of the broker to which it will connect and finally and secondly the path where the datalake will be created and
event will be stored.

To run this program, access the terminal of your operating system, then navigate to the folder where the JAR is located, and finally, enter the following
command: **java -jar YourApp.jar arg1 arg2**.

#### Class diagram
![](DatalakeBuilder.jpg)


****

### Best Weather and Hotel-Price Finder
This application will provide a list of hotels in the best climatic location of the selected Canary Island and the total price of the stay in each of the available hotels for that location during 
the specified range of days in the query, up to a maximum of 5 days.

#### Execution of the program
This program requires 2 arguments for its execution. Firstly, the URL of the broker to which it will connect and finally and secondly the path where the Datamart will be created, 
it is necessary to specify in that path the name of the datamart that we want to assign and that it ends with the extension **.db**, like this example, **C:/Users/Documents/datamart_name.db**.
Very important, it cannot run in the background as the terminal needs to be available to input the necessary parameters for the query.

To run this program, access the terminal of your operating system, then navigate to the folder where the JAR is located, and finally, enter the following
command: **java -jar YourApp.jar arg1 arg2**.

#### Class diagram
![](BestWeather_HotelPrice_Finder.jpg)


</div>
