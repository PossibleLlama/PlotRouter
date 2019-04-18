<template>
    <div id="overlay">
        <div id="overlayBox">
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
</style>
