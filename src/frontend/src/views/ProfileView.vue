<script>
export default {
  name: 'ProfileView',
  data () {
    return {
      profile: []
    }
  },
  methods: {
    logout () {
      localStorage.removeItem('user_token')
      localStorage.removeItem('username')
      this.$router.push('login')
    },
    editProject () {
      fetch('/api/profile/editUserData', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(this.profile)
      })
        .then(response => {
          if (response.status === 200) {
            console.log('Aktualisiert')
          } else {
            alert('Ein Fehler ist aufgetreten. Bitte versuche es erneut')
          }
        })
    }
  },
  mounted () {
    if (localStorage.getItem('user_token') == null) {
      this.$router.push('login')
    }

    fetch('/api/profile/getUserData?user=' + localStorage.getItem('user_token'), {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' }
    })
      .then(response => response.json())
      .then(responseData => {
        console.log(responseData)
        this.profile = responseData
        // this.userProjectDetails = responseData
        // this.activeProject = responseData[0]
      })
  }

}
</script>

<style scoped>
.next-to-sidebar{
  margin-top: 50px;
  margin-left: 300px;
}
</style>

<template>
  <div class="next-to-sidebar">
  <h1 class="mb-4 text-4xl font-extrabold leading-none tracking-tight text-[#052A34] md:text-5xl lg:text-6xl dark:text-white">Profil</h1>
  <br>
  <p name="login-username" class="text-4xl bold text-[#052A34]">{{profile.username}}</p>
  <br>
    <div class="relative rounded-md">
      <label class="block font-medium text-gray-700 leading-5">Passwort</label>
      <input v-model="profile.password" name="login-passwort" type="password" class="w-64 form-input py-3 px-4 leading-5 rounded-md transition duration-150 ease-in-out bg-white border border-gray-300 placeholder-gray-500 focus:outline-none focus:border-blue-300 focus:shadow-outline-blue active:bg-gray-50 active:text-gray-800">
    </div>
    <br>
    <div class="relative rounded-md">
      <label class="block font-medium text-gray-700 leading-5">Vorname</label>
      <input v-model="profile.firstname" id="firstname" name="firstname" type="text" class="w-64 form-input py-3 px-4 leading-5 rounded-md transition duration-150 ease-in-out bg-white border border-gray-300 placeholder-gray-500 focus:outline-none focus:border-blue-300 focus:shadow-outline-blue active:bg-gray-50 active:text-gray-800">
    </div>
    <br>
    <div class="relative rounded-md">
      <label class="block font-medium text-gray-700 leading-5">Nachname</label>
      <input v-model="profile.lastname" id="lastname"  name="lastname" type="text" class="w-64 form-input py-3 px-4 leading-5 rounded-md transition duration-150 ease-in-out bg-white border border-gray-300 placeholder-gray-500 focus:outline-none focus:border-blue-300 focus:shadow-outline-blue active:bg-gray-50 active:text-gray-800">
    </div>
    <br>
    <div class="relative rounded-md">
      <label class="block font-medium text-gray-700 leading-5">E-Mail</label>
      <input v-model="profile.email" id="email" name="email" type="email" class="w-64 form-input py-3 px-4 leading-5 rounded-md transition duration-150 ease-in-out bg-white border border-gray-300 placeholder-gray-500 focus:outline-none focus:border-blue-300 focus:shadow-outline-blue active:bg-gray-50 active:text-gray-800">
    </div>
    <br>
    <button @click="editProject" type="button" class="w-64 font-bold text-white bg-[#052A34] hover:bg-[#041D24] focus:ring-4 focus:ring-[#A7E6F7] font-medium rounded-lg text-sm px-10 py-2.5 mr-2 mb-2 dark:bg-black-600 dark:hover:bg-[#041D24] focus:outline-none dark:focus:ring-[#A7E6F7]">Save Changes</button>
    <br>
    <button @click="logout" type="button" class="text-center w-64 text-red-700 border border-red-700 hover:bg-red-700 hover:text-white focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm p-2.5 text-center inline-flex items-center mr-2 dark:border-red-500 dark:text-red-500 dark:hover:text-white dark:focus:ring-red-800">
      <svg class="fill-current w-4 h-4 mr-2" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M160 96c17.7 0 32-14.3 32-32s-14.3-32-32-32H96C43 32 0 75 0 128V384c0 53 43 96 96 96h64c17.7 0 32-14.3 32-32s-14.3-32-32-32H96c-17.7 0-32-14.3-32-32l0-256c0-17.7 14.3-32 32-32h64zM504.5 273.4c4.8-4.5 7.5-10.8 7.5-17.4s-2.7-12.9-7.5-17.4l-144-136c-7-6.6-17.2-8.4-26-4.6s-14.5 12.5-14.5 22v72H192c-17.7 0-32 14.3-32 32l0 64c0 17.7 14.3 32 32 32H320v72c0 9.6 5.7 18.2 14.5 22s19 2 26-4.6l144-136z"/></svg>
      <span>Logout</span>
    </button>
  </div>
</template>
