<template>
    <vuecal
    :events="events"
    selected-date="2018-11-19"
    :time-from="10 * 60"
    hide-weekends
    events-count-on-year-view
    events-on-month-view="short">
    </vuecal>
</template>

<script>

import {vuecal} from 'vue-cal'
import 'vue-cal/dist/vuecal.css'

export default {
    components: { 'vue-cal': vuecal },
    data: () => ({
    events: [
      {
        start: '2018-11-19 10:35',
        end: '2018-11-19 10:45',
        title: 'Doctor appointment'
      },
      {
        start: '2018-11-19 18:30',
        end: '2018-11-19 19:15',
        title: 'Dentist appointment'
      }
    ]
  })
}
</script>

<style>

</style>