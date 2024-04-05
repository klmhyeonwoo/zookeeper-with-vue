<script setup>
import NegativeIcon from "../../assets/negativeIcon.svg";
import PositiveIcon from '../../assets/positionIcon.svg';
import useToastStore from "../../stores/toast";
import {storeToRefs} from "pinia";

const toastStore = useToastStore();
const { toastMessage } = storeToRefs(toastStore);

</script>
<template>
  <div :class="$style.toastContainer">
    <div v-show="toastMessage === 'Success'" :class="$style.toast">
      <PositiveIcon width="20" height="20"/>
      <span :class="$style.toastMessage"
        >성공적으로 추가되었습니다</span
      >
    </div>
    <div v-show="toastMessage === 'Fail'" :class="$style.toast">
      <NegativeIcon width="20" height="20" />
      <span :class="$style.toastMessage"
        >에러가 발생하여 실패했습니다</span
      >
    </div>
  </div>
</template>

<style module>

.toastContainer {
  position: absolute;
  left: 50%;
  transform: translate(-50%);
  bottom: 5%;
  z-index: 99999;
  padding: 1.2rem 1.8rem 1.2rem 1.8rem;
  border-radius: 10rem;
  background: #333E4C;
  box-sizing: border-box;

  display: flex;
  justify-content: center;
  align-items: center;
}

.toast {
  display: flex;
  justify-content: center;
  align-items: center;
  height: fit-content;
  margin: auto 0;
  column-gap: 0.7rem;
  transition: 0.4s all;
}

.toastMessage {
  font-size: 16px;
  color: white;
}
</style>
