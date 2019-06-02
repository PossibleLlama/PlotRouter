<template>
  <div class="ppList">
      <PlotPointBox id='1' summary='sum' description='description'></PlotPointBox>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import PlotPointBox from './PlotPointBox.vue';

@Component({
  components: {
    PlotPointBox,
  },
})
export default class PlotPointList extends Vue {
    @Prop(String) public readonly user!: string;

    private mounted(): void {
        // get host name for endpoints
        // make request to '{{plotpoint}}/user/{{user}}'
        // v-for each object in data.plotPoints, create a PlotPointBox object
    }
}
</script>

<style>
.ppList {
    border: 1px solid #0000002c;
    background-color: #9beb6536;
    width: 100%;
    height: 100%;
    padding: 2.5%;
}
</style>