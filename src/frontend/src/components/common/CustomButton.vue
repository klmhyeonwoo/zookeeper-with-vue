<template>
  <button :style="style">
    <slot />
  </button>
</template>

<script setup>
defineProps({
  style: StyleSheet,
});
</script>

<style>
button {
  margin-left: auto;
  height: auto;
  min-width: 6rem;
  min-height: 2.4rem;
  white-space: nowrap;
  background: #40464d;
  color: white;
  border: none;
  border-radius: 0.6rem;
  cursor: pointer;
  padding-left: 1.5rem;
  padding-right: 1.5rem;
  padding-top: 0.7rem;
  padding-bottom: 0.7rem;
  box-sizing: border-box;
  transition: 0.4s all;
}
</style>
