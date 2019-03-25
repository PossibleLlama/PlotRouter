<template>
  <header>
    <nav class="navbar navbar-default" role="navigation">
      <div class="container">

        <div class="navbar-header">

					<a href="#"><h1>Plot Router</h1></a>
					<a href="#"><h2>Events</h2></a>
					
				</div> <!-- Navbar Header -->
      	<div class="session">
					<a href="#" @click="toggleSessionStatus">
						<h3 v-if="loggedIn">Logout</h3>
						<h3 v-else>Login</h3>
					</a>
      	</div> <!-- Session -->

      </div> <!-- container -->
    </nav>
  </header>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component({
  components: {
  },
})
export default class Header extends Vue {
  private 'loggedIn': boolean = false;

  private 'toggleSessionStatus'(): void {
    this.loggedIn = !this.loggedIn;
    this.$emit('clicked', 'llama', this.loggedIn);
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
.session {
	position: relative;
}
</style>
