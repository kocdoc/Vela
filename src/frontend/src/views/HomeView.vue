<script>
// @ is an alias to /src
export default {
  name: 'HomeView',
  data () {
    return {
      test: []
    }
  },
  methods: {
    testMethod () {
      fetch('/api/taskmanager/getTasks?username=admin')
        .then((response) => { return response.json() })
        .then(data => {
          data.forEach(task => {
            console.log(task)
          })
        })
    }
  }
}
</script>

<template>
  <div class="home">
    <h1>Test</h1>
    <button @click="testMethod"></button>
  </div>
</template>
