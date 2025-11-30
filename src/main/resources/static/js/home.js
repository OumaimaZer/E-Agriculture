    
    var map = L.map('map').setView([31.7917, -7.0926], 5);

    L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);
    //search box
    L.Control.geocoder().addTo(map);

    var marker;

    function onMapClick(e) {
    if (marker) {
    map.removeLayer(marker);
    }

    marker = L.marker(e.latlng).addTo(map)
    .bindPopup("You clicked the map at " + e.latlng.toString()).openPopup();

    // Send location data to controller
    var latitude = e.latlng.lat ;
    var longitude = e.latlng.lng ; 
    const apiKey = 'd912178d0598502d5d6199eecf98e890';

    document.getElementById('latitude').value = latitude;
    document.getElementById('longitude').value = longitude;      


    fetch('https://api.openweathermap.org/data/2.5/weather?lat='+latitude+'&lon='+longitude+'&appid='+apiKey) 
    .then(function(resp) { return resp.json() }) // Convert data to json
    .then(function(data) {
    console.log(data);
    })
    }
    map.on('click', onMapClick);

    var ctx = document.getElementById('myChart').getContext('2d');
    var myChart = new Chart(ctx, {
    type: 'line',
    data: {
    labels: [],
    datasets: [{
    label: 'Temperature',
    data: [],
    borderColor: 'rgba(255, 99, 132, 1)',
    borderWidth: 1
    }, 
    {
    label: 'Humidity',
    data: [],
    borderColor: 'rgba(54, 162, 235, 1)',
    borderWidth: 1
    }, {
    label: 'Pressure',
    data: [],
    borderColor: 'rgba(75, 192, 192, 1)',
    borderWidth: 1
    }, {
    label: 'Precipitation',
    data: [],
    borderColor: 'rgba(153, 102, 255, 1)',
    borderWidth: 1
    }, {
    label: 'Rain',
    data: [],
    borderColor: 'rgba(255, 159, 64, 1)',
    borderWidth: 1
    }]
    },
    options: {}
    });

    var data = /*[[${history}]]*/[];
    console.log(data);
    var temps = [];
    var humidities = [];
    var pressures = [];
    var precipitations = [];
    var rains = [];
    for (var i = 0; i < data.length; i++) {
    console.log(data[i]);
    temps.push(data[i].temp);
    humidities.push(data[i].humidity);
    pressures.push(data[i].pressure);
    precipitations.push(data[i].precipitation);
    rains.push(data[i].rain);
    }
    //  myChart.data.labels = Array.from({length: 24}, (_, i) => new Date(new Date().setHours(new Date().getHours() - i)).toLocaleDateString());
    myChart.data.labels = Array.from({length: 10}, (_, i) => new Date(new Date().setDate(new Date().getDate() - i)).toLocaleDateString()).reverse();
    myChart.data.datasets[0].data = temps;
    myChart.data.datasets[1].data = humidities;
    myChart.data.datasets[2].data = pressures;
    myChart.data.datasets[3].data = precipitations;
    myChart.data.datasets[4].data = rains;
    myChart.update();

    //////////////////////////////CURRENT WEATHER///////////////////////////////
    var tableBody = document.getElementById("weatherTableBody");
    var weatherData = /*[[${current}]]*/[];

    for (var i = 0; i < weatherData.length; i++) {
    var weather = weatherData[i];
    var hour = (i % 24).toString().padStart(2, "0") + ":00"; // restart hour at 00:00 after 23:00
    var row = document.createElement("tr");
    row.innerHTML = "<td>" + hour + "</td>" +
                "<td>" + weather.temp + "</td>" +
                "<td>" + weather.humidity + "</td>" +
                "<td>" + weather.pressure + "</td>" +
                "<td>" + weather.precipitation + "</td>" +
                "<td>" + weather.rain + "</td>";
    tableBody.appendChild(row);
    }

    console.log("++++++++++++++++++++++++++++++++");
    