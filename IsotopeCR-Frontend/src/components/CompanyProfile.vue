<template>

    <div id="companyProfile" class="container">
    <div class="inner_container shadow">

    <div class="large_text">Company Information</div>

    <hr>
   
    <table id="companyProfiles" class="table">
      <th>CompanyName</th>
      <th>Address</th>
      <th>WorkingHours</th>
      <tr v-for="companyProfile in companyProfiles">
        <td>{{ companyProfile.CompanyName }}</td>
        <td>{{ companyProfile.address }}</td>
        <td>{{ companyProfile.WorkingHours }}</td>
      </tr>
    </table>
   
    <hr>

      <div class="input_container">

        <div class="small_text left_text brand_color_text">Registration Table</div>
        
        <table style="width:80%">
        <tr>
            <th>CompanyName</th>
            <th>Address</th>
            <th>WorkingHours</th>
        </tr>
        <tr>
            <td>
                <input type="text" v-model="CompanyName" placeholder="Company Name">
            </td>
            <td>
                <input type="text" v-model="address" placeholder="address">
            </td>
            <td>
                <input type="text" v-model="WorkingHours" placeholder="working hrs">
            </td>
            <td>
                <button  @click="createCompanyProfile(CompanyName, address, WorkingHours)">Register</button>
            </td>
        </tr>
       
        </table>


        <hr>

        <span v-if="errorCompanyProfile" style="color:red">{{ errorCompanyProfile }} </span>

      </div>
      
      <hr>

    </div> 

  </div>

</template>

<script src="./companyProfile.js">

</script>

<style scoped>
</style>