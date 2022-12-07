<template>
  <div class="next-to-sidebar">

    <form @submit.prevent="register">
      <h1>Registrierung:</h1>
      <br>
      <label>Vorname:</label>
      <input type="text" v-model="firstname" required>
      <br>
      <label>Nachname:</label>
      <input type="text" v-model="lastname" required>
      <br>
      <label>Geburtsdatum:</label>
      <input type="date" v-model="date_of_birth" required>
      <br>
      <label>E-Mail:</label>
      <input type="email" v-model="email" required>
      <br>
      <label>Benutzername:</label>
      <input type="text" v-model="username" required>
      <br>
      <label>Passwort:</label>
      <input type="password" @keyup="validatePassword" v-model="password" required>
      <div v-if="passwordError">
        {{passwordError}}
      </div>
      <br>
      <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full">Registrieren</button>
      <br>
      <br>
      Ich besitze schon einen Account. <router-link :to="{ name: 'login' }">Login</router-link>
    </form>

  </div>
</template>

<script>
export default {
  username: 'RegisterView',
  data () {
    return {
      firstname: 'Matthias',
      lastname: 'Muchitsch',
      date_of_birth: '2022-12-13',
      email: 'matthias@test.at',
      username: 'matthias',
      password: '',
      passwordError: ''
    }
  },
  methods: {
    validatePassword () {
      this.passwordError = this.password.match('^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$')
        ? ''
        : 'Das Passwort muss mindestens 8 Zeichen lang sein, muss Klein- und Großbuchstaben, Zahlen und Sonderzeichen beinhalten!'
    },
    register () {
      this.validatePassword()

      if (!this.passwordError) {
        const userData = {
          firstname: this.firstname,
          lastname: this.lastname,
          date_of_birth: this.date_of_birth,
          email: this.email,
          username: this.username,
          password: this.password
        }

        fetch('/api/register', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(userData)
        })
          .then(response => {
            if (response.status === 200) {
              alert('Erfolgreich registriert!')
              this.$router.push('login')
            } else {
              alert('Benutzername ist schon vorhanden!')
            }
          })
      }
    }
  }
}
</script>

<style scoped>
.next-to-sidebar {
  margin-top: 50px;
  margin-left: 300px;
}
</style>
