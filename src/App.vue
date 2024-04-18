<script setup>
  import { storeToRefs } from "pinia";
  import { RouterLink, RouterView } from "vue-router";
  import DoubleInputModal from "./components/common/modal/DoubleInputModal.vue";
  import ModalWrapper from "./components/common/modal/ModalWrapper.vue";
  import { useModalStore } from "./stores/modal";
  import { useToastStore } from "./stores/toast";
  import CustomToast from "./components/common/CustomToast.vue";
  import WarningModal from "./components/common/modal/WarningModal.vue";

  const modalStore = useModalStore();
  const { doubleInputModalState, warningModalState } = storeToRefs(modalStore);
  const toastStore = useToastStore();
  const { toastState } = storeToRefs(toastStore);
</script>

<template>
  <DoubleInputModal v-if="doubleInputModalState" />
  <WarningModal v-if="warningModalState" />
  <CustomToast v-if="toastState" />
  <RouterView />
</template>

<style scoped></style>
