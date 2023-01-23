<script>
export default {
  name: 'ProfileView',
  data () {
    return {
      profile: []
    }
  },
  methods: {
    logout () {
      localStorage.removeItem('user_token')
      localStorage.removeItem('username')
      this.$router.push('login')
    },
    editProject () {
      fetch('/api/profile/editUserData', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(this.profile)
      })
        .then(response => {
          if (response.status === 200) {
            alert('Profil wurde aktualisiert.')
          } else {
            alert('Ein Fehler ist aufgetreten. Bitte versuche es erneut')
          }
        })
    }
  },
  mounted () {
    if (localStorage.getItem('user_token') == null) {
      this.$router.push('login')
    }

    fetch('/api/profile/getUserData?user=' + localStorage.getItem('user_token'), {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' }
    })
      .then(response => response.json())
      .then(responseData => {
        console.log(responseData)
        this.profile = responseData
        // this.userProjectDetails = responseData
        // this.activeProject = responseData[0]
      })
  }

}
</script>

<style scoped>

</style>

<template>
  <h1>Profil</h1>
  <br>
  <br>
  <br>
  <label for="login-username">Benutzername:</label>
  <p name="login-username">{{profile.username}}</p>
  <label for="login-username">Passwort:</label>
  <input v-model="profile.password" name="login-username" type="password">
  <br>
  <label for="firstname">Vorname:</label>
  <input v-model="profile.firstname" id="firstname" type="text">
  <br>
  <label>Nachname:</label>
  <input v-model="profile.lastname" id="firstname" type="text">
  <br>
  <label>E-Mail:</label>
  <input v-model="profile.email" id="firstname" type="text">
  <br>
  <br>

  <button @click="editProject">Save changes</button>
  <br>
  <button @click="logout">Logout</button>
</template>
