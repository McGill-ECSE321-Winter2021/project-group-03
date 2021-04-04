<template>
  <div id="bookappointment" class="container">
    <b-card title="Book Appointment" tag="article" class="card">
      <b-card-text>
        Book an appointment with one of our highly trained technicians!
      </b-card-text>
      <div class="text-label">Vehicle</div>
      <select v-model="licensePlate">
        <option v-for="vehicle in vehicles" v-bind:value="vehicle.licensePlate">
          {{ vehicle.licensePlate }}
        </option>
      </select>
      <div class="text-label">Service</div>
      <select v-model="serviceName">
        <option v-for="service in services" v-bind:value="service.serviceName">
          {{ service.serviceName }}
        </option>
      </select>
      <div class="text-label">
        In how many weeks do you want your appointment?
      </div>
      <input type="text" v-model="numWeeks" placeholder="0 for this week" />
      <div></div>
      <b-button href="#" variant="primary" class="card-button" @click="getUnavailableTimeslots()"
        >View Availabilities</b-button
      >
      <div></div>
      <span v-if="errorMessage" style="color: red">{{ errorMessage }} </span>
    </b-card>
    <b-card title="Week of" tag="article" class="card">
      <div>{{ weekStart }}</div>
    </b-card>

    <table class="styled-table">
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
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 1)"
            >
              09:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 2)"
            >
              09:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 3)"
            >
              09:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 4)"
            >
              09:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 5)"
            >
              09:30
            </button>
          </div>
        </td>
        <tr>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 1)"
              >
                10:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 2)"
              >
                10:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 3)"
              >
                10:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 4)"
              >
                10:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 5)"
              >
                10:00
              </button>
            </div>
          </td>
        </tr>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 1)"
            >
              10:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 2)"
            >
              10:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 3)"
            >
              10:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 4)"
            >
              10:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 5)"
            >
              10:30
            </button>
          </div>
        </td>
        <tr>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 1)"
              >
                11:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 2)"
              >
                11:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 3)"
              >
                11:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 4)"
              >
                11:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 5)"
              >
                11:00
              </button>
            </div>
          </td>
        </tr>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 1)"
            >
              11:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 2)"
            >
              11:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 3)"
            >
              11:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 4)"
            >
              11:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 5)"
            >
              11:30
            </button>
          </div>
        </td>
        <tr>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 1)"
              >
                12:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 2)"
              >
                12:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 3)"
              >
                12:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 4)"
              >
                12:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 5)"
              >
                12:00
              </button>
            </div>
          </td>
        </tr>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 1)"
            >
              12:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 2)"
            >
              12:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 3)"
            >
              12:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 4)"
            >
              12:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 5)"
            >
              12:30
            </button>
          </div>
        </td>
        <tr>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 1)"
              >
                13:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 2)"
              >
                13:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 3)"
              >
                13:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 4)"
              >
                13:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 5)"
              >
                13:00
              </button>
            </div>
          </td>
        </tr>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 1)"
            >
              13:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 2)"
            >
              13:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 3)"
            >
              13:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 4)"
            >
              13:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 5)"
            >
              13:30
            </button>
          </div>
        </td>
        <tr>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 1)"
              >
                14:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 2)"
              >
                14:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 3)"
              >
                14:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 4)"
              >
                14:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 5)"
              >
                14:00
              </button>
            </div>
          </td>
        </tr>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 1)"
            >
              14:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 2)"
            >
              14:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 3)"
            >
              14:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 4)"
            >
              14:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 5)"
            >
              14:30
            </button>
          </div>
        </td>
        <tr>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 1)"
              >
                15:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 2)"
              >
                15:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 3)"
              >
                15:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 4)"
              >
                15:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 5)"
              >
                15:00
              </button>
            </div>
          </td>
        </tr>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 1)"
            >
              15:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 2)"
            >
              15:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 3)"
            >
              15:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 4)"
            >
              15:30
            </button>
          </div>
        </td>
        <td>
          <div>
            <button
              class="button"
              :disabled="isDisabled('2')"
              @click="selectValue($event, 5)"
            >
              15:30
            </button>
          </div>
        </td>
        <tr>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 1)"
              >
                16:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 2)"
              >
                16:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 3)"
              >
                16:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 4)"
              >
                16:00
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 5)"
              >
                16:00
              </button>
            </div>
          </td>
        </tr>
        <tr>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 1)"
              >
                16:30
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 2)"
              >
                16:30
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 3)"
              >
                16:30
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 4)"
              >
                16:30
              </button>
            </div>
          </td>
          <td>
            <div>
              <button
                class="button"
                :disabled="isDisabled('2')"
                @click="selectValue($event, 5)"
              >
                16:30
              </button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <b-card title="Confirm Booking" tag="article" class="card">
      <div>Selected Time: {{ selected }}</div>
      <b-button
        href="#"
        variant="primary"
        class="card-button"
        @click="createAppointment(licensePlate, serviceName, startTime)"
      >
        Book Appointment
      </b-button>
    </b-card>
    <span v-if="errorMessage" style="color: red">{{ errorMessage }} </span>
  </div>
</template>

<script src='./bookAppointment.js'>
export default {
  name: "BookAppointment",
  methods: {},
};
</script>

<style>
.container {
  display: grid;
}
.card {
  margin-top: 1.5rem;
}
.styled-table {
  border-collapse: collapse;
  margin: 25px 0;
  font-size: 0.9em;
  font-family: "Avenir Black";
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
  font-size: 1rem;
}
.button {
  background-color: white;
  font-family: sans-serif;
  border: none;
}
.button:hover:enabled {
  color: rgb(47, 93, 219);
}
.button:disabled {
  color: rgb(214, 61, 61);
}

.card-button {
  margin-top: 1rem;
}

.text-label {
  margin-top: 1rem;
}

#appointmentInputs {
  padding-bottom: 5cm;
}
</style>