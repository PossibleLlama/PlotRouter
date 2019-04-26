<template>
    <div id="overlay">
        <div id="overlayBox">
          <button class="btn"
            @click="$emit('close')">
          x</button>

          <div id="loginForm">
            <slot></slot>
          </div>
        </div>
    </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component({
  components: {
  },
})
export default class Overlay extends Vue {
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

#overlayBox {
  margin: auto;
  width: 80%;
  height: 80%;
  margin-top: 5%;
  background-color: rgb(230,230,230);
}

#loginForm {
  margin-top: 0;
  margin-left: 5%;
  margin-right: 5%;
}

button {
  position: relative;
  left: 98%;
}
</style>
