<script>
// import TaskManagerView from '@/views/TaskManagerView'
import moment from 'moment/moment'
import AddFriendToProjectComponent from '@/components/AddFriendToProjectComponent'
export default {
  components: { AddFriendToProjectComponent },
  props: ['editProject'],
  name: 'ProjectView',
  data () {
    return {
      friendList: [],
      hideCompleted: true,
      todos: [],
      sortType: 'title',
      activeProject: '-',
      display: '-',
      // userProjects: []
      userProjects: ['Vela', 'POS', 'Mathe'],
      userProjectDetails: []
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

    fetch('/api/project/getProjects?user=' + localStorage.getItem('user_token'), {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' }
    })
      .then(response => response.json())
      .then(responseData => {
        this.userProjectDetails = responseData
        this.activeProject = responseData[0]
      })
  },
  methods: {
    getTasks () {
      this.todos = []
      fetch('/api/project/getTasks?id=' + this.activeProject.projectID, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' }
      })
        .then((response) => { return response.json() })
        .then(data => {
          data.forEach(task => {
            task.projectID = task.project.projectID
            if (task.finishedDate != null) {
              console.log(task)
              task.done = true
            }
            this.todos.push(task)
          })
        })
    },
    createNewProject () {
      this.$router.push('/newproject')
    },
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
    addTodo () {
      // alert(this.activeProject.projectID)
      const newTask = { taskID: null, title: '', category: '', deadline: null, finishedDate: null, projectID: this.activeProject.projectID, user: null }
      console.log(newTask)

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
    },
    onEditProject () {
      // alert('edit Project')
      console.log(this.activeProject.name)
      localStorage.setItem('project_name', this.activeProject.name)
      localStorage.setItem('project_description', this.activeProject.description)
      // localStorage.setItem('project_id', this.activeProject.projectID)
      this.$router.push('editproject')
    },
    openNav () {
      // todo fetch friends
      this.friendList = []
      fetch('/api/friendrequest/getAllFriends?username=' + localStorage.getItem('user_token'), {})
        .then(response => response.json())
        .then(jsonData => {
          jsonData.forEach(friend => {
            this.friendList.push(friend.username)
          })
        })
      document.getElementById('mySidebar').style.width = '300px'
      // document.getElementById('main').style.marginRight = '300px'
    },
    closeNav () {
      document.getElementById('mySidebar').style.width = '0'
      // document.getElementById('main').style.marginRight = '0'
    },
    saveProject () {
      fetch('/api/project/editProject', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          projectID: this.activeProject.projectID,
          description: this.activeProject.description,
          name: this.activeProject.name
        })
      })
        .then(response => {
          // if (response.status === 200) {
          //   alert('Projekt aktualisiert')
          // } else {
          //   alert('Aktualiserung fehlgeschlagen. Bitte versuche es erneut.')
          // }
        })
      this.closeNav()
    }
  }
}
</script>

<style scoped>
.options-button{
  margin-right: -8px;
  float:right;
}

/* The sidebar menu */
.sidebar {
  height: 100%; /* 100% Full-height */
  width: 0; /* 0 width - change this with JavaScript */
  position: fixed; /* Stay in place */
  z-index: 1; /* Stay on top */
  top: 0;
  right: 0;
  background-color: #052A34; /* Black*/
  overflow-x: hidden; /* Disable horizontal scroll */
  padding-top: 60px; /* Place content 60px from the top */
  transition: 0.5s; /* 0.5 second transition effect to slide in the sidebar */
}
/* The sidebar links */
.sidebar a {
  padding: 8px 8px 8px 32px;
  text-decoration: none;
  font-size: 25px;
  color: #f1f1f1;
  display:inline-block;
  transition: 0.3s;
}

#sidebarContent{
  margin-left: 20px;
}

.heading{
  font-size: 25px;
  color: #f1f1f1;
}

.inputs{
  font-size: 14px;
  color: #f1f1f1;
}

#trash-icon{
  align-items: center;
  justify-content: center;
}
</style>

<template>

  <!-- Sidebar -->
  <div id="mySidebar" class="sidebar">
    <div id = "sidebarContent">
    <a href="javascript:void(0)" class="closebtn" @click="closeNav">&times;</a>
    <p class="heading">Projekt ändern</p>
      <br>
    <!--    {{activeProject.projectID}}-->
    <div class="relative rounded-md" style="margin-bottom: 15px">
      <label class="block font-medium text-gray-700 leading-5 inputs">Projektname</label>
      <input v-model="activeProject.name" required name="login-username" type="text" class="w-64 form-input py-3 px-4 leading-5 rounded-md transition duration-150 ease-in-out bg-transparent border text-white border-2 border-white placeholder-gray-500 focus:outline-none focus:shadow-outline-transparent active:text-gray-800">
    </div>

    <div class="relative rounded-md" style="margin-bottom: 15px">
      <label class="block font-medium text-gray-700 leading-5 inputs">Beschreibung</label>
      <input v-model="activeProject.description" required name="login-username" type="text" class="w-64 form-input py-3 px-4 leading-5 rounded-md transition duration-150 ease-in-out bg-transparent border text-white border-2 border-white placeholder-gray-500 focus:outline-none focus:shadow-outline-transparent active:text-gray-800">
    </div>
    <!--    Projektname: <input v-model="project_name" type="text"><br>-->
    <!--    Beschreibung: <input v-model="project_description" type="text">-->
    <button @click="saveProject" type="button" class="w-64 font-bold text-[#052A34] bg-white hover:bg-[#e0e0e0] focus:ring-4 focus:ring-[#A7E6F7] font-medium rounded-lg text-sm px-10 py-2.5 mr-2 mb-2 dark:bg-black-600 dark:hover:bg-[#041D24] focus:outline-none dark:focus:ring-[#A7E6F7]">Änderungen speichern</button>
    <br>
      <br>
    <p class="heading">Freund hinzufügen</p>

    <AddFriendToProjectComponent v-for="friend in friendList" :key="friend" :name="friend" :project-i-d="activeProject.projectID"></AddFriendToProjectComponent>
  </div>
  </div>
  <!-- Sidebar-Ende -->

  <div class="project-div">
    <div style="margin-right: 40px">
      <h1 class="mb-4 text-4xl font-extrabold leading-none tracking-tight text-[#052A34] md:text-5xl lg:text-6xl dark:text-white">{{display}} </h1>

    </div>

    <select class="w-64 font-bold text-white bg-[#052A34] hover:bg-[#041D24] focus:ring-4 focus:ring-[#A7E6F7] font-medium rounded-lg text-sm px-10 py-2.5 mr-2 mb-2 dark:bg-black-600 dark:hover:bg-[#041D24] focus:outline-none dark:focus:ring-[#A7E6F7]">
      <option value=""  selected disabled hidden>Projekt auswählen</option>
      <option v-for="project in userProjectDetails" :key="project" @click="activeProject = project; display = project.name; getTasks()">{{project.name}}</option>
      <option @click="createNewProject">Neues Projekt erstellen</option>
    </select>

    <div class="options-button">
<!--    <button @click="onEditProject">...</button>-->
<!--    ToDo remove line or use line-->
<!--      <button @click="onEditProject" type="button" class="bg-[#052A34] text-white border border-[#052A34] hover:bg-[#041D24] hover:text-white focus:ring-4 focus:outline-none focus:ring-[#A7E6F7] font-medium rounded-lg text-sm p-2.5 items-end mr-2 dark:border-red-500 dark:text-red-500 dark:hover:text-white dark:focus:ring-red-800">-->
      <button @click="openNav" type="button" class="bg-[#052A34] text-white border border-[#052A34] hover:bg-[#041D24] hover:text-white focus:ring-4 focus:outline-none focus:ring-[#A7E6F7] font-medium rounded-lg text-sm p-2.5 items-end mr-2 w-10">
        <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 448 512" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M120 256c0 30.9-25.1 56-56 56s-56-25.1-56-56s25.1-56 56-56s56 25.1 56 56zm160 0c0 30.9-25.1 56-56 56s-56-25.1-56-56s25.1-56 56-56s56 25.1 56 56zm104 56c-30.9 0-56-25.1-56-56s25.1-56 56-56s56 25.1 56 56s-25.1 56-56 56z" clip-rule="evenodd"></path></svg>
      </button>
<!--        <button @click="onEditProject" type="button" class="bg-[#052A34] text-white border border-[#052A34] hover:bg-[#041D24] hover:text-white focus:ring-4 focus:outline-none focus:ring-[#A7E6F7] font-medium rounded-lg text-sm p-2.5 items-end mr-2 dark:border-red-500 dark:text-red-500 dark:hover:text-white dark:focus:ring-red-800">-->
<!--        <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 448 512" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M120 256c0 30.9-25.1 56-56 56s-56-25.1-56-56s25.1-56 56-56s56 25.1 56 56zm160 0c0 30.9-25.1 56-56 56s-56-25.1-56-56s25.1-56 56-56s56 25.1 56 56zm104 56c-30.9 0-56-25.1-56-56s25.1-56 56-56s56 25.1 56 56s-25.1 56-56 56z" clip-rule="evenodd"></path></svg>-->
<!--      </button>-->
    </div>

      <div class="overflow-x-auto relative shadow-md sm:rounded-lg">
        <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
          <thead class="text-xs text-[#FFFCF7] uppercase bg-[#052A34] dark:bg-gray-700 dark:text-gray-400">
          <tr>
            <th scope="col" class="p-4">
              <button @click="addTodo">
                <svg  xmlns="http://www.w3.org/2000/svg" viewBox="0 0 384 512" width="20%" class="w-4 h-4 text-[#FFFCF7] transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white" fill="currentColor">
                  <path fill="currentColor" d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32V224H48c-17.7 0-32 14.3-32 32s14.3 32 32 32H192V432c0 17.7 14.3 32 32 32s32-14.3 32-32V288H400c17.7 0 32-14.3 32-32s-14.3-32-32-32H256V80z"/>
                </svg>
              </button>
            </th>
            <th scope="col" class="py-3 px-6" @click="sortTodos('title')">
              <span style="font-size: 20px; font-weight: lighter" >TITEL</span>
            </th>
            <th scope="col" class="py-3 px-6" @click="sortTodos('category')">
              <span style="font-size: 20px; font-weight: lighter" >KATEGORIE</span>
            </th>
            <th scope="col" class="py-3 px-6" @click="sortTodos('deadline')">
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
<!--            <th scope="row" class="py-4 px-6 font-medium text-gray-900 whitespace-nowrap dark:text-white">-->
            <td class="py-4 px-6">
              <input v-model="todo.title" @keyup.enter="updateTask(todo)" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Titel">
            </td>
            <td class="py-4 px-6">
              <input v-model="todo.category" @keyup.enter="updateTask(todo)" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Kategorie">
            </td>
            <td class="py-4 px-6">
              <div class="relative">
                <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                  <svg aria-hidden="true" class="w-5 h-5 text-gray-500 dark:text-gray-400" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M6 2a1 1 0 00-1 1v1H4a2 2 0 00-2 2v10a2 2 0 002 2h12a2 2 0 002-2V6a2 2 0 00-2-2h-1V3a1 1 0 10-2 0v1H7V3a1 1 0 00-1-1zm0 5a1 1 0 000 2h8a1 1 0 100-2H6z" clip-rule="evenodd"></path></svg>
                </div>
                <input v-model="todo.deadline" @change="updateTask(todo)" datepicker datepicker-format="mm/dd/yyyy" type="date" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Select date">
              </div>
            </td>
            <td class="flex items-center py-4 px-6 space-x-3">
              <button @click="removeTodo(todo)" type="button" class="w-10 text-red-700 border border-red-700 hover:bg-red-700 hover:text-white focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm p-2.5 text-center inline-flex items-center mr-2 dark:border-red-500 dark:text-red-500 dark:hover:text-white dark:focus:ring-red-800">
                <div style="align-items: center">
                  <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 480 512" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M135.2 17.7L128 32H32C14.3 32 0 46.3 0 64S14.3 96 32 96H416c17.7 0 32-14.3 32-32s-14.3-32-32-32H320l-7.2-14.3C307.4 6.8 296.3 0 284.2 0H163.8c-12.1 0-23.2 6.8-28.6 17.7zM416 128H32L53.2 467c1.6 25.3 22.6 45 47.9 45H346.9c25.3 0 46.3-19.7 47.9-45L416 128z" clip-rule="evenodd"></path></svg>
                </div>
              </button>

<!--              <button @click="removeTodo(todo)" type="button" class="text-red-700 border border-red-700 hover:bg-red-700 hover:text-white focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm p-2.5 text-center inline-flex items-center mr-2 dark:border-red-500 dark:text-red-500 dark:hover:text-white dark:focus:ring-red-800">-->
<!--                <svg aria-hidden="true" class="w-5 h-5" id="trash-icon" fill="currentColor" viewBox="0 0 448 512" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M135.2 17.7L128 32H32C14.3 32 0 46.3 0 64S14.3 96 32 96H416c17.7 0 32-14.3 32-32s-14.3-32-32-32H320l-7.2-14.3C307.4 6.8 296.3 0 284.2 0H163.8c-12.1 0-23.2 6.8-28.6 17.7zM416 128H32L53.2 467c1.6 25.3 22.6 45 47.9 45H346.9c25.3 0 46.3-19.7 47.9-45L416 128z" clip-rule="evenodd"></path></svg>-->
<!--              </button>-->
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
