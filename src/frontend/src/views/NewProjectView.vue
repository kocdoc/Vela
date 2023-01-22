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
            alert('Projekt ' + this.project_name + ' wurde erstellt')
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
</style>

<template>
  <div class="project-div">
    <p> Neues Projekt erstellen </p>

    Projektname: <input v-model="project_name" type="text"><br>
    Beschreibung: <input v-model="description" type="text">
    <br>
    <button @click="createNewProject">Neues Projekt erstellen</button>
  </div>
</template>
