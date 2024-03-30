<script setup>
const props = defineProps({
  name: String,
  class: String,
});
</script>

<template>
    <div class="name" :class="props.class">· {{ props.name }}</div>
</template>

<style scoped>
.name {
  color: white;
  font-size: 21px;
  border: solid;
  cursor: pointer;

  padding-bottom: 0.5rem;
  border-top: 0;
  border-left: 0;
  border-right: 0;

  transition: 0.4s all;
  opacity: 80%;
}

.selected {
  font-weight: 700;
  opacity: 100%;
}
</style>
