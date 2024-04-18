<template>
  <span :style="style">
    <slot />
  </span>
</template>

<script setup>
  defineProps({
    style: Object,
  });
</script>

<style scoped>
  span {
    font-weight: 300;
    font-size: 14px;
    margin-bottom: 0.5rem;
  }
</style>
