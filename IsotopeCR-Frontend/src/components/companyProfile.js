import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.build.backendHost+":"+config.build.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


function CompanyProfileDTO(companyName, address, workingHours) {
   this.companyName = companyName
   this.address = address
   this.workingHours = workingHours
  }
  

  export default {
    name: 'companyProfile',
    data() {
      return {
        // courses: [],
        // courseName: '',
        // courseID: '',
        // level: '',
        // subject: '',
  
        companyProfiles: [],
        newCompanyProfile: '',
        errorCompanyProfile: '',
        response: []
      }
    },
    
    created: function() {
        AXIOS.get('/CompanyProfile/get')
            .then(response => {
                // JSON responses are automatically parsed.
                this.companyProfiles = response.data
            })
            .catch(e => {
                this.errorCompanyProfile = e
            })  
    },

    methods: { 
        createCompanyProfile: function (companyName, address, workingHours) {
        // CREATE a CompanyProfile
        AXIOS.post(backendUrl+'/CompanyProfile/create', {}, {
          params: {
            companyName: companyName,
            address: address,
            workingHours: workingHours
          }
        })
              .then(response => {
                // JSON responses are automatically parsed.
                this.companyProfiles.push(response.data)
                this.errorCompanyProfile = ''
                this.newCompanyProfile = ''
              })
              .catch(e => {
                var errorMsg = e.response.data.message
                console.log(errorMsg)
                this.errorPerson = errorMsg
              });
        }
  
    } // end of methods
  
  } // end of export default