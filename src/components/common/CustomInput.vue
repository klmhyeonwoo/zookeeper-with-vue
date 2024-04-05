<script setup>
import { storeToRefs } from "pinia";

const props = defineProps({
  label: String,
  modelValue: String,
  placeholder: String,
});
const emits = defineEmits(["update:modelValue"]);
</script>

<template>
  <div :class="$style.container">
    <label> {{ props.label }}</label>
    <input
      :placeholder="props.placeholder"
      :value="props.modelValue"
      @input="$emit('update:modelValue', $event.target.value)"
    />
  </div>
</template>

<style module>
.container {
  width: 100%;
  display: flex;
  flex-direction: column;
  row-gap: 0.7rem;
}

input {
  border: none;
  box-shadow: inset 0 0 0 1px rgba(0, 27, 55, 0.1);
  border-radius: 0.7rem;
  width: 100%;
  min-height: 3.2rem;
  height: 100%;
  padding-left: 1rem;
  box-sizing: border-box;
}

label {
  color: #4e5968;
}
</style>
