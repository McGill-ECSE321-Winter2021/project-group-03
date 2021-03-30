import axios from 'axios'

var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port;
var backendUrl = 'http://' + config.build.backendHost + ':' + config.build.port;

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
    name: 'app',
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

                AXIOS.get(backendUrl+'/login', {
                    email,
                    password
                })

                .then(response => {
                    // JSON responses are automatically parsed.
                    this.profile = response.data
                    this.email = ''
                    this.password = ''
                    this.errorMessage = ''

                  })
                  .catch(e => {
                    // Set logged in tutor to empty
                    this.errorMessage = e.response.data.message

                });

            }

        }
    }

}
