<script setup>
import CustomButton from "../CustomButton.vue";
import useModalStore from "../../../stores/modal";
import { ref } from "vue";
import { storeToRefs } from "pinia";
const store = useModalStore();
const { closeModal, openModal, confirm, modalInfo } = store;

const state = storeToRefs(store);
const submit = () =>
  state.modalInfo.value.confirm()
</script>

<template>
  <div :class="$style.modalWrapper">
    <div :class="$style.modal">
      <slot />
      <div :class="$style.utilSection">
        <div :class="$style.buttonSection">
          <CustomButton
            @click="submit"
            :style="[
              modalInfo.firstValue.length && modalInfo.twiceValue.length
                ? { 'background-color': 'rgb(49, 129, 246)' }
                : { filter: 'grayscale(100%)', opacity: '40%' },
            ]"
            :disabled="
              !(modalInfo.firstValue.length && modalInfo.twiceValue.length)
            "
            >확인하기</CustomButton
          >
          <CustomButton @click="closeModal">취소하기</CustomButton>
        </div>
      </div>
    </div>
  </div>
</template>

<style module>
.modal {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 10001;
  background: white;
  min-width: 32rem;
  min-height: 10rem;
  height: auto;
  border-radius: 0.7rem;
  box-sizing: border-box;

  display: flex;
  flex-direction: column;
  row-gap: 1.5rem;
  align-items: center;
  padding: 2rem;
}

.modalWrapper {
  position: absolute;
  width: 100vw;
  height: 100vh;
  background: rgba(15, 15, 15, 0.921);
  z-index: 10000;
}

.utilSection {
  display: flex;
  width: 100%;
}

.buttonSection {
  display: flex;
  column-gap: 1rem;
  margin-left: auto;
  margin-top: 0.2rem;
}
</style>
