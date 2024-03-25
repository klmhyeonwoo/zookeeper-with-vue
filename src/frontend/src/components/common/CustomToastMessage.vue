<script setup>
import { computed, watch } from "vue";
import NegativeIcon from "../../assets/negativeIcon.svg";
import PositiveIcon from "../../assets/positiveIcon.svg";
const props = defineProps({
  toastState: {
    type: String,
    default: "",
  },
});

// const emit = defineEmits(["initialize"]);
// watch(
//   props.toastState,
//   () => {
//     if (props.toastState !== "") {
//       // TODO: 2초 뒤에 자동으로 사라지도록 로직 설계
//       setTimeout(() => {
//         emit("initialize");
//       }, 3000);
//     }
//   },
//   { immediate: true },
// );
</script>
<template>
  <div v-show="toastState === 'success'" :class="$style.toast">
    <PositiveIcon />
    <span :class="$style.toastMessage" style="color: #15c47e"
      >성공적으로 추가되었습니다</span
    >
  </div>
  <div v-show="toastState === 'fail'" :class="$style.toast">
    <NegativeIcon />
    <span :class="$style.toastMessage" style="color: #f04452"
      >에러가 발생하여 실패했습니다</span
    >
  </div>
</template>

<style module>
.toast {
  display: flex;
  justify-content: center;
  align-items: center;
  height: fit-content;
  margin: auto 0;
  column-gap: 0.4rem;
  transition: 0.4s all;
}

.toastMessage {
  font-size: 14px;
}
</style>
