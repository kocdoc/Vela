<template>
  <h1>Calender</h1>
</template>

<script>
export default {
  name: 'CalenderView',
  mounted () {
    if (localStorage.getItem('user_token') == null) {
      this.$router.push('login')
    }
  }
}
</script>

<style scoped>

</style>
