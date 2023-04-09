<template>
  <div class="next-to-sidebar">
    <!-- Sidebar -->
    <div id="mySidebar" class="sidebar">
      <div style ="padding-left: 20px">

      <a href="javascript:void(0)" class="closebtn" @click="closeNav">&times;</a>
<!--      <div>-->
<!--        <a href="#">NicoTategami</a>-->
<!--        <div class="buttons">-->
<!--          <button class="round-button">&#10003;</button>-->
<!--          <button class="round-button">&#88;</button>-->
<!--        </div>-->
<!--      </div>-->
<!--      <div>-->
<!--        <a href="#">tobjar03</a>-->
<!--        <div class="buttons">-->
<!--          <button @click="handleRequest('test',true)" class="round-button">&#10003;</button>-->
<!--          <button class="round-button">&#88;</button>-->
<!--        </div>-->
<!--      </div>-->
<!--      ToDo Formatierung-->
      <p class="heading">Freundschaftsanfragen</p>
      <RequestComponent v-for="request in requestList" :key="request" :name="request"></RequestComponent>
    </div>
    </div>
    <!-- Sidebar-Ende -->
    <div class="search-container">
      <input id="friend-input" class="search" placeholder="Suchen">
      <button id="addButton" @click="addUser" type="button" class="w-64 font-bold text-white bg-[#052A34] hover:bg-[#041D24] focus:ring-4 focus:ring-[#A7E6F7] font-medium rounded-lg text-sm px-10 py-2.5 mr-2 mb-2 dark:bg-black-600 dark:hover:bg-[#041D24] focus:outline-none dark:focus:ring-[#A7E6F7]">Add</button>
    </div>

    <div class="bell" @click="openNav">
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"
           class="ml-1.5 w-12 h-12 place-self-center text-[#052A34] transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
           fill="currentColor">
        <path
          d="M224 0c-17.7 0-32 14.3-32 32V51.2C119 66 64 130.6 64 208v18.8c0 47-17.3 92.4-48.5 127.6l-7.4 8.3c-8.4 9.4-10.4 22.9-5.3 34.4S19.4 416 32 416H416c12.6 0 24-7.4 29.2-18.9s3.1-25-5.3-34.4l-7.4-8.3C401.3 319.2 384 273.9 384 226.8V208c0-77.4-55-142-128-156.8V32c0-17.7-14.3-32-32-32zm45.3 493.3c12-12 18.7-28.3 18.7-45.3H224 160c0 17 6.7 33.3 18.7 45.3s28.3 18.7 45.3 18.7s33.3-6.7 45.3-18.7z"/>
      </svg>
    </div>

    <div id="friend_list"></div>

<!--    <div class="container" style="margin-top: 30px">-->
<!--      <svg width="50" height="50" viewBox="0 0 512 512" class="text-[#052A34]" fill="currentColor">-->
<!--        <path-->
<!--          d="M399 384.2C376.9 345.8 335.4 320 288 320H224c-47.4 0-88.9 25.8-111 64.2c35.2 39.2 86.2 63.8 143 63.8s107.8-24.7 143-63.8zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256zm256 16a72 72 0 1 0 0-144 72 72 0 1 0 0 144z"/>-->
<!--      </svg>-->
<!--      <p class="friend" style="display: inline-block; margin-left: 10px">mucmac18</p>-->
<!--    </div>-->

    <!--    <div class="container" style="margin-top: 30px">-->
    <!--      <svg width="50" height="50" viewBox="0 0 512 512" class="text-[#052A34]" fill="currentColor">-->
    <!--        <path d="M399 384.2C376.9 345.8 335.4 320 288 320H224c-47.4 0-88.9 25.8-111 64.2c35.2 39.2 86.2 63.8 143 63.8s107.8-24.7 143-63.8zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256zm256 16a72 72 0 1 0 0-144 72 72 0 1 0 0 144z"/>-->
    <!--      </svg>-->
    <!--      <p class = "friend" style="display: inline-block; margin-left: 10px">kamnic18</p>-->
    <!--    </div>-->

    <!--    <div class="container" style="margin-top: 30px">-->
    <!--      <svg width="50" height="50" viewBox="0 0 512 512" class="text-[#052A34]" fill="currentColor">-->
    <!--        <path d="M399 384.2C376.9 345.8 335.4 320 288 320H224c-47.4 0-88.9 25.8-111 64.2c35.2 39.2 86.2 63.8 143 63.8s107.8-24.7 143-63.8zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256zm256 16a72 72 0 1 0 0-144 72 72 0 1 0 0 144z"/>-->
    <!--      </svg>-->
    <!--      <p class = "friend" style="display: inline-block; margin-left: 10px">kocdoc18</p>-->
    <!--    </div>-->

    <!--    ToDo request-->
<!--        <div class="container" style="margin-top: 30px">-->
<!--          <svg width="50" height="50" viewBox="0 0 512 512" class="text-[#052A34]" fill="currentColor">-->
<!--            <path d="M399 384.2C376.9 345.8 335.4 320 288 320H224c-47.4 0-88.9 25.8-111 64.2c35.2 39.2 86.2 63.8 143 63.8s107.8-24.7 143-63.8zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256zm256 16a72 72 0 1 0 0-144 72 72 0 1 0 0 144z"/>-->
<!--          </svg>-->
<!--          <p class = "friend" style="display: inline-block; margin-left: 10px">jartoc18</p>-->
<!--          <svg width="50" height="50" viewBox="0 0 680 512" class="text-[#052A34]" fill="currentColor" style="margin-right: 50%; margin-left: auto">-->
<!--            <path d="M96 128a128 128 0 1 1 256 0A128 128 0 1 1 96 128zM0 482.3C0 383.8 79.8 304 178.3 304h91.4C368.2 304 448 383.8 448 482.3c0 16.4-13.3 29.7-29.7 29.7H29.7C13.3 512 0 498.7 0 482.3zM504 312V248H440c-13.3 0-24-10.7-24-24s10.7-24 24-24h64V136c0-13.3 10.7-24 24-24s24 10.7 24 24v64h64c13.3 0 24 10.7 24 24s-10.7 24-24 24H552v64c0 13.3-10.7 24-24 24s-24-10.7-24-24z"/>-->
<!--          </svg>-->
<!--        </div>-->

  </div>
</template>

<script>
import RequestComponent from '@/components/RequestComponent'
export default {
  name: 'FriendsView',
  components: { RequestComponent },
  data () {
    return {
      friendList: [],
      requestList: []
    }
  },
  mounted () {
    if (localStorage.getItem('user_token') == null) {
      this.$router.push('login')
    }

    fetch('/api/friendrequest/getAllFriends?username=' + localStorage.getItem('user_token'), {})
      .then(response => response.json())
      .then(jsonData => {
        jsonData.forEach(friend => {
          this.friendList.push(friend.username)
          document.getElementById('friend_list').innerHTML += `
            <div class="container" style="margin-top: 30px">
            <svg width="50" height="50" viewBox="0 0 512 512" class="text-[#052A34]" fill="currentColor">
                <path d="M399 384.2C376.9 345.8 335.4 320 288 320H224c-47.4 0-88.9 25.8-111 64.2c35.2 39.2 86.2 63.8 143 63.8s107.8-24.7 143-63.8zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256zm256 16a72 72 0 1 0 0-144 72 72 0 1 0 0 144z"/>
            </svg>
            <p class = "friend" style="display: inline-block; margin-left: 10px">${friend.username}</p>
            </div>
          `
        })
      })

    fetch('/api/friendrequest/getRequests?username=' + localStorage.getItem('user_token'), {})
      .then(response => response.json())
      .then(jsonData => {
        jsonData.forEach(request => {
          this.requestList.push(request)
        })
      })
  },
  methods: {
    openNav () {
      document.getElementById('mySidebar').style.width = '300px'
      // document.getElementById('main').style.marginRight = '300px'
    },
    closeNav () {
      document.getElementById('mySidebar').style.width = '0'
      // document.getElementById('main').style.marginRight = '0'
    },
    handleRequest (username, answer) {
      console.log(username)
      console.log(answer)
    },
    addUser () {
      // friend-input
      fetch('/api/friendrequest/addUserToFR?usernameToAdd=' + document.getElementById('friend-input').value, {
        method: 'POST',
        body: JSON.stringify({ mainUsername: localStorage.getItem('user_token') })
      })
        .then(response => {
          if (response.status !== 200) {
            alert('Error')
          } else {
            alert('Anfrage erfolgreich versendet!')
          }
        })
    },
    finishRequest () {
      this.friendList = []
      document.getElementById('friend_list').innerHTML = ''

      fetch('/api/friendrequest/getAllFriends?username=' + localStorage.getItem('user_token'), {})
        .then(response => response.json())
        .then(jsonData => {
          jsonData.forEach(friend => {
            this.friendList.push(friend.username)
            document.getElementById('friend_list').innerHTML += `
            <div class="container" style="margin-top: 30px">
            <svg width="50" height="50" viewBox="0 0 512 512" class="text-[#052A34]" fill="currentColor">
                <path d="M399 384.2C376.9 345.8 335.4 320 288 320H224c-47.4 0-88.9 25.8-111 64.2c35.2 39.2 86.2 63.8 143 63.8s107.8-24.7 143-63.8zM0 256a256 256 0 1 1 512 0A256 256 0 1 1 0 256zm256 16a72 72 0 1 0 0-144 72 72 0 1 0 0 144z"/>
            </svg>
            <p class = "friend" style="display: inline-block; margin-left: 10px">${friend.username}</p>
            </div>
          `
          })
        })
      this.closeNav()
    }
  }
}
</script>

<style>
.heading{
  font-size: 25px;
  color: #f1f1f1;
}

.search-container {
  position: relative;
  width: 70%;
  margin-left: 0px;
}

#friend-input{
  width: 50%;
}

.search{
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  box-sizing: border-box;
  border: 2px solid #052A34;
  border-radius: 25px;
  background-color: white;
  font-size: 22px;
}

#addButton{
  padding: 18px 20px 18px 20px;
  margin: 8px 20px 8px 10px;
  box-sizing: border-box;
  border: 2px solid #052A34;
  border-radius: 25px;
  background-color: #052A34;
  font-size: 22px;
  color: white;
}

.next-to-sidebar{
  margin-top: 50px;
  margin-left: 300px;
}
.friend{
  font-size: 35px;
  font-weight: bold;
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
.buttons{
  float: right;
  margin-right: 20px;
  transition: 0.3s;
  margin-top: 10px;
  display:inline-block;
  color: #818181;
}
.bell{
  position: fixed;
  bottom: 30px;
  right: 30px;
}
.bell:hover{
  color: #ffffff;
}
.round-button:hover{
  color: #ffffff;
  border: 2px solid #ffffff;
}
.round-button {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  border: 2px solid #818181;
  background-color: transparent;
  color: #818181;
  display: inline-block;
  justify-content: center;
  align-items: center;
  margin-left: 10px;
  transition: 0.3s;
}
/* Position and style the close button (top right corner) */
.sidebar .closebtn {
  position: absolute;
  top: 0;
  right: 25px;
  font-size: 36px;
  margin-left: 50px;
}
/* Style page content - use this if you want to push the page content to the right when you open the side navigation */
#main {
  transition: margin-left .5s; /* If you want a transition effect */
  padding: 20px;
}
.container {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  margin-left: 0px;
  margin-top: 20px;
}
svg {
  margin-right: 20px;
}

</style>
