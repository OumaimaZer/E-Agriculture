# E-Agriculture Platform
## Project Description

The E-Agriculture Platform is designed to support farmers in Morocco by providing easy access to essential agricultural data and insights to optimize farming practices. The platform serves as a centralized web-based solution, offering local and international farmers a comprehensive set of tools and information for smarter agricultural decision-making.

This project was developed collaboratively by me, **Zerhouani Oumaima**, and two other contributors, **Hanane Malki** and **Aaya Qadry**, combining expertise in web development, IoT, and agricultural research.

Through the platform, users can:
-  **Access real-time and historical weather data:** Including temperature, precipitation, humidity, and other factors critical for crop success. The platform displays weather trends through intuitive charts, weekly forecasts, and hourly updates to help farmers plan their activities effectively.
-  **Monitor field conditions in real-time:** Using an ESP32 microcontroller with sensors, the platform provides live updates on temperature, soil moisture, and precipitation from the user’s farm. Users can also track historical data to analyze trends over time and optimize crop management.
-  **Receive personalized crop recommendations:** Based on regional climate data and historical weather records dating back to 1970, the platform suggests the most suitable crops for the current season and the user’s specific location. This helps farmers select crops that maximize productivity while reducing risk.
-  **Access detailed plant information:** By selecting a specific crop, users can obtain comprehensive details, including scientific classification, growth duration, and other critical agronomic information.

## Objectives

The platform aims to:
-  Facilitate access to accurate, relevant, and region-specific agricultural data.
-  Support research and informed decision-making in the agricultural sector.
-  Improve farmers’ practices by providing actionable insights and recommendations.
  
The E-Agriculture Platform combines weather monitoring, crop planning, and knowledge dissemination into a single, user-friendly interface, empowering farmers to enhance productivity and sustainability.

## Intefaces :

### User registration :
The user can start by creatin an account. He must select a location on the map, which will be saved in the system as a user's "agricultural zone" containing: the country name, city name, longitude, and latitude.
If the agricultural land exists in Morocco, a region attribute will be assigned to it.

<img width="1521" height="776" alt="image" src="https://github.com/user-attachments/assets/b155be91-7e62-4e8f-8d14-b6d7c3d8a7fd" />

## Home page :
The homepage offers users a sophisticated and engaging way to access weather data relevant to their agricultural area. Users can view data for their current location or explore other areas using the interactive map. The homepage provides a comprehensive weather overview, including four graphs displaying data from the past year, weekly weather forecasts, and hourly data for the current day.

<img width="1469" height="658" alt="image" src="https://github.com/user-attachments/assets/7d872040-1789-419f-8e0a-f2f7f32aac64" />
<img width="1443" height="823" alt="image" src="https://github.com/user-attachments/assets/36eae117-a209-4c13-a597-ef93ba5f74a3" />
<img width="1458" height="424" alt="image" src="https://github.com/user-attachments/assets/98438547-ba75-43e8-90e0-33a9e3416b24" />
<img width="1413" height="400" alt="image" src="https://github.com/user-attachments/assets/d783ab3a-c6b1-4d12-9813-3e4e02e513ec" />



## Monitoring page :
-  On the monitoring page, the user can add an ESP to their existing list of ESPs.
-  Display real-time weather data.
-  Display historical weather data stored in the system of a specific monitor.

<img width="1476" height="595" alt="image" src="https://github.com/user-attachments/assets/32687015-bcd8-4ed2-affd-63653aac3045" />

## Plant recommendations page :
This page allows the user to view plants by season and sector with a comparison method to determine if the climatic conditions of the agricultural area are favorable for each plant.

<img width="1473" height="703" alt="image" src="https://github.com/user-attachments/assets/22d9a20b-47f4-4226-ba3f-85b6c744df7f" />

If the agricultural area is in Morocco, the page will recommend sectors for the region of the agricultural land:

<img width="1496" height="539" alt="image" src="https://github.com/user-attachments/assets/55850e4e-f72f-4405-840a-441cbcdef91c" />

The user can select an agricultural area to view its details:

<img width="1458" height="676" alt="image" src="https://github.com/user-attachments/assets/f58ce611-05ff-447e-86cd-6861616bf623" />


## Conclusion :
This final-year project at the **École Supérieure de Technologie** was a highly enriching experience, guided by **Dr. Lahmer Mohammed**, whose expertise greatly contributed to its success. The project involved the design and development of a robust and user-friendly agricultural monitoring web application using Spring Boot and Thymeleaf.

The application provides farmers with practical tools such as real-time weather data, crop recommendations tailored to specific regions, and equipment monitoring, helping them optimize decisions and improve productivity. Designed to evolve, future improvements include expanding the plant database, developing mobile applications, and adding features to track planted crops over time.

Beyond the technical outcomes, the project deepened our understanding of key software development concepts like MVC architecture, separation of concerns, and framework integration. It also strengthened our practical and collaborative skills, preparing us for future professional challenges. We sincerely thank Dr. Lahmer Mohammed for his guidance throughout this journey.
