<template>
  <h1>Profile</h1>
  <button @click="logout">Logout</button>
</template>

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
