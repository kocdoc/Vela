<template>
  <div class="next-to-sidebar">

    <form @submit.prevent="register">
      <h1 class="mb-4 text-4xl font-extrabold leading-none tracking-tight text-[#052A34] md:text-5xl lg:text-6xl dark:text-white">Registrierung</h1>
      <br>
      <div class="relative rounded-md">
        <label class="block font-medium text-gray-700 leading-5">Vorname</label>
        <input v-model="firstname" required name="login-username" type="text" class="w-64 form-input py-3 px-4 leading-5 rounded-md transition duration-150 ease-in-out bg-white border border-gray-300 placeholder-gray-500 focus:outline-none focus:border-blue-300 focus:shadow-outline-blue active:bg-gray-50 active:text-gray-800">
      </div>
      <br>
      <div class="relative rounded-md">
        <label class="block font-medium text-gray-700 leading-5">Nachname</label>
        <input v-model="lastname" required name="login-username" type="text" class="w-64 form-input py-3 px-4 leading-5 rounded-md transition duration-150 ease-in-out bg-white border border-gray-300 placeholder-gray-500 focus:outline-none focus:border-blue-300 focus:shadow-outline-blue active:bg-gray-50 active:text-gray-800">
      </div>
      <br>
      <div class="relative rounded-md">
        <label class="block font-medium text-gray-700 leading-5">Geburtsdatum</label>
        <input v-model="date_of_birth" required name="login-username" type="date" class="w-64 form-input py-3 px-4 leading-5 rounded-md transition duration-150 ease-in-out bg-white border border-gray-300 placeholder-gray-500 focus:outline-none focus:border-blue-300 focus:shadow-outline-blue active:bg-gray-50 active:text-gray-800">
      </div>
      <br>
      <div class="relative rounded-md">
        <label class="block font-medium text-gray-700 leading-5">E-Mail</label>
        <input v-model="email" type="email" required name="login-username" class="w-64 form-input py-3 px-4 leading-5 rounded-md transition duration-150 ease-in-out bg-white border border-gray-300 placeholder-gray-500 focus:outline-none focus:border-blue-300 focus:shadow-outline-blue active:bg-gray-50 active:text-gray-800">
      </div>
      <br>
      <div class="relative rounded-md">
        <label class="block font-medium text-gray-700 leading-5">Benutzername</label>
        <input v-model="username" required name="login-username" type="text" class="w-64 form-input py-3 px-4 leading-5 rounded-md transition duration-150 ease-in-out bg-white border border-gray-300 placeholder-gray-500 focus:outline-none focus:border-blue-300 focus:shadow-outline-blue active:bg-gray-50 active:text-gray-800">
      </div>
      <br>
      <label class="block font-medium text-gray-700 leading-5">Passwort</label>
      <input type="password" required @keyup="validatePassword" v-model="password" name="login-username" class="w-64 form-input py-3 px-4 leading-5 rounded-md transition duration-150 ease-in-out bg-white border border-gray-300 placeholder-gray-500 focus:outline-none focus:border-blue-300 focus:shadow-outline-blue active:bg-gray-50 active:text-gray-800">
      <div v-if="passwordError">
        {{passwordError}}
      </div>
      <br>
      <button type="submit" class="w-64 font-bold text-white bg-[#052A34] hover:bg-[#041D24] focus:ring-4 focus:ring-[#A7E6F7] font-medium rounded-lg text-sm px-10 py-2.5 mr-2 mb-2 dark:bg-black-600 dark:hover:bg-[#041D24] focus:outline-none dark:focus:ring-[#A7E6F7]">Registrieren</button>
      <br>
      <router-link :to="{ name: 'login' }" class="text-gray-400 hover:text-[#052A34] hover: bold">Ich besitze schon einen Account</router-link>
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
