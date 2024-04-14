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
    <div> 중복된 값이 존재합니다 </div>
    <div> {{ modalInfo.clusterAddNodeAddress }} </div>
    <div> 현재 노드의 값 : {{ modalInfo.clusterCurNodeValue }}</div>
    <div> 변경될 노드 : {{ modalInfo.clusterAddNodeValue }}</div>
  </ModalWrapper>
</template>
