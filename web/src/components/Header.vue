<template>
  <header>
    <nav class="navbar navbar-default" role="navigation">
      <div class="container">

        <div class="navbar-header">
					<span class="home">
            <a class="navbar-brand" href="#"><h1>Plot Router</h1></a>
					</span>

					<button role="menubar" type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#collapseMenu"
					aria-expanded="false" style="inline">
						<span class="sr-only">Toggle Navigation Bar</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					
				</div> <!-- Navbar Header -->

				<div class="collapse navbar-collapse" id="collapseMenu">
					<ul role="menubar" class="nav navbar-nav">
						<li>
							<a href="#">Events</a>
						</li>
						<li>
							<a href="#">B</a>
						</li>
						<li>
							<a href="#">C</a>
						</li>
					</ul>
          </div> <!-- Collapsable Navbar -->

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
}
</style>
