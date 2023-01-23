<script>

export default {
  name: 'EditProjectView',
  data () {
    return {
      project_name: 'Test',
      description: 'sdg',
      activeProject: 'noch nichts'
    }
  },
  mounted () {
    this.project = localStorage.getItem('active_project')
  },
  methods: {
    saveProject () {

    }
  }
}

</script>

<style scoped>
</style>

<template>
  <div class="project-div">
    <p> Projekt ändern </p>

    {{activeProject.projectID}}

    Projektname: <input v-model="project_name" type="text"><br>
    Beschreibung: <input v-model="description" type="text">
    <br>
    <button @click="createNewProject">Änderungen speichern</button>
  </div>
</template>
