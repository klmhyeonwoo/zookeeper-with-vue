<script setup>
import ModalWrapper from "./ModalWrapper.vue";
import CustomInput from "../CustomInput.vue";
import { ref } from "vue";
import useModalStore from "../../../stores/modal";
import { storeToRefs } from "pinia";

const store = useModalStore();
const { modalInfo } = storeToRefs(store);
console.log(modalInfo.value);
</script>

<template>
  <ModalWrapper>
    <div :class="$style.title"> 등록하고자 하는 노드(
      <span :class="$style.address"> {{ modalInfo.clusterAddNodeAddress }} </span>
      )가 존재합니다 </div>
    <div :class="$style.values">
      <div> {{ modalInfo.clusterCurNodeValue }} (현재)</div>
      <span> >>> </span>
      <div> {{ modalInfo.clusterAddNodeValue }} (변경)</div>
    </div>
  </ModalWrapper>
</template>

<style module>
  .title {
    font-size: 1.3rem;
    font-weight: 600;
    color: #212529;
  }

  .address {
    font-size: 0.9rem;
    font-weight: 500;
  }

  .values {
    display: flex;
    column-gap: 1rem;
  }

  .values > div {
    padding: 1rem;
    border-radius: 0.4rem;
  }
</style>
