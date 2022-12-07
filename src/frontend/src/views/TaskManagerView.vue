<script>
export default {
  title: 'Tasks',
  name: 'TaskManagerView',
  data () {
    return {
      hideCompleted: false,
      todos: []
    }
  },
  computed: {
    filteredTodos () {
      return this.hideCompleted
        ? this.todos.filter((t) => !t.finishedDate == null)
        : this.todos
    }
  },
  mounted () {
    console.log('sdfhh')
    fetch('/api/taskmanager/getTasks?username=admin', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify('sortType: filter')
    })
      .then((response) => { return response.json() })
      .then(data => {
        data.forEach(task => {
          console.log(task)
          this.todos.push(task)
        })
        this.sortTodos('category')
      })
  },
  methods: {
    sortTodos (sortType) {
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
      }
    },
    addTodo () {
      const newTask = { taskID: null, title: '', category: '', deadline: null, finishedDate: null, project: null, user: null }

      // fetch
      fetch('/api/taskmanager/addTask?username=admin', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newTask)
      })
        .then((response) => {
          console.log(response.json())
          this.todos.push(response.json())
          // console.log(this.todos.pop())
          // console.log(response.json)
          // return console.log(response.json)
        })
        // .then(data => {
        //   data.forEach(task => {
        //     console.log(task)
        //     this.todos.push(task)
        //   })
        // })
      // pop update push

      // Todo fetch addTodo
      // set todo.id
    },
    removeTodo (todo) {
      fetch('/api/taskmanager/deleteTask?username=admin', {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(todo)
      })
        .then((response) => { return console.log(response) })
      this.todos = this.todos.filter((t) => t !== todo)
      // todo fetch removeToDo
    },
    updateTitle (todo) {
      console.log('Update:' + JSON.stringify(todo))
      fetch('/api/taskmanager/updateTask?username=admin', {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(todo)
      })
      // .then((response) => {
      //   // this.todos.push(response.json)
      //   // return console.log(response)
      // })
      // alert('Todo has changed:' + todo.category)
      //  ToDo fetch updateTodo
    }
  }
}
</script>

<template>
  <!--  Task Table-->
  <div class="task-table">
    <div class="overflow-x-auto relative shadow-md sm:rounded-lg">
      <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
        <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
        <tr>
          <th scope="col" class="p-4">
            <button @click="addTodo">+</button>
          </th>
          <th scope="col" class="py-3 px-6">
            Titel
          </th>
          <th scope="col" class="py-3 px-6">
            Kategorie
          </th>
          <th scope="col" class="py-3 px-6">
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
              <input v-model="todo.done" id="checkbox-table-search-1"
                     type="checkbox"
                     class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
              <label for="checkbox-table-search-1" class="sr-only">checkbox</label>
            </div>
          </td>
          <th scope="row" class="py-4 px-6 font-medium text-gray-900 whitespace-nowrap dark:text-white">
            <input v-model="todo.title" @keyup="updateTitle(todo)">
          </th>
          <td class="py-4 px-6">
            <!--            <select>-->
            <!--              <option v-for="category in categories" :key="category" :value="category">-->
            <!--                {{ category }}-->
            <!--              </option>-->
            <!--            </select>-->
            <input v-model="todo.category" @keyup="updateTitle(todo)">
          </td>
          <td class="py-4 px-6">
            <input type="date" v-model="todo.deadline" @change="updateTitle(todo)">
          </td>
          <td class="flex items-center py-4 px-6 space-x-3">
            <a href="#" @click="removeTodo(todo)" class="font-medium text-red-600 dark:text-red-500 hover:underline">Remove</a>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <br>

<!--    <div style="text-align: left">-->
<!--      <button @click="hideCompleted = !hideCompleted">-->
<!--        {{ hideCompleted ? 'Abgeschlossene Tasks einblenden' : 'Abgeschlossene Tasks ausblenden' }}-->
<!--      </button>-->
<!--    </div>-->

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

</template>

<style>

.pagination {
  text-align: center;
}

.task-table {
  margin-left: 270px;
  margin-right: 30px;
  margin-top: 30px;
  text-align: center;
}
</style>
