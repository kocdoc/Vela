<script>

export default {
  name: 'EditProjectView',
  data () {
    return {
      project_name: '',
      project_description: '',
      project_id: ''
    }
  },
  mounted () {
    this.project_name = localStorage.getItem('project_name')
    this.project_description = localStorage.getItem('project_description')
    this.project_id = localStorage.getItem('project_id')
  },
  methods: {
    saveProject () {
      // fetch('/api/project/editProject', {
      //   method: 'POST',
      //   headers: { 'Content-Type': 'application/json' },
      //   body: JSON.stringify({
      //     projectID: this.project_id,
      //     description: this.project_description,
      //     name: this.project_name
      //   })
      // })
      //   .then(response => {
      //     if (response.status === 200) {
      //       alert('Projekt aktualisiert')
      //     } else {
      //       alert('Aktualiserung fehlgeschlagen. Bitte versuche es erneut.')
      //     }
      //   })
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
    <h1 style="margin-top: -20px; margin-bottom: 30px" class="mb-4 text-4xl font-extrabold leading-none tracking-tight text-[#052A34] md:text-5xl lg:text-6xl dark:text-white">Projekt ändern</h1>

<!--    <p> Projekt ändern </p>-->

<!--    {{activeProject.projectID}}-->
    <div class="relative rounded-md" style="margin-bottom: 15px">
      <label class="block font-medium text-gray-700 leading-5">Projektname</label>
      <input v-model="project_name" required name="login-username" type="text" class="w-64 form-input py-3 px-4 leading-5 rounded-md transition duration-150 ease-in-out bg-white border border-gray-300 placeholder-gray-500 focus:outline-none focus:border-blue-300 focus:shadow-outline-blue active:bg-gray-50 active:text-gray-800">
    </div>

    <div class="relative rounded-md" style="margin-bottom: 15px">
      <label class="block font-medium text-gray-700 leading-5">Beschreibung</label>
      <input v-model="project_description" required name="login-username" type="text" class="w-64 form-input py-3 px-4 leading-5 rounded-md transition duration-150 ease-in-out bg-white border border-gray-300 placeholder-gray-500 focus:outline-none focus:border-blue-300 focus:shadow-outline-blue active:bg-gray-50 active:text-gray-800">
    </div>
<!--    Projektname: <input v-model="project_name" type="text"><br>-->
<!--    Beschreibung: <input v-model="project_description" type="text">-->
    <br>
    <button @click="saveProject" type="button" class="w-64 font-bold text-white bg-[#052A34] hover:bg-[#041D24] focus:ring-4 focus:ring-[#A7E6F7] font-medium rounded-lg text-sm px-10 py-2.5 mr-2 mb-2 dark:bg-black-600 dark:hover:bg-[#041D24] focus:outline-none dark:focus:ring-[#A7E6F7]">Änderungen speichern</button>

<!--    <button @click="saveProject">Änderungen speichern</button>-->
  </div>
</template>
