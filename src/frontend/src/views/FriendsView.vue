<template>
  <h1>Friends</h1>
</template>

<script>
export default {
  name: 'FriendsView',
  mounted () {
    if (localStorage.getItem('user_token') == null) {
      this.$router.push('login')
    }
  }
}
</script>

<style scoped>

</style>
