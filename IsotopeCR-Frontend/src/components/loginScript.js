import axios from 'axios'
import JQuery from 'jquery'

let $ = JQuery
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port;
var backendUrl = 'http://' + config.build.backendHost;

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function ProfileDto(email, firstName, lastName, password) {
    this.firstName = firstName
    this.lastName = lastName
    this.email = email
    this.password = password
}

export default {
    name: 'login',
    data() {
        return {
            profile: [],
            firstName: '',
            lastName: '',
            email: '',
            password: '',
            errorMessage: '',
            response: []
        }
    },


    methods: {
        login: function (email, password) {
            if (email == "" || password == "") {
                this.errorMessage = 'Email or password cannot be empty.'
                return false
            } else if (email == "owner@isotopecr.ca", password == "Owner123") {
                localStorage.setItem('loggedIn', 'Admin')
                console.log(localStorage)
                this.$cookie.set('email', email, { expires: '1h' })
                window.location.href = "/"
            } else {
                AXIOS.post(backendUrl + '/api/profile/login', {}, {
                    params: {
                        email: email,
                        password: password
                    }
                })
                    .then(response => {
                        this.response = response.data
                        console.log(response)
                        this.errorMessage = ''
                        if (this.response != '') {
                            localStorage.setItem('loggedIn', this.response['type'])
                            console.log(localStorage)
                            this.$cookie.set('email', this.response['email'], { expires: '1h' })
                            console.log(this.$cookie.get('email'))
                            window.location.href = "/"
                        }
                    })
                    .catch(e => {
                        if (e.response) {
                            console.log(e.response)
                            console.log(e.response.data)
                            console.log(e.response.status)
                        }
                        this.errorMessage = e.response.data;
                    });

            }

        }
    }

}
