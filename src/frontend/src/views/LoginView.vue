<script>
export default {
  username: 'LoginView',
  data () {
    return {
      username: '',
      password: 'Passwort1!'
    }
  },
  methods: {
    login () {
      const loginData = {
        username: this.username,
        password: this.password
      }
      fetch('/api/login/', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(loginData)
      })
        .then((response) => {
          if (response.status === 200) {
            return response.json()
          } else {
            alert('Anmeldedaten nicht korrekt')
          }
        })
        .then(jsonData => {
          // console.log('Data' + jsonData)
          console.log('Token ' + jsonData.username)
          localStorage.setItem('user_token', jsonData.token)
          localStorage.setItem('username', jsonData.username)
          this.$router.push('taskmanager')
        })
    }
  }
}
</script>

<style>
  .next-to-sidebar{
    margin-top: 50px;
    margin-left: 300px;
  }
</style>

<template>
  <div class="next-to-sidebar">
    <h1 style="margin-top: -20px; margin-bottom: 30px" class="mb-4 text-4xl font-extrabold leading-none tracking-tight text-[#052A34] md:text-5xl lg:text-6xl dark:text-white">Login</h1>
    <div class="relative rounded-md">
      <label class="block font-medium text-gray-700 leading-5">Benutzername</label>
      <input v-model="username" name="login-username" class="w-64 form-input py-3 px-4 leading-5 rounded-md transition duration-150 ease-in-out bg-white border border-gray-300 placeholder-gray-500 focus:outline-none focus:border-blue-300 focus:shadow-outline-blue active:bg-gray-50 active:text-gray-800">
    </div>
    <br>
    <div class="relative rounded-md">
      <label class="block font-medium text-gray-700 leading-5">Passwort</label>
      <input v-model="password" name="login-username" type="password" class="w-64 form-input py-3 px-4 leading-5 rounded-md transition duration-150 ease-in-out bg-white border border-gray-300 placeholder-gray-500 focus:outline-none focus:border-blue-300 focus:shadow-outline-blue active:bg-gray-50 active:text-gray-800">
    </div>
    <br>
    <button @click="login" type="button" class="w-64 font-bold text-white bg-[#052A34] hover:bg-[#041D24] focus:ring-4 focus:ring-[#A7E6F7] font-medium rounded-lg text-sm px-10 py-2.5 mr-2 mb-2 dark:bg-black-600 dark:hover:bg-[#041D24] focus:outline-none dark:focus:ring-[#A7E6F7]">Login</button>
    <br>
    <router-link :to="{ name: 'register' }" class="text-gray-400 hover:text-[#052A34] hover: bold">Registrieren</router-link>

    <br>
    <br>

    <!--    <a href="TaskManagerComponent.vue">Create Account</a>-->
  </div>

</template>
