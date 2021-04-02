import axios from 'axios'

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

        login: function(email,password){
            if(email == "" || password == "") {
              this.errorMessage = 'Email or password cannot be empty.'
              return false
            } else {
                
                AXIOS.post(backendUrl+'/api/profile/login', {},{params:{
                    email: email,
                    password: password
                }})

                .then(response => {
                    // JSON responses are automatically parsed.
                    // this.profile = response.data
                    // this.email = ''
                    // this.password = ''
                    // this.errorMessage = ''
                    console.log(response)

                  })
                  .catch(e => {
                    console.log("hello")
                    console.log(e) 
                });

            }

        }
    }

}
