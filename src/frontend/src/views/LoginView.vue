<script>
export default {
  username: 'LoginView',
  data () {
    return {
      username: 'admin',
      password: 'admin'
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
    <h1>Login</h1>
    <br>
    <label for="login-username">Username:</label>
    <input v-model="username" name="login-username" type="text">
    <br>
    <label for="login-username">Password:</label>
    <input v-model="password" name="login-username" type="password">
    <br>
    <button @click="login" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full">Login</button>

    <br>
    <br>
    <router-link :to="{ name: 'register' }">Register</router-link>

    <br>
    <br>
    <!--    <a href="TaskManagerComponent.vue">Create Account</a>-->
  </div>

</template>
