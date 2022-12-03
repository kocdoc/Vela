<template>
<!--  <nav>-->
<!--    <router-link :to="{ name: 'taskmanager' }">Tasks</router-link>-->
<!--    <br>-->
<!--    <router-link :to="{ name: 'projects' }">Projekte</router-link>-->
<!--    <br>-->
<!--    <router-link :to="{ name: 'calendar' }">Kalender</router-link>-->
<!--    <br>-->
<!--    <router-link :to="{ name: 'friends' }">Freunde</router-link>-->
<!--    <br>-->
<!--    <router-link :to="{ name: 'profile' }">Profil</router-link>-->
<!--  </nav>-->
<!--  <FriendsView/>-->
  <SidebarComponent/>
  <router-view/>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
<script>
import SidebarComponent from '@/views/SidebarComponent'
export default {
  components: { SidebarComponent }
}
</script>
