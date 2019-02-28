import Vue from 'vue';
import Router from 'vue-router';
import Lab1 from "../components/Lab1";
import Lab2 from "../components/Lab2";
import Lab3 from "../components/Lab3";
import Lab4 from "../components/Lab4";
import Lab5 from "../components/Lab5";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/lab1',
      name: 'lab1',
      component: Lab1
    },
    {
      path: '/lab2',
      name: 'lab2',
      component: Lab2
    },
    {
      path: '/lab3',
      name: 'lab3',
      component: Lab3
    },
    {
      path: '/lab4',
      name: 'lab4',
      component: Lab4
    },
    {
      path: '/lab5',
      name: 'lab5',
      component: Lab5
    }
  ]
})
