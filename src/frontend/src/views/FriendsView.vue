<template>
  <div class="next-to-sidebar">
    <div id="mySidebar" class="sidebar">
      <a href="javascript:void(0)" class="closebtn" @click="closeNav">&times;</a>
      <a href="#">About</a>
      <a href="#">Services</a>
      <a href="#">Clients</a>
      <a href="#">Contact</a>
    </div>

    <button class="openbtn" @click="openNav">&#9776; Open Sidebar</button>
  </div>
</template>

<script>
export default {
  name: 'FriendsView',
  mounted () {
    if (localStorage.getItem('user_token') == null) {
      this.$router.push('login')
    }
  },
  methods: {
    openNav () {
      document.getElementById('mySidebar').style.width = '250px'
    },
    closeNav () {
      document.getElementById('mySidebar').style.width = '0'
    }
  }
}
</script>

<style>

.next-to-sidebar{
  margin-top: 50px;
  margin-left: 300px;
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
  color: #818181;
  display: block;
  transition: 0.3s;
}

/* When you mouse over the navigation links, change their color */
.sidebar a:hover {
  color: #f1f1f1;
}

/* Position and style the close button (top right corner) */
.sidebar .closebtn {
  position: absolute;
  top: 0;
  right: 25px;
  font-size: 36px;
  margin-left: 50px;
}

/* The button used to open the sidebar */
.openbtn {
  font-size: 20px;
  cursor: pointer;
  background-color: #052A34;
  color: white;
  padding: 10px 15px;
  border: none;
  width: 1200px;
}

.openbtn:hover {
  background-color: #444;
}

</style>
