<template>
    <div class="session">
        <button class="btn"
          v-if="!loggedIn" @click="overlayVisible = true">Login</button>
        <button class="btn"
          v-else @click="logout">Logout</button>
        <Overlay v-if="overlayVisible" @close="overlayVisible = false">

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
    this.loggedIn = true;
    this.$emit('clicked', user, 'login');
  }

  private 'logout'(user: string): void {
    this.loggedIn = false;
    this.$emit('clicked', user, 'logout');
  }
}
</script>

<style>
</style>

