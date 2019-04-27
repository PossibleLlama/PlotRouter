<template>
    <div class="session">
        <button class="btn"
          v-if="!loggedIn" @click="overlayVisible = true">Login</button>
        <button class="btn"
          v-else @click="logout">Logout</button>
        <Overlay v-if="overlayVisible"
          @close="overlayVisible = false">
          <div>
            <h3>User</h3>
            <input v-model="user" placeholder="username">
            <button class="btn" id="submitForm" @click="login(user)">Submit</button>
          </div>
        </Overlay>
    </div> <!-- Session -->
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Overlay from '../Overlay.vue';

@Component({
  components: {
    Overlay,
  },
})
export default class Session extends Vue {
  private 'loggedIn': boolean = false;
  private 'overlayVisible': boolean = false;

  private 'login'(user: string): void {
    this.$emit('clicked', user, 'login');
    this.loggedIn = true;
    this.overlayVisible = false;
  }

  private 'logout'(user: string): void {
    this.loggedIn = false;
    this.$emit('clicked', user, 'logout');
  }
}
</script>

<style>
#submitForm {
  display: block;
  position: inherit;
  margin-top: 1rem;
}
</style>

