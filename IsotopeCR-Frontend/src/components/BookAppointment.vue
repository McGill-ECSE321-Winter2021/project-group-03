<template>
  <div id="bookappointment" class="container">
    <button class="button" :disabled="isPrevDisabled()">Previous Week</button>
    <button class="button" :disabled="isDisabled('1')">Next Week</button>
    <b-button id="1" @click="isDisabled()">Next Week</b-button>
    <div></div>
    <table class="styled-table" @click="selectValue($event)">
      <thead>
        <tr>
          <th>Monday</th>
          <th>Tuesday</th>
          <th>Wednesday</th>
          <th>Thursday</th>
          <th>Friday</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>
            <div>
              <button class="button" :disabled="isDisabled('1')">Send</button>
            </div>
          </td>
          <td>
            <div>
              <b-button id="1" @click="isDisabled()">check</b-button>
            </div>
          </td>

          <td>9:00</td>
          <td>9:00</td>
          <td>9:00</td>
        </tr>
        <td>9:30</td>
        <td>9:30</td>
        <td>9:30</td>
        <td>9:30</td>
        <td>9:30</td>
        <tr>
          <td>10:00</td>
          <td>10:00</td>
          <td>10:00</td>
          <td>10:00</td>
          <td>10:00</td>
        </tr>
        <td>10:30</td>
        <td>10:30</td>
        <td>10:30</td>
        <td>10:30</td>
        <td>10:30</td>
        <tr>
          <td>11:00</td>
          <td>11:00</td>
          <td>11:00</td>
          <td>11:00</td>
          <td>11:00</td>
        </tr>
        <td>11:30</td>
        <td>11:30</td>
        <td>11:30</td>
        <td>11:30</td>
        <td>11:30</td>
        <tr>
          <td>12:00</td>
          <td>12:00</td>
          <td>12:00</td>
          <td>12:00</td>
          <td>12:00</td>
        </tr>
        <td>12:30</td>
        <td>12:30</td>
        <td>12:30</td>
        <td>12:30</td>
        <td>12:30</td>
        <tr>
          <td>1:00</td>
          <td>1:00</td>
          <td>1:00</td>
          <td>1:00</td>
          <td>1:00</td>
        </tr>
        <td>1:30</td>
        <td>1:30</td>
        <td>1:30</td>
        <td>1:30</td>
        <td>1:30</td>
        <tr>
          <td>2:00</td>
          <td>2:00</td>
          <td>2:00</td>
          <td>2:00</td>
          <td>2:00</td>
        </tr>
        <td>2:30</td>
        <td>2:30</td>
        <td>2:30</td>
        <td>2:30</td>
        <td>2:30</td>
        <tr>
          <td>3:00</td>
          <td>3:00</td>
          <td>3:00</td>
          <td>3:00</td>
          <td>3:00</td>
        </tr>
        <td>3:30</td>
        <td>3:30</td>
        <td>3:30</td>
        <td>3:30</td>
        <td>3:30</td>
        <tr>
          <td>4:00</td>
          <td>4:00</td>
          <td>4:00</td>
          <td>4:00</td>
          <td>4:00</td>
        </tr>
        <tr>
          <td>4:30</td>
          <td>4:30</td>
          <td>4:30</td>
          <td>4:30</td>
          <td>4:30</td>
        </tr>

        <!-- and so on... -->
      </tbody>
    </table>

    Selected: {{ selected }}

    <div class="input_container">
      <div class="small_text left_text brand_color_text">Vehicle:</div>
      <input
        type="text"
        class="input wide"
        v-model="license"
        placeholder="Liscence"
      />
      <div class="small_text left_text brand_color_text">Service:</div>
      <input
        type="text"
        class="input wide"
        v-model="serviceName"
        placeholder="Service"
      />
      <div class="small_text left_text brand_color_text">Start Time:</div>
      <input
        type="text"
        class="input wide"
        v-model="startTime"
        placeholder="Start Time"
      />
      <div class="small_text left_text brand_color_text">Date:</div>
      <input type="text" class="input wide" v-model="date" placeholder="date" />

      <span v-if="errorMessage" style="color: red">{{ errorMessage }} </span>
      <div>
        <button
          class="button brand_color wide"
          @click="createAppointment(license, serviceName, startTime, date)"
        >
          Book An Appointment
        </button>
      </div>
    </div>
    <span v-if="errorMessage" style="color: red">{{ errorMessage }} </span>
    <div>
      <button
        class="button brand_color wide"
        @click="createAppointment(license, serviceName, startTime, date)"
      >
        Book An Appointment
      </button>
    </div>
  </div>
</template>

<script src='./bookAppointment.js'>
export default {
  name: "BookAppointment",
  methods: {},
};
</script>

<style>
.styled-table {
  border-collapse: collapse;
  margin: 25px 0;
  font-size: 0.9em;
  font-family: sans-serif;
  min-width: 400px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
  background-color: white;
}

.styled-table th,
.styled-table td {
  padding: 12px 15px;
}
.styled-table tbody {
  border-bottom: 1px solid #dddddd;
}

.styled-table tbody tr {
  background-color: white;
}
.styled-table thead {
  background-color: #f3f3f3;
}
.button {
  background-color: white;
  font-family: sans-serif;
  border: none;
}
.button:disabled {
  color: rgb(214, 61, 61);
}
</style>