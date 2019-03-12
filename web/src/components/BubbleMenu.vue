<template>
    <div class="circle">{{ msg }}</div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class BubbleMenu extends Vue {
  @Prop() private msg!: string;
}
</script>

<style scoped>
.circle
    {
    border-radius:50%;
    padding-top: 1em;
    text-align:center;
    font-size: medium;
    color:rgb(231, 241, 227);
    background:rgb(56, 97, 54);
    }
</style>

