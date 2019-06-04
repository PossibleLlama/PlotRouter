<template>
  <div class="ppList">
      <PlotPointBox id='1' summary='sum' description='description'></PlotPointBox>
  </div>
</template>

<script lang="ts">
import axios from 'axios';
import { Component, Prop, Vue } from 'vue-property-decorator';
import PlotPointBox from './PlotPointBox.vue';

@Component({
  components: {
    PlotPointBox,
  },
})
export default class PlotPointList extends Vue {
    @Prop(String) public readonly user!: string;

    public plotPointsList: Array<{
      id: string,
      user: string,
      summary: string,
      description: string,
      preceedingId: string,
    }> = [];
    private baseUrl: string = 'http://localhost:8000/api/plotpoint';

    private async mounted(): Promise<void> {
      const plotPointsList = await this.getAllPlotPointsForUser(this.user);
      // v-for each object in data.plotPoints, create a PlotPointBox object
    }

    private async getAllPlotPointsForUser(user: string): Promise<Array<{
      id: string,
      user: string,
      summary: string,
      description: string,
      preceedingId: string,
    }>> {
      const getAllByUserUrl = `${this.baseUrl}/user/${user}`;
      const apiResponse = await axios.get(getAllByUserUrl);
      return apiResponse.data.plotPoints;
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