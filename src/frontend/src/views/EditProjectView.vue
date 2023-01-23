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
</style>

<template>
  <div class="project-div">
    <p> Projekt ändern </p>

<!--    {{activeProject.projectID}}-->

    Projektname: <input v-model="project_name" type="text"><br>
    Beschreibung: <input v-model="project_description" type="text">
    <br>
    <button @click="saveProject">Änderungen speichern</button>
  </div>
</template>
