<script>
// import TaskManagerView from '@/views/TaskManagerView'
import moment from 'moment/moment'
export default {
  name: 'ProjectView',
  data () {
    return {
      hideCompleted: true,
      todos: [],
      sortType: 'title',
      activeProject: 'Vela',
      // userProjects: []
      userProjects: ['Vela', 'POS', 'Mathe']
    }
  },
  computed: {
    filteredTodos () {
      return this.hideCompleted
        ? this.todos.filter(t => !t.done)
        : this.todos
    }
  },
  mounted () {
    if (localStorage.getItem('user_token') == null) {
      this.$router.push('login')
    }

    fetch('/api/taskmanager/getTasks?username=' + localStorage.getItem('username'), {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify('sortType: ' + this.sortType)
    })
      .then((response) => { return response.json() })
      .then(data => {
        data.forEach(task => {
          if (task.finishedDate != null) {
            console.log(task)
            task.done = true
          }
          this.todos.push(task)
        })
      })
  },
  methods: {
    createNewProject () {
      alert('New Project')
      fetch('/api/project/addProject?username=' + localStorage.getItem('username'), {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name: 'Projekt XY', description: 'gsfgfdjkgdf gjkdf gdfg kdf ' })
      })
        .then(response => response.json())
        .then(jsonData => console.log(jsonData))
    },
    sortTodos (sortType) {
      this.sortType = sortType
      switch (sortType) {
        case 'title':
          console.log('Hier bin ich')
          this.todos.sort((a, b) => {
            const t1 = a.title.toLowerCase()
            const t2 = b.title.toLowerCase()
            if (t1 < t2) {
              return -1
            }
            if (t1 > t2) {
              return 1
            }
            return 0
          })
          console.log(this.todos)
          break
        case 'category':
          this.todos.sort((a, b) => {
            const c1 = a.category.toLowerCase()
            const c2 = b.category.toLowerCase()
            if (c1 < c2) {
              return -1
            }
            if (c1 > c2) {
              return 1
            }
            return 0
          })
          console.log(this.todos)
          break
        case 'deadline':
          this.todos.sort((a, b) => {
            const d1 = a.deadline
            const d2 = b.deadline
            if (d1 < d2) {
              return -1
            }
            if (d1 > d2) {
              return 1
            }
            return 0
          })
          console.log(this.todos)
          break
      }
    },
    addTodo () {
      const newTask = { taskID: null, title: '', category: '', deadline: null, finishedDate: null, project: null, user: null }

      fetch('/api/taskmanager/addTask?username=' + localStorage.getItem('username'), {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newTask)
      })
        .then(response => response.json())
        .then(jsonData => this.todos.push(jsonData))
    },
    removeTodo (todo) {
      fetch('/api/taskmanager/deleteTask?username=' + localStorage.getItem('username'), {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(todo)
      })
        .then((response) => { return console.log(response) })
      this.todos = this.todos.filter((t) => t !== todo)
    },
    updateTask (todo) {
      console.log('Update:' + JSON.stringify(todo))
      fetch('/api/taskmanager/updateTask?username=' + localStorage.getItem('username'), {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(todo)
      })
    },
    onFinished (todo) {
      if (todo.finishedDate == null) {
        todo.finishedDate = moment().format('YYYY-MM-DD')
        todo.done = true
      } else {
        todo.finishedDate = null
        todo.done = false
      }
      this.updateTask(todo)
    }
  }
}
</script>

<style scoped>
  /*.project-div {*/
  /*margin-left: 270px;*/
  /*margin-right: 30px;*/
  /*margin-top: 30px;*/
  /*!*text-align: center;*!*/
  /*}*/
</style>

<template>
  <div class="project-div">
    <p> {{activeProject}} </p>

    <select>
      <option v-for="project in userProjects" :key="project" @click="activeProject = project">{{project}}</option>
      <option @click="createNewProject">Neues Projekt erstellen</option>
    </select>

    <br>
    <button>...</button>
    <br>
    <button>Tasks</button>
    <br>
    <button>Meetings</button>

    <!--  Task Table-->
<!--    <div class="project-div">-->
      <div class="overflow-x-auto relative shadow-md sm:rounded-lg">
        <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
          <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
          <tr>
            <th scope="col" class="p-4">
              <button @click="addTodo">+</button>
            </th>
            <th scope="col" class="py-3 px-6 text-decoration: line-through" @click="sortTodos('title')">
              Titel
            </th>
            <th scope="col" class="py-3 px-6" @click="sortTodos('category')">
              Kategorie
            </th>
            <th scope="col" class="py-3 px-6" @click="sortTodos('deadline')">
              Deadline
            </th>
            <th scope="col" class="py-3 px-6">

            </th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="todo in filteredTodos" :key="todo.id"
              class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
            <td class="p-4 w-4">
              <div class="flex items-center">
                <input v-model="todo.done" @click="onFinished(todo)" id="checkbox-table-search-1"
                       type="checkbox"
                       class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
                <label for="checkbox-table-search-1" class="sr-only">checkbox</label>
              </div>
            </td>
            <th scope="row" class="py-4 px-6 font-medium text-gray-900 whitespace-nowrap dark:text-white">
              <input v-model="todo.title" @keyup="updateTask(todo)">
            </th>
            <td class="py-4 px-6">
              <input v-model="todo.category" @keyup="updateTask(todo)">
            </td>
            <td class="py-4 px-6">
              <input type="date" v-model="todo.deadline" @change="updateTask(todo)">
            </td>
            <td class="flex items-center py-4 px-6 space-x-3">
              <a href="#" @click="removeTodo(todo)" class="font-medium text-red-600 dark:text-red-500 hover:underline">Remove</a>
            </td>
          </tr>
          </tbody>
        </table>
      </div>

      <br>

      <div style="text-align: left">
        <button @click="hideCompleted = !hideCompleted">
          {{ hideCompleted ? 'Abgeschlossene Tasks einblenden' : 'Abgeschlossene Tasks ausblenden' }}
        </button>
      </div>

      <!--    <div class="pagination">-->
      <!--      <nav aria-label="Page navigation example">-->
      <!--        <ul class="inline-flex items-center -space-x-px">-->
      <!--          <li>-->
      <!--            <a href="#"-->
      <!--               class="block py-2 px-3 ml-0 leading-tight text-gray-500 bg-white rounded-l-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">-->
      <!--              <span class="sr-only">Previous</span>-->
      <!--              <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20"-->
      <!--                   xmlns="http://www.w3.org/2000/svg">-->
      <!--                <path fill-rule="evenodd"-->
      <!--                      d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z"-->
      <!--                      clip-rule="evenodd"></path>-->
      <!--              </svg>-->
      <!--            </a>-->
      <!--          </li>-->
      <!--          <li>-->
      <!--            <a href="#"-->
      <!--               class="py-2 px-3 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">1-->
      <!--              von 1</a>-->
      <!--          </li>-->
      <!--          <li>-->
      <!--            <a href="#"-->
      <!--               class="block py-2 px-3 leading-tight text-gray-500 bg-white rounded-r-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">-->
      <!--              <span class="sr-only">Next</span>-->
      <!--              <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20"-->
      <!--                   xmlns="http://www.w3.org/2000/svg">-->
      <!--                <path fill-rule="evenodd"-->
      <!--                      d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z"-->
      <!--                      clip-rule="evenodd"></path>-->
      <!--              </svg>-->
      <!--            </a>-->
      <!--          </li>-->
      <!--        </ul>-->
      <!--      </nav>-->
      <!--    </div>-->

    </div>

<!--    <TaskManagerView/>-->

<!--  </div>-->
</template>
