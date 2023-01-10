<script>
export default {
  name: 'ProfileView',
  methods: {
    logout () {
      localStorage.removeItem('user_token')
      localStorage.removeItem('username')
      this.$router.push('login')
    }
  },
  mounted () {
    if (localStorage.getItem('user_token') == null) {
      this.$router.push('login')
    }
  }
}
</script>

<style scoped>

</style>

<template>
  <h1>Profile</h1>
  <button @click="logout">Logout</button>

  <br>
  <label for="firstname">Vorname:</label>
  <p id="firstname">Tobias</p>
  <br>
  <label>Nachname:</label>
  <p>Tobias</p>
  <br>
  <label>Geburtsdatum:</label>
  <p>Tobias</p>
  <br>
  <label>E-Mail:</label>
  <p>Tobias</p>
  <br>
  <label>Benutzername:</label>
  <p>Tobias</p>
  <br>
  <label>Passwort:</label>
  <p>Tobias</p>
</template>
