<template>
  <div>
    <a href="#">{{name}}</a>
    <div class="buttons">
      <button @click="handleRequest(name,true)" class="round-button">&#10003;</button>
      <button @click="handleRequest(name,false)" class="round-button">&#88;</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'RequestComponent',
  props: ['name'],
  methods: {
    handleRequest (username, answer) {
      console.log(username)
      console.log(answer)
      fetch('/api/friendrequest/proceedUser?userToAccept=' + username + '&requestAnswer=' + answer, {
        method: 'POST',
        body: JSON.stringify({ mainUsername: localStorage.getItem('user_token') })
      })
        .then(response => {
          this.$parent.finishRequest()
          // this.calendarOptions.events = this.calendarOptions.events.filter((e) => parseInt(e.id) !== parseInt(id))
        })
    }
  }
}
</script>

<style scoped>
.buttons{
  float: right;
  margin-right: 20px;
  transition: 0.3s;
  margin-top: 10px;
  display:inline-block;
  color: #818181;
}
</style>
