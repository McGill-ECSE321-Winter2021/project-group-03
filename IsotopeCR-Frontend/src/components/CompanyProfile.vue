<template>
  <div id="companyProfile" class="container">
    <div class="inner_container shadow">
      <div class="large_text"><b>Company Information</b></div>

      <hr />

      <table id="companyProfiles" class="table">
        <th>CompanyName</th>
        <th>Address</th>
        <th>WorkingHours</th>
        <tr v-for="companyProfile in companyProfiles">
          <td>{{ companyProfile.companyName }}</td>
          <td>{{ companyProfile.address }}</td>
          <td>{{ companyProfile.workingHours }}</td>
        </tr>
      </table>

      <hr />

      <div class="input_container">
        <div class="small_text left_text brand_color_text">
          Registration Table
        </div>

        <table style="width: 90%">
          <tr>
            <th>CompanyName</th>
            <th>Address</th>
            <th>WorkingHours</th>
          </tr>
          <tr>
            <td>
              <input
                type="text"
                v-model="newCompanyProfile"
                placeholder="Company Name"
              />
            </td>
            <td>
              <input type="text" v-model="newAddress" placeholder="address" />
            </td>
            <td>
              <input
                type="text"
                v-model="newWorkingHours"
                placeholder="working hrs"
              />
            </td>
            <td>
              <button
                @click="
                  createCompanyProfile(
                    newCompanyProfile,
                    newAddress,
                    newWorkingHours
                  )
                "
              >
                Register
              </button>
            </td>
          </tr>
          <tr>
            <td></td>
            <td></td>
            <td></td>
            <td>
              <button @click="deleteCompanyProfile()">Delete</button>
            </td>
          </tr>
        </table>
        <hr />
        <span v-if="errorCompanyProfile" style="color: red">{{
          errorCompanyProfile
        }}</span>
      </div>

      <hr />
    </div>
  </div>
</template>

<script src="./companyProfile.js">
</script>

<style scoped>
</style>