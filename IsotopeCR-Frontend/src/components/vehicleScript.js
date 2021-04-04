import axios from "axios"
var config = require("../../config")
var frontendUrl = "http://" + config.dev.host + ':' + config.dev.port
var backendUrl = "http://" + config.build.backendHost
var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { "Access-Control-Allow-Origin": frontendUrl },
})

function VehicleDto(licensePlate, year, model, brand) {
    this.licensePlate = licensePlate
    this.year = year
    this.model = model
    this.brand = brand
}

export default {
    name: 'createcustomerprofile',
    data() {
        return {
            vehicles: [],
            email: '',
            licensePlate: '',
            year: '',
            model: '',
            brand: '',
            errorVehicle: '',
            response: []
        }
    },

    methods: {
        displayVehicle: function (email) {
            AXIOS.get('/api/profile/customer/vehicle/get-all/' + email)
                .then(response => {
                    this.vehicles = response.data
                    alert("Vehicles displayed!")
                })
                .catch(e => {
                    if (e.response) {
                        console.log(e.response.data)
                        console.log(e.response.status)
                    }
                    this.errorVehicle = e.response.data
                })
        },

        createVehicle: function (email, licensePlate, year, model, brand) {
            if (email == "") {
                this.errorVehicle = "Please enter your email";
            } else if (licensePlate == "") {
                this.errorVehicle = "Please enter your licence plate";
            } else if (year == "") {
                this.errorVehicle = "Please enter your vehicle year";
            } else if (model == "") {
                this.errorVehicle = "Please enter your vehicle model";
            } else if (brand == "") {
                this.errorVehicle = "Please enter your vehicle brand";
            } else {
                AXIOS.post(backendUrl + '/api/profile/customer/vehicle/create/' + email, {}, {
                    params: {
                        licensePlate: licensePlate,
                        year: year,
                        model: model,
                        brand: brand
                    }
                })
                    .then(
                        (response) => {
                            console.log(response.data)
                            this.vehicles.push(response.data)
                            this.errorVehicle = ''
                        }
                    )
                    .catch(e => {
                        console.log(e)
                        if (e.response) {
                            console.log(e.response.data)
                            console.log(e.response.status)
                        }
                        if(e.response.data.length > 30){
                            this.errorVehicle = "Duplicate LicensePlate"
                        } else {
                            this.errorVehicle = "Please check your input according to hint"
                        }
                    });
            }
        },
    }
}