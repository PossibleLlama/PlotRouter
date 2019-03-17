<template>
  <div id="app">
    <Header></Header>

    <div class="main">
      <div class="page">
        <div class="container">
          <div class="row">
            <div class="col-sm-12">
              <img alt="Vue logo" src="./assets/logo.png">
            </div>
          </div>
        </div>
      </div>
    </div>

    <Footer></Footer>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Footer from './components/Footer.vue';
import Header from './components/Header.vue';

@Component({
  components: {
    Footer,
    Header,
  },
})
export default class App extends Vue {}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  color: rgb(12, 22, 9);
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  margin-top: 0px;
  background-color: #e3f1e3;
  box-sizing: border-box;
}
</style>
