<template>
  <h1>Project</h1>
</template>

<script>
export default {
  name: 'ProjectView',
  mounted () {
    if (localStorage.getItem('user_token') == null) {
      this.$router.push('login')
    }
  }
}
</script>

<style scoped>

</style>
