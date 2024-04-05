<script setup>
import ModalWrapper from "./ModalWrapper.vue";
import CustomInput from "../CustomInput.vue";
import { ref } from "vue";
import useModalStore from "../../../stores/modal";
import { storeToRefs } from "pinia";

const store = useModalStore();
const { modalInfo } = storeToRefs(store);
</script>

<template>
  <ModalWrapper>
    <CustomInput
      v-model.trim="modalInfo.firstValue"
      :placeholder="modalInfo.firstPlaceholder"
      :label="modalInfo.firstLabel"
    />
    <CustomInput
      v-model.trim="modalInfo.twiceValue"
      :placeholder="modalInfo.twicePlaceholder"
      :label="modalInfo.twiceLabel"
    />
  </ModalWrapper>
</template>
