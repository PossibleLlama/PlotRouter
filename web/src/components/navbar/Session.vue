<template>
    <div class="session">
        <button class="btn sessionState"
          v-if="!loggedIn" @click="openedComponents.overlay = true">Login</button>
        <div class="dropdown">
          <button class="btn sessionState"
            v-if="loggedIn" @click="openedComponents.dropdown = !openedComponents.dropdown">
            {{truncate(_username, 10)}}</button>
          <div id="sessionDropdown"
            v-if="openedComponents.dropdown">
            <button class="btn sessionState" @click="logout">Logout</button>
          </div>
        </div>

        <Overlay v-if="openedComponents.overlay"
          @close="openedComponents.overlay = false">
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
  private 'openedComponents': {
    overlay: boolean,
    dropdown: boolean,
  } = {
    overlay: false,
    dropdown: false,
  };

  private 'login'(user: string): void {
    this.$emit('clicked', user, 'login');
    this._username = user;
    this.loggedIn = true;
    this.openedComponents.overlay = false;
  }

  private 'logout'(user: string): void {
    this.$emit('clicked', user, 'logout');
    this._username = '';
    this.loggedIn = false;
    this.openedComponents.dropdown = false;
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

.sessionState:hover, .sessionState:focus {
  background-color: #a4bea859;
}

#sessionDropdown {
  position: absolute;
  z-index: 1;
}
</style>

