<template>
    <div class="session">
        <button class="btn sessionState"
          v-if="!loggedIn" @click="overlayVisible = true">Login</button>
        <button class="btn sessionState"
          v-else @click="logout">{{truncate(_username, 10)}}</button>
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
  private '_username': string = '';
  private 'loggedIn': boolean = false;
  private 'overlayVisible': boolean = false;

  private 'login'(user: string): void {
    this.$emit('clicked', user, 'login');
    this._username = user;
    this.loggedIn = true;
    this.overlayVisible = false;
  }

  private 'logout'(user: string): void {
    this.loggedIn = false;
    this.$emit('clicked', user, 'logout');
  }

  private 'truncate'(original: string, maxLength: number): string {
    return original.length > maxLength ?
      original.substring(0, maxLength) + '...' : original;
  }
}
</script>

<style>
#submitForm {
  display: block;
  position: inherit;
  margin-top: 1rem;
}

.btn {
  border: 1px solid #54685454;
}

.sessionState {
  width: 8rem;
}
</style>

