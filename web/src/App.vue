<template>
  <div id="app">
    <Index/>
    <router-view/>
  </div>
</template>

<script>
  import Index from "./components/Index";

  export default {
    name: 'App',
    components: {Index}
  }
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }
</style>
