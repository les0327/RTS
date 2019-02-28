<template>
  <div class="index">
    <ol>
      <li v-for="lab in labs">
        <router-link :to=lab.path>{{lab.name}}</router-link>
      </li>
    </ol>
  </div>
</template>

<script>
  export default {
    name: 'index',
    data() {
      return {
        labs: [
          {path: "/lab1", name: "Lab1"},
          {path: "/lab2", name: "Lab2"},
          {path: "/lab3", name: "Lab3"},
          {path: "/lab4", name: "Lab4"},
          {path: "/lab5", name: "Lab5"}
        ]
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }
</style>
