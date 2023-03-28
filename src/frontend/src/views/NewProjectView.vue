<script>

export default {
  name: 'NewProjectView',
  data () {
    return {
      project_name: '',
      description: ''
    }
  },
  methods: {
    createNewProject () {
      fetch('/api/project/addProject?user=' + localStorage.getItem('user_token'), {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          name: this.project_name, description: this.description
        })
      })
        .then(response => {
          if (response.status === 200) {
            console.log('Projekt erstellt')
            // alert('Projekt ' + this.project_name + ' wurde erstellt')
            this.$router.push('/projects')
          } else {
            alert('Projekterstellung fehlgeschlagen. Versuche es bitte erneut!')
          }
        })
    }
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
    <h1 style="margin-top: -20px; margin-bottom: 30px" class="mb-4 text-4xl font-extrabold leading-none tracking-tight text-[#052A34] md:text-5xl lg:text-6xl dark:text-white">Neues Projekt</h1>

    <div class="relative rounded-md" style="margin-bottom: 15px">
      <label class="block font-medium text-gray-700 leading-5">Projektname</label>
      <input v-model="project_name" required name="login-username" type="text" class="w-64 form-input py-3 px-4 leading-5 rounded-md transition duration-150 ease-in-out bg-white border border-gray-300 placeholder-gray-500 focus:outline-none focus:border-blue-300 focus:shadow-outline-blue active:bg-gray-50 active:text-gray-800">
    </div>

    <div class="relative rounded-md" style="margin-bottom: 15px">
      <label class="block font-medium text-gray-700 leading-5">Beschreibung</label>
      <input v-model="description" required name="login-username" type="text" class="w-64 form-input py-3 px-4 leading-5 rounded-md transition duration-150 ease-in-out bg-white border border-gray-300 placeholder-gray-500 focus:outline-none focus:border-blue-300 focus:shadow-outline-blue active:bg-gray-50 active:text-gray-800">
    </div>

<!--    Projektname: <input v-model="project_name" type="text"><br>-->
<!--    Beschreibung: <input v-model="description" type="text">-->
    <br>
    <button @click="createNewProject" type="button" class="w-64 font-bold text-white bg-[#052A34] hover:bg-[#041D24] focus:ring-4 focus:ring-[#A7E6F7] font-medium rounded-lg text-sm px-10 py-2.5 mr-2 mb-2 dark:bg-black-600 dark:hover:bg-[#041D24] focus:outline-none dark:focus:ring-[#A7E6F7]">Neues Projekt erstellen</button>
<!--    <button @click="createNewProject">Neues Projekt erstellen</button>-->
  </div>
</template>
