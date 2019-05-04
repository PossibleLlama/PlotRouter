<template>
  <div id="app">
    <Header @clicked="userEvent"></Header>

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
import Vue from 'vue';
import Component from 'vue-class-component';
import Footer from './components/Footer.vue';
import Header from './components/navbar/Header.vue';

@Component({
  components: {
    Footer,
    Header,
  },
})
export default class App extends Vue {
  private 'user': {
      name: string,
      status: string;
    } = {
    name: '',
    status: 'unknown',
  };

  private 'userEvent'(userName: string, userEvent: string): void {
    this.user.name = userName;
    if (userEvent === 'login') {
      this.user.status = 'logged in';
    } else if (userEvent === 'logout') {
      this.user.status = 'logged out';
    } else {
      // TODO log what event was returned.
      // TODO consider using enums (as much as JS allows)
      this.user.status = 'unknown';
    }
  }
}
</script>

<style>
body {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  color: rgb(12, 22, 9);
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  margin-top: 0px;
  background-color: #e3f1e3;
  box-sizing: border-box;
}
</style>
