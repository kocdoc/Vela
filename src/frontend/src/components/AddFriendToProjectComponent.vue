<template>
  <div class="container" style="margin-top: 20px; margin-right: 10px">
    <p class = "friend" style="display: inline-block; margin-left: 20px; font-size: 22px; font-weight: normal; color: #e3e3e3 ">{{ name }}</p>
    <svg width="30" height="30" viewBox="0 0 680 512" class="text-[#052A34]" fill="white" style="margin-right: 5%; margin-left: auto" @click="onAddToProject(name, projectID)">
      <path d="M96 128a128 128 0 1 1 256 0A128 128 0 1 1 96 128zM0 482.3C0 383.8 79.8 304 178.3 304h91.4C368.2 304 448 383.8 448 482.3c0 16.4-13.3 29.7-29.7 29.7H29.7C13.3 512 0 498.7 0 482.3zM504 312V248H440c-13.3 0-24-10.7-24-24s10.7-24 24-24h64V136c0-13.3 10.7-24 24-24s24 10.7 24 24v64h64c13.3 0 24 10.7 24 24s-10.7 24-24 24H552v64c0 13.3-10.7 24-24 24s-24-10.7-24-24z"/>
    </svg>
  </div>
</template>

<script>
export default {
  name: 'AddFriendToProjectComponent',
  props: ['name', 'projectID'],
  methods: {
    onAddToProject (name, projectID) {
      console.log('Added to project')
      fetch('/api/project?id=' + projectID + '&username=' + name, {
        method: 'PATCH'
      })
        .then(response => {
          if (response.status === 200) {
            alert('Freund wurde zum Projekt hinzugefügt')
          } else {
            alert('Freund ist bereits im Projekt')
          }
        })
    }
  }
}
</script>

<style scoped>

</style>
