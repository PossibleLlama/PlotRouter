<template>
    <div class="session">
        <button class="btn" v-if="loggedIn" @click="toggleSessionStatus">Logout</button>
        <button class="btn" v-else @click="toggleSessionStatus">Login</button>
    </div> <!-- Session -->
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component({
  components: {
  },
})
export default class Session extends Vue {
  private 'loggedIn': boolean = false;
  private 'username': string = 'Username';

  private 'toggleSessionStatus'(): void {
    this.loggedIn = !this.loggedIn;
    this.$emit('clicked', this.username, this.loggedIn);
  }
}
</script>

<style>
</style>

