<script>
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'

export default {
  components: {
    FullCalendar // make the <FullCalendar> tag available
  },
  data () {
    var self = this
    return {
      calendarOptions: {
        selectable: true,
        plugins: [dayGridPlugin, interactionPlugin],
        initialView: 'dayGridMonth',
        weekends: true, // initial value
        weekNumberCalculation: 'ISO',
        locale: 'de',
        eventColor: '#052A34',
        events: [],
        customButtons: {
          // addEvent: {
          //   text: 'Add Event',
          //   click: function () {
          //     self.addNewEvent()
          //     // calendarOptions.events.push('Test','2023-02-11')
          //   }
          // }
        },
        dateClick: function (info) {
          // info.dayEl.style.backgroundColor = 'red'
          self.onDateClick(info.dateStr)
        },
        eventClick: function (info) {
          // console.log(info)
          // alert(info.event.id)
          document.getElementById('title_input').value = info.event.title
          document.getElementById('date_input').value = info.event.startStr
          document.getElementById('description_input').value = '...'
          document.getElementById('eventID').innerText = info.event.id

          // document.getElementById('saveEvent').click = self.saveEvent(info.event.id)
          // document.getElementById('removeEvent').click = self.removeEvent(info.event.id)
          self.openNav()
        },
        headerToolbar: {
          left: '',
          center: 'title',
          right: 'prev,next'
        },
        height: 650

      }
    }
  },
  mounted () {
    fetch('/api/calendar/?user=' + localStorage.getItem('user_token'), {})
      .then(response => response.json())
      .then(responseData => {
        responseData.forEach(e => {
          const event = { id: e.eventId, title: e.title, date: e.date, startStr: e.date, description: '', username: localStorage.getItem('user_token') }
          this.calendarOptions.events.push(event)
        })
      })
  },
  methods: {
    onDateClick (dateStr) {
      const body = JSON.stringify({
        date: dateStr,
        title: ''
      })

      fetch('/api/calendar/?user=' + localStorage.getItem('user_token'), {
        method: 'POST',
        body: body,
        headers: { 'Content-Type': 'application/json' }
      })
        .then(response => response.json())
        .then(e => {
          const event = { id: e.eventId, title: e.title, date: e.date, startStr: e.date, description: '', username: localStorage.getItem('user_token') }
          this.calendarOptions.events.push(event)
        })
    },
    openNav () {
      document.getElementById('mySidebar').style.width = '450px'
    },
    closeNav () {
      document.getElementById('mySidebar').style.width = '0'
    },
    removeEvent () {
      const id = document.getElementById('eventID').innerText

      fetch('/api/calendar?id=' + id, {
        method: 'DELETE'
      })
        .then(response => {
          if (response.status === 200) {
            console.log(id)

            this.calendarOptions.events = this.calendarOptions.events.filter((e) => parseInt(e.id) !== parseInt(id))

            // this.calendarOptions.events = []
            // fetch('/api/calendar/?user=' + localStorage.getItem('user_token'), {})
            //   .then(response => response.json())
            //   .then(responseData => {
            //     responseData.forEach(e => {
            //       const event = { id: e.eventId, title: e.title, date: e.date, startStr: e.date, description: '', username: localStorage.getItem('user_token') }
            //       this.calendarOptions.events.push(event)
            //     })
            //   })
          }
        })

      // ToDo find way to remove event from array by id
      // call the delete and update methods from the backend
      // implement a date picker in the sidebar

      // this.calendarOptions.events.remove(e => parseInt(e.id) === parseInt(id))

      // const event = this.calendarOptions.events.find(e => parseInt(e.id) === parseInt(id))
      // this.calendarOptions.getEventById(id)
      // console.log(event)

      //
      // this.calendarOptions.events = this.calendarOptions.events.map(e => e.id !== id ? u : editedUser);
      // this.calendarOptions.events.
      // this.getEventById(id)
      this.closeNav()
    },
    saveEvent () {
      const id = document.getElementById('eventID').innerText
      const event = this.calendarOptions.events.find(e => parseInt(e.id) === parseInt(id))

      event.title = document.getElementById('title_input').value
      event.description = document.getElementById('description_input').value
      event.date = document.getElementById('date_input').value

      // ToDo fetch update Event
      const body = JSON.stringify({
        eventId: id,
        title: event.title,
        description: event.description,
        date: event.date
      })

      fetch('/api/calendar', {
        method: 'PUT',
        body: body,
        headers: { 'Content-Type': 'application/json' }
      })
        .then(response => response.json())
        .then(e => {
          console.log(e)
        })

      this.calendarOptions.events.push(event)

      this.closeNav()
    }
  }
}
</script>

<style>
.calendarDiv {
  padding-top: 20px;
  width: 80%;
  margin-left: auto;
  margin-right: 0;
  padding-right: 50px;
}

/* The sidebar menu */
.sidebar {
  height: 100%; /* 100% Full-height */
  width: 0; /* 0 width - change this with JavaScript */
  position: fixed; /* Stay in place */
  z-index: 1; /* Stay on top */
  top: 0;
  right: 0;
  background-color: #052A34; /* Black*/
  overflow-x: hidden; /* Disable horizontal scroll */
  padding-top: 60px; /* Place content 60px from the top */
  transition: 0.5s; /* 0.5 second transition effect to slide in the sidebar */
}

/* The sidebar links */
.sidebar a {
  padding: 8px 8px 8px 32px;
  text-decoration: none;
  font-size: 25px;
  color: #818181;
  display: block;
  transition: 0.3s;
}

/* When you mouse over the navigation links, change their color */
.sidebar a:hover {
  color: #f1f1f1;
}

/* Position and style the close button (top right corner) */
.sidebar .closebtn {
  position: absolute;
  top: 0;
  right: 25px;
  font-size: 36px;
  margin-left: 50px;
}

/* The button used to open the sidebar */
.openbtn {
  font-size: 20px;
  cursor: pointer;
  background-color: #052A34;
  color: white;
  padding: 10px 15px;
  border: none;
  width: 1200px;
}

.openbtn:hover {
  background-color: #444;
}
</style>

<template>
 <FullCalendar class="calendarDiv" :options="calendarOptions"/>
  <div id="mySidebar" class="sidebar">
    <div style="padding-left: 30px">
      <a class="closebtn" @click="closeNav">&times;</a>
      <p style="color: white">Title:</p><input id="title_input" type="text">
      <p style="color: white">Date:</p><input id="date_input" type="text">
      <p style="color: white">Description:</p><input id="description_input" type="text">
      <br><br>

      <button style="background-color: white; color: black" @click="saveEvent">Save event</button>
      <br>
      <button style="background-color: white; color: black" @click="removeEvent">Delete event</button>

      <p id="eventID" hidden></p>

    </div>
  </div>
</template>
