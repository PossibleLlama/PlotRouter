<template>
    <div class="session">
        <button class="btn" v-if="loggedIn" @click="toggleSessionStatus">Logout</button>
        <button class="btn" v-else @click="toggleSessionStatus">Login</button>
        
        <div id="overlay" v-if="overlay">
        </div>
    </div> <!-- Session -->
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component({
  components: {
  },
})
export default class Session extends Vue {
  private 'overlay': boolean = false;
  private 'loggedIn': boolean = false;
  private 'username': string = 'Username';

  private 'toggleSessionStatus'(): void {
    this.overlay = true;
    this.loggedIn = !this.loggedIn;
    this.$emit('clicked', this.username, this.loggedIn);
  }
}
</script>

<style>
#overlay {
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0,0,0,0.5);
  z-index: 2;
  cursor: pointer;
}
</style>

