<template>
  <button
    :style="[style, disabled && 'filter: grayscale(100%); opacity: 40%;']"
    :disabled="disabled"
  >
    <slot />
  </button>
</template>

<script setup>
  const props = defineProps({
    style: Object,
    disabled: Boolean,
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
