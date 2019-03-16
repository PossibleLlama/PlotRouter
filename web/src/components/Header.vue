<template>
  <header>
    <nav class="navbar navbar-default" role="navigation">
      <div class="container">

        <div class="navbar-header">

					<a href="#"><h1>Plot Router</h1></a>
					<a href="#"><h2>Events</h2></a>
					
				</div> <!-- Navbar Header -->

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
export default class App extends Vue {}
</script>

<style>
a {
	color: inherit;
	display: inline-block;
	margin-right: 1rem;
	margin-left: 1rem;
}
a:hover {
	color: rgb(62, 72, 59);
}
h1 {
	font-size: 2rem;
	margin: 0px;
}
h2 {
	font-size: 1.5rem;
	margin: 0px;
}
</style>
