<template>
  <header>
    <nav class="navbar navbar-default" role="navigation">
      <div class="container">

        <div class="navbar-header">

					<a href="#"><h1>Plot Router</h1></a>
					<a href="#"><h2>Events</h2></a>
					
				</div> <!-- Navbar Header -->
        <Session @clicked="toggleSessionStatus"></Session>
      </div> <!-- container -->
    </nav>
  </header>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Session from './Session.vue';

@Component({
  components: {
    Session,
  },
})
export default class Header extends Vue {
  private 'toggleSessionStatus'(username: string, loggedIn: string): void {
    this.$emit('clicked', username, loggedIn);
  }
}
</script>

<style>
a {
	color: inherit;
	display: inline-block;
	margin: 0px;
	margin-right: 1rem;
	margin-left: 1rem;
}
a:hover {
	color: rgb(62, 72, 59);
}
h1 {
	font-size: 2rem;
}
h2, h3 {
	font-size: 1.2rem;
}
</style>
