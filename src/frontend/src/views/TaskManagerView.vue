<script>
import moment from 'moment'

export default {
  title: 'Tasks',
  name: 'TaskManagerView',
  data () {
    return {
      hideCompleted: true,
      todos: [],
      sortType: 'title'
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

    fetch('/api/taskmanager/getTasks?user=' + localStorage.getItem('user_token'), {
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
    sortTodos (sortType) {
      this.sortType = sortType
      switch (sortType) {
        case 'title':
          this.todos.sort((a, b) => {
            const t1 = a.title.toLowerCase()
            const t2 = b.title.toLowerCase()
            if (t1 === '') {
              return 1
            }
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
            if (c1 === '') {
              return 1
            }
            if (c2 === '') {
              return -1
            }
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
            if (d1 === null) {
              return 1
            }
            if (d2 === null) {
              return -1
            }
            if (d1 < d2) {
              return -1
            }
            if (d1 > d2) {
              return 1
            }
            return 0
          })
          break
      }
    },
    addTask () {
      const newTask = { taskID: null, title: '', category: '', deadline: null, finishedDate: null, project: null, user: null }

      fetch('/api/taskmanager/addTask?user=' + localStorage.getItem('user_token'), {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newTask)
      })
        .then(response => response.json())
        .then(jsonData => this.todos.push(jsonData))
    },
    removeTodo (todo) {
      fetch('/api/taskmanager/deleteTask?user=' + localStorage.getItem('user_token'), {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(todo)
      })
        .then((response) => { return console.log(response) })
      this.todos = this.todos.filter((t) => t !== todo)
    },
    updateTask (todo) {
      console.log('Update:' + JSON.stringify(todo))
      fetch('/api/taskmanager/updateTask?user=' + localStorage.getItem('user_token'), {
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

<template>
  <!--  Task Table-->
  <div class="project-div">
    <div class="overflow-x-auto relative shadow-md sm:rounded-lg">
      <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
        <thead class="text-xs text-[#FFFCF7] uppercase bg-[#052A34] dark:bg-gray-700 dark:text-gray-400">
        <tr>
          <th scope="col" class="p-4">
            <button @click="addTask">
              <svg  xmlns="http://www.w3.org/2000/svg" viewBox="0 0 384 512" width="20%" class="w-4 h-4 text-[#FFFCF7] transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white" fill="currentColor">
                <path fill="currentColor" d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32V224H48c-17.7 0-32 14.3-32 32s14.3 32 32 32H192V432c0 17.7 14.3 32 32 32s32-14.3 32-32V288H400c17.7 0 32-14.3 32-32s-14.3-32-32-32H256V80z"/>
              </svg>
            </button>
          </th>
          <th scope="col" class="py-3 px-6 bg-center" @click="sortTodos('title')">
            <span style="font-size: 20px; font-weight: lighter" >TITEL</span>
          </th>

          <th scope="col" class="py-3 px-6 bg-center" @click="sortTodos('category')">
            <span style="font-size: 20px; font-weight: lighter" >KATEGORIE</span>
          </th>

          <th scope="col" class="py-3 px-6 bg-center" @click="sortTodos('deadline')">
            <span style="font-size: 20px; font-weight: lighter" >DEADLINE</span>
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
            <input v-model="todo.title" @keyup.enter="updateTask(todo)" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Titel">
          </th>
         <td class="py-4 px-6">
           <input v-model="todo.category" @keyup.enter="updateTask(todo)" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Kategorie">
         </td>

          <td class="py-4 px-6">
            <div class="relative">
              <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">-->
                <svg aria-hidden="true" class="w-5 h-5 text-gray-500 dark:text-gray-400" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M6 2a1 1 0 00-1 1v1H4a2 2 0 00-2 2v10a2 2 0 002 2h12a2 2 0 002-2V6a2 2 0 00-2-2h-1V3a1 1 0 10-2 0v1H7V3a1 1 0 00-1-1zm0 5a1 1 0 000 2h8a1 1 0 100-2H6z" clip-rule="evenodd"></path></svg>
              </div>
             <input v-model="todo.deadline" @change="updateTask(todo)" datepicker datepicker-format="mm/dd/yyyy" type="date" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Select date">
           </div>

          </td>
          <td class="flex items-center py-4 px-0 space-x-3">
            <button @click="removeTodo(todo)" type="button" class="w-10 text-red-700 border border-red-700 hover:bg-red-700 hover:text-white focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm p-2.5 text-center inline-flex items-center mr-2 dark:border-red-500 dark:text-red-500 dark:hover:text-white dark:focus:ring-red-800">
             <div style="align-items: center">
               <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 430 512" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M135.2 17.7L128 32H32C14.3 32 0 46.3 0 64S14.3 96 32 96H416c17.7 0 32-14.3 32-32s-14.3-32-32-32H320l-7.2-14.3C307.4 6.8 296.3 0 284.2 0H163.8c-12.1 0-23.2 6.8-28.6 17.7zM416 128H32L53.2 467c1.6 25.3 22.6 45 47.9 45H346.9c25.3 0 46.3-19.7 47.9-45L416 128z" clip-rule="evenodd"></path></svg>
             </div>
              </button>
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

  </div>

</template>

<style>

.pagination {
  text-align: center;
}

.project-div {
  margin-left: 270px;
  margin-right: 30px;
  margin-top: 30px;
  text-align: center;
}
</style>
