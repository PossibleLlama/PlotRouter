<template>
  <div class="ppBox">
      <h1>{{ summary }}</h1>
      <p>{{ description }}</p>
      <p>{{ preceedingPlotPointId }}</p>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class PlotPointBox extends Vue {
    @Prop(String) public readonly id!: string;
    @Prop(String) public readonly summary!: string;
    @Prop(String) public readonly description!: string;
    @Prop(String) public readonly preceedingPlotPointId!: string;
}
</script>

<style>
.ppBox {
    border: 1px solid #0000002c;
    background-color: #c4cccc77;
    width: 45%;
    height: 80px;
}
.ppBox h1 {
    font-size: 1.4rem;
}
.ppBox p {
    font-size: 1rem;
}
</style>