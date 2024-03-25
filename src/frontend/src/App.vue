<script setup>
import { storeToRefs } from "pinia";
import { RouterLink, RouterView } from "vue-router";
import DoubleInputModal from "./components/common/modal/DoubleInputModal.vue";
import ModalWrapper from "./components/common/modal/ModalWrapper.vue";
import { useModalStore } from "./stores/modal";

const store = useModalStore();
const { state } = storeToRefs(store);
</script>

<template>
  <DoubleInputModal v-if="state" />
  <RouterView />
</template>

<style scoped></style>
